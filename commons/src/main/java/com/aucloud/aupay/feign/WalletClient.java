package com.aucloud.aupay.feign;

import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.bo.AupayDigitalCurrencyWallet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient("service-wallet")
public interface WalletClient {

    @RequestMapping("wallet/getUserDigitalCurrencyWallet")
    Result<AupayDigitalCurrencyWallet> getUserDigitalCurrencyWallet(@RequestParam("userId") String userId, @RequestParam(value = "currencyId",required = false) Integer currencyId, @RequestParam(value = "currencyChain", required = false) Integer currencyChain);

    @RequestMapping("wallet/getApplicationCurrencyWallet")
    Result<AupayDigitalCurrencyWallet> getApplicationCurrencyWallet(@RequestParam("applicationId") String applicationId, @RequestParam(value = "currencyId") Integer currencyId, @RequestParam("currencyChain") Integer currencyChain);

    @RequestMapping("wallet/initUserAssetsWallet")
    Result initUserAssetsWallet(@RequestParam("userId") String userId);

    @RequestMapping("trade/initApplinctionWallet")
    Result initApplinctionWallet(@RequestParam("applicationId") String applicationId);

    @RequestMapping("wallet/getWithdrawWallet")
    Result<AupayDigitalCurrencyWallet> getWithdrawWallet(@RequestParam("currencyId") Integer currencyId, @RequestParam("currencyChain") Integer currencyChain);

//    @RequestMapping("wallet/addBalance")
//    Result addBalance(@RequestParam("walletId") String walletId, @RequestParam("amount") BigDecimal amount);

//    @RequestMapping("wallet/testTransaction")
//    Result testTransaction();

    @RequestMapping("wallet/getUserWalletByUserId")
    Result<List<AupayDigitalCurrencyWallet>> getUserWalletByUserId(@RequestParam("userId") String userId);

    @RequestMapping("trade/getApplicationWalletByApplicationId")
    Result getApplicationWalletByApplicationId(@RequestParam("applicationId") String applicationId);

    @RequestMapping("wallet/userAutoAssetsCollect")
    Result userAutoAssetsCollect(@RequestParam("limitAmount") BigDecimal limitAmount);
}