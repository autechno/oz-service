package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.po.AupayEnterprise;
import com.aucloud.aupay.user.orm.service.AupayEnterpriseService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enterprise")
public class EnterpriseController {

    private final AupayEnterpriseService aupayEnterpriseService;

    public EnterpriseController(AupayEnterpriseService aupayEnterpriseService) {
        this.aupayEnterpriseService = aupayEnterpriseService;
    }

    @GetMapping("baseInfo")
    public Result<AupayEnterprise> getEnterprise() {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long accountId = tokenInfo.getAccountId();
        AupayEnterprise enterprise = aupayEnterpriseService.getById(accountId);
        return Result.returnResult(ResultCodeEnum.SUCCESS, enterprise);
    }
}
