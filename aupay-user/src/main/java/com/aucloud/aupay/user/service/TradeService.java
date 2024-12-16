package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.feign.FeignWalletService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService {

    @Autowired
    private AssetsService assetsService;
    @Autowired
    private FeignWalletService feignWalletService;

    public String withdraw(WithdrawDTO withdrawDTO) {
        String tradeNo = assetsService.preDeduct(withdrawDTO);
        withdrawDTO.setTradeNo(tradeNo);

        Result<?> result1 = feignWalletService.generateWithdrawTask(withdrawDTO);
        if (null == result1 || result1.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
            throw new ServiceRuntimeException(ResultCodeEnum.INSUFFICIENT_BALANCE);
        }
        return tradeNo;
    }
}
