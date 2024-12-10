package com.aucloud.aupay.wallet.feign;

import com.aucloud.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("chain-rpc-tron")
public interface TronClient {

    @RequestMapping("createWallet")
    Result<String> createWallet();

    @RequestMapping("transfer")
    Result<String> transfer(@RequestParam("currencyId") Integer currencyId, @RequestParam("fromAddress") String fromAddress, @RequestParam("toAddress") String toAddress, @RequestParam("amount") BigDecimal amount);

    @RequestMapping("getBalance")
    Result<BigDecimal> getBalance(@RequestParam("address") String address, @RequestParam("currencyId") Integer currencyId);

    @RequestMapping("getTxInfo")
    Result getTxInfo(@RequestParam("currencyId") Integer currencyId, @RequestParam("txId") String txId);
}
