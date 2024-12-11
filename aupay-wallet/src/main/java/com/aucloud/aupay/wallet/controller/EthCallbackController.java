package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.service.WalletTransferRecordService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.EthTransactionCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("eth")
public class EthCallbackController {

    @Autowired
    private WalletTransferRecordService walletTransferRecordService;

    @PostMapping("receiptTransactionHashAsync")
    Result<?> receiptTransactionHashAsync(@RequestBody EthTransactionCallback obj) {
        walletTransferRecordService.updateTransactionHash(obj.getInnerHash(), obj.getHash());
        return Result.returnResult(ResultCodeEnum.SUCCESS);
    }

    @PostMapping("postTransactionResult")
    Result<String> postTransactionResult(@RequestBody EthTransactionCallback obj) {
        walletTransferRecordService.transactionResult(obj);
        return Result.returnResult(ResultCodeEnum.SUCCESS);
    }
}
