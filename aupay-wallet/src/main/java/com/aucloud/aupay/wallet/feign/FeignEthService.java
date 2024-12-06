package com.aucloud.aupay.wallet.feign;


import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawBatchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("aupay-eth")
public interface FeignEthService {

    @RequestMapping("getBalance")
    Result<BigDecimal> getBalance(@RequestParam("address") String address, @RequestParam("currencyId") Integer currencyId);

    @PostMapping("withdrawBatch")
    Result<String> withdrawBatch(@RequestBody WithdrawBatchDto dto);
}
