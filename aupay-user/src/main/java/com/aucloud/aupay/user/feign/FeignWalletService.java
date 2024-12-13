package com.aucloud.aupay.user.feign;

import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.AccountChainWalletDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface FeignWalletService {

    @RequestMapping(value = "wallet/getAccountWallets",method = RequestMethod.GET)
    Result<List<AccountChainWalletDto>> getAccountWallets(@RequestParam Integer accountId, @RequestParam Integer accountType);

    @RequestMapping("wallet/generateAccountWallet")
    public Result<List<AccountChainWalletDto>> generateAccountWallet(@RequestParam Long accountId, @RequestParam Integer accountType);
}
