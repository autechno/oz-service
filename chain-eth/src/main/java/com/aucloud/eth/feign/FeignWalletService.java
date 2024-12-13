package com.aucloud.eth.feign;

import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.EthTransactionCallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "aupay-wallet")
public interface FeignWalletService {

    @GetMapping("config/getConfigedWalletAddress")
    Result<String> getConfigedWalletAddress(@RequestParam("walletType") Integer walletType, @RequestParam("currencyChain") Integer currencyChain);

    @PostMapping("eth/receiptTransactionHashAsync")
    Result<?> receiptTransactionHashAsync(@RequestBody EthTransactionCallback obj);

    @PostMapping("eth/postTransactionResult")
    Result<?> postTransactionResult(@RequestBody EthTransactionCallback obj);
}
