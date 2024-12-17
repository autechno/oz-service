package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.service.WalletTransferRecordService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.vo.WalletTransferRecordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("transfer")
public class WalletTransferRecordController {

    @Autowired
    private WalletTransferRecordService walletTransferRecordService;

//    public Result<List<WalletTransferRecord>> getTransferRecords() {
//        return Result.returnResult(ResultCodeEnum.SUCCESS);
//    }

    @GetMapping("getTransferRecordsByTradeNo")
    public Result<List<WalletTransferRecordVo>> getTransferRecordsByTradeNo(@RequestParam(value = "tradeNo") String tradeNo) {
        List<WalletTransferRecordVo> voList = new ArrayList<>();
        List<WalletTransferRecord> list = walletTransferRecordService.lambdaQuery().eq(WalletTransferRecord::getTradeNo, tradeNo).list();
        if (list != null && !list.isEmpty()) {
            voList = list.stream().map(r -> {
                WalletTransferRecordVo vo = new WalletTransferRecordVo();
                BeanUtils.copyProperties(r, vo);
                vo.setTradeType(r.getTradeType().getCode());
                return vo;
            }).toList();
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS, voList);
    }
}
