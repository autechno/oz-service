package com.aucloud.aupay.wallet.feign;

import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.AcountRechargeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "aupay-user")
public interface FeignUserService {

    @PostMapping("/assets/recharge")
    Result<?> recharge(@RequestBody AcountRechargeDTO dto);
}
