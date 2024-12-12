package com.aucloud.aupay.operate.controller;


import com.aucloud.aupay.operate.service.OperationService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.entity.HuobiTicker;
import com.aucloud.commons.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("operation")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @RequestMapping(value = "getUSDTTickers", method = RequestMethod.GET)
    public Result<List<HuobiTicker>> getUSDTTickers() {
        List<HuobiTicker> usdtTickers = operationService.getUSDTTickers();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), usdtTickers);
    }

    @RequestMapping(value = "getUSDTRates", method = RequestMethod.GET)
    public Result<Map<String,String>> getUSDTRates() {
        Map<String,String> currencyRates = operationService.getUSDTRates();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), currencyRates);
    }

}
