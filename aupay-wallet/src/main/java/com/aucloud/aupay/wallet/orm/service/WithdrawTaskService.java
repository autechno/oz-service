package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.feign.FeignEthContractService;
import com.aucloud.aupay.wallet.feign.FeignUserService;
import com.aucloud.aupay.wallet.orm.constant.TradeType;
import com.aucloud.aupay.wallet.orm.mapper.WithdrawTaskMapper;
import com.aucloud.aupay.wallet.orm.po.ConfigWalletAddress;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.po.WithdrawTask;
import com.aucloud.commons.constant.*;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.WithdrawBatchDto;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import com.aucloud.commons.pojo.dto.WithdrawRecordQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    private FeignEthContractService feignEthContractService;
    @Autowired
    private WalletTransferRecordService walletTransferRecordService;
    @Autowired
    private FeignUserService feignUserService;
    @Autowired
    private ConfigWalletAddressService configWalletAddressService;

    public void saveWithdrawTask(WithdrawDTO withdrawDTO) {
        WithdrawTask task = new WithdrawTask();
        task.setAmount(withdrawDTO.getAmount());
        task.setFee(withdrawDTO.getFee());
        task.setCurrencyId(withdrawDTO.getCurrencyId());
        task.setCurrencyChain(withdrawDTO.getCurrencyChain());
        task.setToAddress(withdrawDTO.getToAddress());
        task.setCreateTime(new Date());
        task.setStatus(WithdrawTaskStatus.PENDING);
        task.setTradeNo(withdrawDTO.getTradeNo());
        task.setAssetsId(withdrawDTO.getAssetsId());
        task.setAccountId(withdrawDTO.getAccountId());
        task.setAccountType(withdrawDTO.getAccountType());
        save(task);
    }

    public Page<WithdrawTask> listWithdrawTaskRecords(PageQuery<WithdrawRecordQuery> pageQuery) {
        LambdaQueryWrapper<WithdrawTask> queryWrapper = new LambdaQueryWrapper<>();
        WithdrawRecordQuery query = pageQuery.getConditions();
        Long accountId = query.getAccountId();
        if (accountId != null) {
            queryWrapper.eq(WithdrawTask::getAccountId, accountId);
        }
        Integer accountType = query.getAccountType();
        if (accountType != null) {
            queryWrapper.eq(WithdrawTask::getAccountType, accountType);
        }
        Long assetsId = query.getAssetsId();
        if (assetsId != null) {
            queryWrapper.eq(WithdrawTask::getAssetsId, assetsId);
        }
        String tradeNo = query.getTradeNo();
        if (tradeNo != null) {
            queryWrapper.eq(WithdrawTask::getTradeNo, tradeNo);
        }
        Page<WithdrawTask> page = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        return page(page, queryWrapper);
    }



    private static final int once_max = 10;

    //定时提币 批量处理
    @Scheduled(initialDelay = 1000, fixedRate = 60000)
    public void withdrawScheduled() {
        List<WithdrawTask> list = lambdaQuery()
                .eq(WithdrawTask::getStatus, WithdrawTaskStatus.PENDING)
                .or(w -> w.eq(WithdrawTask::getStatus, WithdrawTaskStatus.FAILED).lt(WithdrawTask::getFailedTimes, 3))
                .list();
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
                    batchWithdraw(k1, k, sub);
                    i += end;
                }
            });
        });
    }

    private String getWithdrawAddress(Integer currencyId, Integer chainId) {
        List<ConfigWalletAddress> list = configWalletAddressService.lambdaQuery().eq(ConfigWalletAddress::getWalletType, WalletType.WITHDRAW.getCode()).eq(ConfigWalletAddress::getCurrencyChain, chainId).list();
        if (list == null || list.isEmpty()) {
            throw new ServiceRuntimeException(ResultCodeEnum.ENVIRONMENTAL_ANOMALY);
        }
        return list.get(0).getWalletAddress();
    }

    private void batchWithdraw(Integer currencyId, Integer chainId, List<WithdrawTask> list) {
        Map<String, BigDecimal> addressAmountMaps = list.stream().collect(Collectors.toMap(WithdrawTask::getToAddress, WithdrawTask::getAmount));
        BigDecimal totalAmount = addressAmountMaps.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        String batchNo = UUID.randomUUID().toString();

        list.forEach(item -> {
            item.setBatchNo(batchNo);
            item.setStatus(WithdrawTaskStatus.WAITING);
            updateById(item);
        });

        try {
            String withdrawAddress = getWithdrawAddress(currencyId, chainId);
            Result<BigDecimal> wdBalance = feignEthContractService.getBalance(withdrawAddress, currencyId);
            if (wdBalance!=null && wdBalance.getCode() == ResultCodeEnum.SUCCESS.getCode() && wdBalance.getData().compareTo(totalAmount) > 0) {
                WithdrawBatchDto dto = new WithdrawBatchDto();
                dto.setBatchNo(batchNo);
                dto.setAddressAmounts(addressAmountMaps);
                dto.setCurrencyEnum(CurrencyEnum.findById(currencyId));
                Result<String> result = feignEthContractService.withdrawBatch(dto);
                if (result != null && result.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
                    String txHash = result.getData();

                    WalletTransferRecord record = new WalletTransferRecord();
                    record.setAmount(totalAmount);
                    record.setTradeNo(batchNo);
                    record.setTxHash(txHash);
                    record.setCurrencyId(currencyId);
                    record.setCurrencyChain(chainId);
                    record.setTradeType(TradeType.WITHDRAW);
                    record.setCreateTime(new Date());
                    record.setStatus(WalletTransferStatus.PENDING);
                    record.setFromAddress(withdrawAddress);
                    walletTransferRecordService.save(record);
                } else {
                    throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
                }
            } else {
                throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
            }
        } catch (Exception e) {
            list.forEach(item -> {
                item.setBatchNo(batchNo);
                item.setStatus(WithdrawTaskStatus.FAILED);
                int failedTimes = item.getFailedTimes() == null ? 0 : item.getFailedTimes();
                item.setFailedTimes(failedTimes + 1);
                updateById(item);
            });
        }
    }

    public void withdrawFinish(String txHash) {
        WalletTransferRecord record = walletTransferRecordService.lambdaQuery().eq(WalletTransferRecord::getTxHash, txHash).oneOpt().orElseThrow();
        record.setStatus(WalletTransferStatus.SUCCESS);
        record.setFinishTime(new Date());
        walletTransferRecordService.updateById(record);
        String tradeNo = record.getTradeNo();
        List<WithdrawTask> list = lambdaQuery().eq(WithdrawTask::getBatchNo, tradeNo).list();
        list.forEach(item -> {
            item.setStatus(WithdrawTaskStatus.SUCCESS);
            item.setFinishTime(new Date());
            updateById(item);
            feignUserService.withdrawFinish(item.getTradeNo());
        });
    }
}
