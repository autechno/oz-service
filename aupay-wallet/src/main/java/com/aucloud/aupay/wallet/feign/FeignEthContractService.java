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
public interface FeignEthContractService {

    @RequestMapping("contract/getBalance")
    Result<BigDecimal> getBalance(@RequestParam("address") String address, @RequestParam("currencyId") Integer currencyId);

    @PostMapping("contract/withdrawBatch")
    Result<String> withdrawBatch(@RequestBody WithdrawBatchDto dto);

    @PostMapping("contract/user2collect")
    Result<String> user2collect(@RequestParam("currencyId") Integer currencyId, @RequestParam("limit") BigDecimal limit);
    @PostMapping("contract/collect2withdraw")
    Result<String> collect2withdraw(@RequestParam("currencyId") Integer currencyId, @RequestParam("limit") BigDecimal limit);
    @PostMapping("contract/collect2store")
    Result<String> collect2store(@RequestParam("storeWallet") String storeWallet, @RequestParam("currencyId") Integer currencyId, @RequestParam("limit") BigDecimal limit);
}
