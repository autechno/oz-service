package com.aucloud.aupay.feign;

import com.aucloud.commons.entity.HuobiTicker;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.do_.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-operate")
public interface SysClient {

    @RequestMapping("sys/bindWithdrawWallet")
    Result bindWithdrawWallet(@RequestParam("currencyId") Integer currencyId, @RequestParam("currencyChain") Integer currencyChain, @RequestParam("walletId") String walletId);

    @RequestMapping("sys/bindAssetsWallet")
    Result bindAssetsWallet(@RequestParam("currencyId") Integer id, @RequestParam("currencyChain") Integer chainId, @RequestParam("walletId") String walletId);

    @RequestMapping("sys/bindTransferWallet")
    Result bindTransferWallet(@RequestParam("currencyId") Integer id, @RequestParam("currencyChain") Integer chainId, @RequestParam("walletId") String walletId);

    @RequestMapping("sys/bindTransferFeeWallet")
    Result bindTransferFeeWallet(@RequestParam("currencyId") Integer id, @RequestParam("currencyChain") Integer chainId, @RequestParam("walletId") String walletId);

    @RequestMapping("sys/getTransferWalletConfig")
    Result<List<AupayTransferWalletConfig>> getTransferWalletConfig();

    @RequestMapping("sys/getWithdrawWalletConfig")
    Result<List<AupayWithdrawWalletConfig>> getWithdrawWalletConfig();
    @RequestMapping("sys/getReserveWalletConfig")
    Result<List<AupayReserveWalletConfig>> getReserveWalletInfo();

    @RequestMapping("sys/getFeeWalletConfig")
    Result<List<AupayFeeWalletConfig>> getFeeWalletConfig();

    @RequestMapping("sys/getUserAssetsCollectionConfig")
    Result<List<AupayUserAssetsCollectionConfig>> getUserAssetsCollectionConfig();

    @RequestMapping("sys/getWithdrawConfig")
    Result<AupayWithdrawConfig> getWithdrawConfig(@RequestParam("currencyId") Integer currencyId, @RequestParam("currencyChain") Integer currencyChain);

    @RequestMapping("sys/getUSDTTickers")
    Result<List<HuobiTicker>> getUSDTTickers();

}
