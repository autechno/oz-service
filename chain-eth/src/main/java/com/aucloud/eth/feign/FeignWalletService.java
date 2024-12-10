package com.aucloud.eth.feign;

import com.aucloud.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "aupay-wallet")
public interface FeignWalletService {

    @GetMapping("config/getConfigedWalletAddress")
    Result<String> getConfigedWalletAddress(@RequestParam("walletType") Integer walletType, @RequestParam("currencyChain") Integer currencyChain);
}
