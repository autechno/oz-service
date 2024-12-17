package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.po.UserSystemConfig;
import com.aucloud.aupay.user.orm.service.UserSystemConfigService;
import com.aucloud.commons.constant.AccountType;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("config")
public class UserSystemConfigController {

    private final UserSystemConfigService userSystemConfigService;

    public UserSystemConfigController(UserSystemConfigService userSystemConfigService) {
        this.userSystemConfigService = userSystemConfigService;
    }

    @GetMapping("getUserSystemConfig")
    public Result<UserSystemConfig> getUserSystemConfig() {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Integer accountType = tokenInfo.getAccountType();
        Long humanId = tokenInfo.getUserId();
        if (Objects.equals(accountType, AccountType.COMPANY_USER)) {
            humanId = tokenInfo.getEmployeeId();
        }

        UserSystemConfig userSystemConfig = userSystemConfigService.lambdaQuery()
                .eq(UserSystemConfig::getHumanId, humanId)
                .eq(UserSystemConfig::getHumanType, accountType)
                .oneOpt().orElseGet(UserSystemConfig::new);
        return Result.returnResult(ResultCodeEnum.SUCCESS, userSystemConfig);
    }

    @PostMapping("setUserSystemConfig")
    public Result<Boolean> setUserSystemConfig(@RequestBody UserSystemConfig userSystemConfig) {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Integer accountType = tokenInfo.getAccountType();
        Long humanId = tokenInfo.getUserId();
        if (Objects.equals(accountType, AccountType.COMPANY_USER)) {
            humanId = tokenInfo.getEmployeeId();
        }
        userSystemConfig.setHumanId(humanId);
        userSystemConfig.setHumanType(accountType);
        boolean b = userSystemConfigService.saveOrUpdate(userSystemConfig);
        return Result.returnResult(ResultCodeEnum.SUCCESS, b);
    }
}
