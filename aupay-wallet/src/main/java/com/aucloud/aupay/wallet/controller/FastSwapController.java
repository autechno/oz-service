package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.db.orm.po.FastSwapRecord;
import com.aucloud.aupay.wallet.orm.service.FastSwapRecordService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("trade")
public class FastSwapController {

    @Autowired
    private FastSwapRecordService fastSwapRecordService;

    @PostMapping("generateFastSwapRecord")
    public Result<String> generateFastSwapRecord(@RequestBody FastSwapRecord fastSwapRecord) {
        String tradeNo = Tools.generateRandomString(32);
        fastSwapRecord.setTradeNo(tradeNo);
        fastSwapRecordService.save(fastSwapRecord);
        return Result.returnResult(ResultCodeEnum.SUCCESS, tradeNo);
    }
}
