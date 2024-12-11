package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.orm.po.AupayUserLoginLog;
import com.aucloud.aupay.user.orm.service.AupayUserLoginLogService;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.UserInfoDTO;
import com.aucloud.pojo.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class BaseInfoController {

    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private AupayUserLoginLogService aupayUserLoginLogService;

    @RequestMapping(value = "getUserInfo",method = RequestMethod.GET)
//    @Operation(value = OperationEnum.GET_USER_INFO,handler = DefaultOperationHandler.class)
    public Result<UserInfoVo> getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        UserInfoVo userInfo = aupayUserService.getUserInfo(userId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),userInfo);
    }

    @RequestMapping(value = "setUserInfo",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.SET_USER_INFO,handler = DefaultOperationHandler.class)
    public Result<?> setUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        aupayUserService.setUserInfo(userInfoDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping(value = "switchWhiteAddress",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.SWITCH_WHITE_ADDRESS,handler = DefaultOperationHandler.class)
    public Result<?> switchWhiteAddress() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        aupayUserService.switchWhiteAddress(userId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }
    @RequestMapping(value = "getSwitchWhite",method = RequestMethod.PUT)
//    @Operation(value = OperationEnum.SWITCH_WHITE_ADDRESS,handler = DefaultOperationHandler.class)
    public Result<Integer> getSwitchWhite() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        int switchWhite = aupayUserService.getSwitchWhite(userId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),switchWhite);
    }

    @RequestMapping(value = "getRecentLoginLog",method = RequestMethod.GET)
//    @Operation(value = OperationEnum.GET_RECENT_LOGIN_LOG,handler = DefaultOperationHandler.class)
    public Result<AupayUserLoginLog> getRecentLoginLog() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = 0L;
        AupayUserLoginLog aupayUserLoginLog = aupayUserLoginLogService.lambdaQuery()
                .eq(AupayUserLoginLog::getUserId, userId)
                .orderByDesc(AupayUserLoginLog::getCreateTime)
                .last("limit 1")
                .one();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),aupayUserLoginLog);
    }

}
