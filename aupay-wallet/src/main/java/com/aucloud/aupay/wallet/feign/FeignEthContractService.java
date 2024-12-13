package com.aucloud.aupay.wallet.feign;


import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.WithdrawBatchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @GetMapping("generateUserWalletsBatch")
    Result<List<String>> generateUserWalletsBatch(@RequestParam("count") int count);

    @RequestMapping("contract/recycleUserWallet")
    Result<?> recycleUserWallet(@RequestParam("address") String address);
}
