package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.service.AssetsService;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.pojo.Result;
import com.aucloud.pojo.dto.AcountRechargeDTO;
import com.aucloud.pojo.dto.WithdrawDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("assets")
public class AssetsController {

    @Autowired
    private AssetsService assetsService;

    @PostMapping("/withdraw/pre-deduct")
    public Result<String> preDeduct(@RequestBody WithdrawDTO withdrawDTO) {
        String tradeNo = assetsService.preDeduct(withdrawDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS, tradeNo);
    }

    @PostMapping("recharge")
    public Result<?> recharge(@RequestBody AcountRechargeDTO dto) {
        assetsService.recharge(dto);
        return Result.returnResult(ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/withdraw/finish")
    public Result<?> withdrawFinish(@RequestParam("tradeNo") String tradeNo) {
        assetsService.withdrawFinish(tradeNo);
        return Result.returnResult(ResultCodeEnum.SUCCESS);
    }


    @RequestMapping(value = "getAssetsInfo",method = RequestMethod.GET)
//    @Operation(value = OperationEnum.GET_ASSETS_INFO,handler = DefaultOperationHandler.class)
    public Result getAssetsInfo() {
        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        UserAssetsIndexVo userAssetsIndexVo = new UserAssetsIndexVo();
        List<UserAssetsVo> list = userService.findUserAssetsVoList(userId);
        AupayUserTradeRecord aupayUserTradeRecord = userService.findUserRecentTradeRecord(userId);
        AupayUserRechargeRecord aupayUserRechargeRecord = userService.findUserRecentRechargeRecord(userId);
        userAssetsIndexVo.setList(list);
        userAssetsIndexVo.setOzBalance(BigDecimal.ZERO);
        userAssetsIndexVo.setRecentAupayUserTraderRecord(aupayUserTradeRecord);
        userAssetsIndexVo.setRecentAupayUserRechargeRecord(aupayUserRechargeRecord);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),userAssetsIndexVo);
    }

    @RequestMapping(value = "getCurrencyAssetsInfo",method = RequestMethod.GET)
//    @Operation(value = OperationEnum.GET_CURRENCY_ASSETS_INFO,handler = DefaultOperationHandler.class)
    public Result getCurrencyAssetsInfo(@RequestParam Integer currencyId, @RequestParam(defaultValue = "0") Integer currencyChain) {
        String userId = SecurityTokenHandler.getTokenInfoObject().getId();
        UserAssetsVo userAssetsVo = userService.getCurrencyAssetsInfo(userId,currencyId,currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),userAssetsVo);
    }
}
