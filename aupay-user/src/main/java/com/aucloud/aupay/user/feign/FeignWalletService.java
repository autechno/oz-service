package com.aucloud.aupay.user.feign;

import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.AccountChainWalletDto;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "aupay-wallet")
public interface FeignWalletService {

    @PostMapping("/withdraw/generateWithdrawTask")
    Result<?> generateWithdrawTask(@RequestBody WithdrawDTO withdrawDTO);

    @RequestMapping(value = "wallet/getAccountWallets",method = RequestMethod.GET)
    Result<List<AccountChainWalletDto>> getAccountWallets(@RequestParam Integer accountId, @RequestParam Integer accountType);

    @RequestMapping("wallet/generateAccountWallet")
    Result<List<AccountChainWalletDto>> generateAccountWallet(@RequestParam Long accountId, @RequestParam Integer accountType);
}
