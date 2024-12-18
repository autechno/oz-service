package com.aucloud.aupay.trade.fegin;

import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.WithdrawDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aupay-wallet")
public interface FeignWalletService {

    @PostMapping("/withdraw/generateWithdrawTask")
    public Result<?> generateWithdrawTask(@RequestBody WithdrawDTO withdrawDTO);
}
