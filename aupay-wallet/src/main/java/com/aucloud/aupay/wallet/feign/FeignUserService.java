package com.aucloud.aupay.wallet.feign;

import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.AcountRechargeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "aupay-user")
public interface FeignUserService {

    @PostMapping("assets/recharge")
    Result<?> recharge(@RequestBody AcountRechargeDTO dto);

    @GetMapping("assets/withdraw/finish")
    Result<?> withdrawFinish(@RequestParam("tradeNo") String tradeNo);
}
