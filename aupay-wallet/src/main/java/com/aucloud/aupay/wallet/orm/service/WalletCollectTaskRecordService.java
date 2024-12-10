package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.mapper.WalletCollectTaskRecordMapper;
import com.aucloud.aupay.wallet.orm.po.WalletCollectTaskRecord;
import com.aucloud.constant.WalletTransferStatus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 内部钱包资金归集周转任务记录表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Service
public class WalletCollectTaskRecordService extends ServiceImpl<WalletCollectTaskRecordMapper, WalletCollectTaskRecord> implements IService<WalletCollectTaskRecord> {

    public WalletCollectTaskRecord generateTaskRecord(Integer configId, Integer dependTaskId) {
        String tradeNo = UUID.randomUUID().toString();
        WalletCollectTaskRecord taskRecord = new WalletCollectTaskRecord();
        taskRecord.setConfigId(configId);
        taskRecord.setStatus(WalletTransferStatus.PENDING);
        taskRecord.setTradeNo(tradeNo);
        taskRecord.setDependTaskId(dependTaskId);
        save(taskRecord);
        return taskRecord;
    }


    public WalletCollectTaskRecord updateResult(String tradeNo, Integer status) {
        WalletCollectTaskRecord taskRecord = lambdaQuery().eq(WalletCollectTaskRecord::getTradeNo, tradeNo).oneOpt().orElseThrow();
        taskRecord.setStatus(status);
        taskRecord.setFinishTime(new Date());
        updateById(taskRecord);
        return taskRecord;
    }
}
