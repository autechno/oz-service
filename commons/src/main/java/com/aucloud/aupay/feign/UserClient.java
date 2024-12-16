package com.aucloud.aupay.feign;

import com.aucloud.commons.pojo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-user")
public interface UserClient {

    @RequestMapping(value = "internal/haveGoogleAuth")
    public Result<Boolean> haveGoogleAuth(@RequestParam(name = "userId") String userId);
}
