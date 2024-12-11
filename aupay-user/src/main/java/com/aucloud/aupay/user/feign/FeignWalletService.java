package com.aucloud.aupay.user.feign;

import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.AccountChainWalletDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface FeignWalletService {

    @RequestMapping(value = "getAccountWallets",method = RequestMethod.GET)
    Result<List<AccountChainWalletDto>> getAccountWallets(@RequestParam Integer accountId, @RequestParam Integer accountType);
}
