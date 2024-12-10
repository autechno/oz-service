package com.aucloud.aupay.wallet.service;

import com.aucloud.aupay.wallet.orm.constant.CollectEventType;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletCollect;
import com.aucloud.aupay.wallet.orm.po.WalletCollectTaskRecord;
import com.aucloud.aupay.wallet.orm.service.ConfigWalletCollectService;
import com.aucloud.aupay.wallet.orm.service.WalletCollectTaskRecordService;
import com.aucloud.constant.QueueConstant;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class WalletCollectTaskService {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private final ConcurrentHashMap<Integer, ScheduledFuture<?>> configScheduledMap = new ConcurrentHashMap<>();

    @Autowired
    private ConfigWalletCollectService configWalletCollectService;
    @Autowired
    private WalletCollectTaskRecordService walletCollectTaskRecordService;
    @Autowired
    private GasService gasService;
    @Autowired
    private User2TransferService user2TransferService;
    @Autowired
    private Transfer2WithdrawService transfer2WithdrawService;
    @Autowired
    private Transfer2StoreService transfer2StoreService;
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//    private ScheduledFuture<?> scheduledFuture;

    @PostConstruct
    public void init() {
        List<ConfigWalletCollect> config = configWalletCollectService.lambdaQuery().ge(ConfigWalletCollect::getPause, 0).list();
        if (config != null && !config.isEmpty()) {
            config.forEach(this::startCronTask);
        }
    }

    /**
     * 启动定时任务
     *
     * @param configWalletCollect 定时任务类
     */
    public void startCronTask(ConfigWalletCollect configWalletCollect) {
        log.info("startCronTask ");
        ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(() -> {
            log.info("开始执行 自动归集 定时任务任务: {}", System.currentTimeMillis());
            schedule(configWalletCollect.getId());
        }, new CronTrigger(configWalletCollect.getCron()));
        assert scheduledFuture != null;
        configScheduledMap.put(configWalletCollect.getId(), scheduledFuture);
    }

    /**
     * 动态更新定时任务的Cron表达式
     */
    public void updateCronTask(ConfigWalletCollect configWalletCollect) {
        log.info("updateCronTask config Id: {}", configWalletCollect.getId());
        stopCronTask(configWalletCollect.getId()); // 先停止当前任务
        startCronTask(configWalletCollect); // 然后启动带有新Cron表达式的任务
    }

    /**
     * 停止定时任务
     */
    public void stopCronTask(Integer configId) {
        log.info("stopCronTask. config id: {}", configId);
        if (!configScheduledMap.isEmpty() && configScheduledMap.containsKey(configId)) {
            configScheduledMap.get(configId).cancel(true);
            configScheduledMap.remove(configId);
        }
    }

    public void saveOrupdate(ConfigWalletCollect configWalletCollect) {
        log.info("updateConfig config Id: {}", configWalletCollect.getId());
        Integer id = configWalletCollect.getId();
        if (Objects.isNull(id)) {
            //save
            configWalletCollectService.save(configWalletCollect);
            startCronTask(configWalletCollect);
        } else {
            ConfigWalletCollect old = configWalletCollectService.getById(configWalletCollect.getId());
            configWalletCollectService.updateById(configWalletCollect);
            if (StringUtils.isNotBlank(configWalletCollect.getCron()) && !StringUtils.equalsIgnoreCase(old.getCron(), configWalletCollect.getCron())) {
                updateCronTask(configWalletCollect);
            }
        }
    }

    private void schedule(Integer configId) {
        log.info("schedule config Id: {}", configId);
        ConfigWalletCollect config = configWalletCollectService.getById(configId);
        if (Objects.nonNull(config)) {

            WalletCollectTaskRecord taskRecord = walletCollectTaskRecordService.generateTaskRecord(configId, null);

            CollectEventType eventType = config.getEventType();
            switch (eventType) {
                case USER_TO_TRANSFER:
                    user2TransferService.user2transfer(config, taskRecord);
                    break;
                case TRANSFER_TO_WITHDRAW:
                    transfer2WithdrawService.transfer2withdraw(config, taskRecord);
                    break;
                case TRANSFER_TO_STORE:
                    transfer2StoreService.transfer2store(config, taskRecord);
                    break;
                case GAS_TO_OPERATOR:
                    gasService.gas2operator(config, taskRecord);
                    break;
                default:
                    ;
            }
        }
    }

    @RabbitListener(queuesToDeclare = {@Queue(value = QueueConstant.AFTER_GAS_TRANSFER, durable = "true", autoDelete = "false")})
    public void afteGasTask(Integer dependTaskId) {
        WalletCollectTaskRecord dependtask = walletCollectTaskRecordService.getById(dependTaskId);
        Integer dependConfigId = dependtask.getConfigId();
        ConfigWalletCollect dependConfig = configWalletCollectService.getById(dependConfigId);
        CollectEventType eventType = dependConfig.getEventType();
        switch (eventType) {
            case USER_TO_TRANSFER:
                user2TransferService.user2transfer(dependConfig, dependtask);
                break;
            case TRANSFER_TO_WITHDRAW:
                transfer2WithdrawService.transfer2withdraw(dependConfig, dependtask);
                break;
            case TRANSFER_TO_STORE:
                transfer2StoreService.transfer2store(dependConfig, dependtask);
                break;
            default:
                ;
        }
    }

}
