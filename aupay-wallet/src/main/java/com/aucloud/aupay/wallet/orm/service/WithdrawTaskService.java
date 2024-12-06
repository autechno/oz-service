package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.feign.FeignEthService;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.po.WithdrawTask;
import com.aucloud.aupay.wallet.orm.mapper.WithdrawTaskMapper;
import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.constant.TradeType;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawBatchDto;
import com.aucloud.pojo.dto.WithdrawDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <p>
 * 提币任务表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class WithdrawTaskService extends ServiceImpl<WithdrawTaskMapper, WithdrawTask> implements IService<WithdrawTask> {

    @Autowired
    private FeignEthService feignEthService;
    @Autowired
    private WalletTransferRecordService walletTransferRecordService;

    public void saveWithdrawTask(WithdrawDTO withdrawDTO) {
        WithdrawTask task = new WithdrawTask();
        task.setAmount(withdrawDTO.getAmount());
        task.setFee(withdrawDTO.getFee());
        task.setCurrencyId(withdrawDTO.getCurrencyId());
        task.setCurrencyChain(withdrawDTO.getCurrencyChain());
        task.setToAddress(withdrawDTO.getToAddress());
        task.setTradeNo(withdrawDTO.getTradeNo());
        task.setCreateTime(new Date());
        save(task);
    }

    private static final int once_max = 10;

    //定时提币 批量处理
    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void nf() {
        List<WithdrawTask> list = lambdaQuery().eq(WithdrawTask::getStatus, 0).list();
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Integer, List<WithdrawTask>> chainMaps = list.stream().collect(Collectors.groupingBy(WithdrawTask::getCurrencyChain));
        chainMaps.forEach((k, v) -> {
            Map<Integer, List<WithdrawTask>> currencyMaps = v.stream().collect(Collectors.groupingBy(WithdrawTask::getCurrencyId));
            currencyMaps.forEach((k1, v1) -> {
                int size = v1.size();
                for (int i = 0; i < v1.size(); ) {
                    int end = Math.min(i + once_max, size);
                    List<WithdrawTask> sub = v1.subList(i, end);
                    xxx(k1, k, sub);
                    i += end;
                }
            });
        });
    }

    private String getWithdrawAddress(Integer currencyId, Integer chainId) {
        return "";
    }

    private void xxx(Integer currencyId, Integer chainId, List<WithdrawTask> list) {
        Map<String, BigDecimal> addressAmountMaps = list.stream().collect(Collectors.toMap(WithdrawTask::getToAddress, WithdrawTask::getAmount));
        BigDecimal totalAmount = addressAmountMaps.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        String withdrawAddress = getWithdrawAddress(currencyId, chainId);
        Result<BigDecimal> wdBalance = feignEthService.getBalance(withdrawAddress, currencyId);
        if (wdBalance!=null && wdBalance.getCode() == ResultCodeEnum.SUCCESS.getCode() && wdBalance.getData().compareTo(totalAmount) > 0) {
            String batchNo = UUID.randomUUID().toString();
            WithdrawBatchDto dto = new WithdrawBatchDto();
            dto.setBatchNo(batchNo);
            dto.setAddressAmounts(addressAmountMaps);
            dto.setCurrencyEnum(CurrencyEnum.findById(currencyId));
            Result<String> result = feignEthService.withdrawBatch(dto);
            if (result != null && result.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
                String txHash = result.getData();
                list.forEach(item -> {
                    item.setBatchNo(batchNo);
                    item.setStatus(1);
                    save(item);
                });
                WalletTransferRecord record = new WalletTransferRecord();
                record.setAmount(totalAmount);
                record.setTradeNo(batchNo);
                record.setTxHash(txHash);
                record.setCurrencyId(currencyId);
                record.setCurrencyChain(chainId);
                record.setTradeType(TradeType.WITHDRAW.getCode());
                record.setCreateTime(new Date());
                record.setStatus(1);
                record.setFromAddress(withdrawAddress);
                walletTransferRecordService.save(record);
            } else {
                throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
            }
        } else {
            throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
        }
    }
}
