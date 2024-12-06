package com.aucloud.aupay.trade.service;

import com.aucloud.aupay.trade.fegin.FeignUserService;
import com.aucloud.aupay.trade.fegin.FeignWalletService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawService {

    @Autowired
    private FeignUserService feignUserService;
    @Autowired
    private FeignWalletService feignWalletService;

    public String withdraw(WithdrawDTO withdrawDTO) {
        String tradeNo = "";
        Result<String> result = feignUserService.assetsPreDeduct(withdrawDTO);
        if (null != result && result.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            tradeNo = result.getData();
            withdrawDTO.setTradeNo(tradeNo);

            Result<?> result1 = feignWalletService.generateWithdrawTask(withdrawDTO);
            if (null == result1 || result1.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
            }
        }
        return tradeNo;
    }

}
