package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.constant.TradeType;
import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.mapper.WalletTransferRecordMapper;
import com.aucloud.aupay.wallet.service.GasService;
import com.aucloud.aupay.wallet.service.Transfer2StoreService;
import com.aucloud.aupay.wallet.service.Transfer2WithdrawService;
import com.aucloud.aupay.wallet.service.User2TransferService;
import com.aucloud.constant.WalletTransferStatus;
import com.aucloud.pojo.dto.EthTransactionCallback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 链上交易流水表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class WalletTransferRecordService extends ServiceImpl<WalletTransferRecordMapper, WalletTransferRecord> implements IService<WalletTransferRecord> {

    @Autowired
    private GasService gasService;
    @Autowired
    private User2TransferService user2TransferService;
    @Autowired
    private Transfer2StoreService transfer2StoreService;
    @Autowired
    private Transfer2WithdrawService transfer2WithdrawService;
    @Autowired
    private WithdrawTaskService withdrawTaskService;

    public void updateTransactionHash(String innerHash, String transactionHash) {
        WalletTransferRecord record = lambdaQuery().eq(WalletTransferRecord::getTxHash, innerHash).oneOpt().orElseThrow();
        record.setTxHash(transactionHash);
        record.setStatus(WalletTransferStatus.WAITING);
        updateById(record);
    }



    public void transactionResult(EthTransactionCallback callback) {
        String innerHash = callback.getInnerHash();
        String transactionHash = callback.getHash();
        WalletTransferRecord record = lambdaQuery()
                .eq(WalletTransferRecord::getTxHash, innerHash)
                .or(w -> w.eq(WalletTransferRecord::getTxHash, transactionHash))
                .oneOpt().orElseThrow();
        Integer status = callback.isStatus()? WalletTransferStatus.SUCCESS: WalletTransferStatus.FAILED;
        record.setStatus(status);
        record.setFinishTime(new Date());
        updateById(record);

        TradeType tradeType = record.getTradeType();
        String tradeNo = record.getTradeNo();

        switch (tradeType) {
            case GAS_TO_OPERATOR -> gasService.result(tradeNo, status);
            case TRANSFER_TO_STORE -> transfer2StoreService.result(tradeNo, status);
            case TRANSFER_TO_WITHDRAW -> transfer2WithdrawService.result(tradeNo, status);
            case USER_TO_TRANSFER -> user2TransferService.result(tradeNo, status);
            case WITHDRAW -> withdrawTaskService.withdrawFinish(tradeNo);
        }
    }

}
