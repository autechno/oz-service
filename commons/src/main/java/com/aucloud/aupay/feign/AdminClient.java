package com.aucloud.aupay.feign;

import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.do_.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("service-admin")
public interface AdminClient {

    @RequestMapping("saveOperationLog")
    Result saveOperationLog(@RequestBody AupayAdminOperationLog aupayAdminOperationLog);

    @RequestMapping("checkAdminIp")
    Integer checkAdminIp(@RequestParam("ipAddress") String ipAddress);

    @RequestMapping("trade/applicationAssetsChange")
    Result<AupayApplicationAssetsChangeRecord> applicationAssetsChange(@RequestParam("applicationOrderId") String applicationOrderId,
                                                                       @RequestParam("applicationId") String applicationId,
                                                                       @RequestParam("currencyId") Integer currencyId,
                                                                       @RequestParam("currencyChain") Integer currencyChain,
                                                                       @RequestParam("amount") BigDecimal amount,
                                                                       @RequestParam(value = "chainTxId",required = false) String chainTxId,
                                                                       @RequestParam(value = "sigin",required = false) Integer sigin) ;

    @RequestMapping("application_assets/add")
    Result addApplicationAssets(@RequestBody AupayApplicationAssets aupayApplicationAssets) ;

    @RequestMapping("initApplication")
    Result initApplication(@RequestParam("merchantId") String merchantId, @RequestParam(value = "merchantName", required = false) String merchantName) ;

//    @RequestMapping("trade/getApplicationWalletByMerId")
//    Result getApplicationWalletByMerId(@RequestParam("merchantId")   String merchantId) ;

    @RequestMapping("trade/getApplicationApiKey")
    Result getApplicationApiKey(@RequestParam("applicationId") String applicationId);

    @RequestMapping("trade/getApplicationInfoByMerId")
    Result<AupayApplication> getApplicationInfoByMerId(@RequestParam("merchantId") String merchantId);

    @RequestMapping(value = "trade/getAdminInfo",method = RequestMethod.GET)
    public Result<AupayAdmin> getAdminInfo(@RequestParam(value = "adminId", required = false) String adminId);

    }
