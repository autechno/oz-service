package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.constant.MerchantsConstant;
import com.aucloud.aupay.constant.ResultCodeEnum;
import com.aucloud.aupay.feign.AdminClient;
import com.aucloud.aupay.mybatis.Pager;
import com.aucloud.aupay.operation.OperationEnum;
import com.aucloud.aupay.operation.annotations.Operation;
import com.aucloud.aupay.operation.handler.AdminOperationHandler;
import com.aucloud.aupay.pojo.Result;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.AupayApplication;
import com.aucloud.aupay.pojo.do_.AupayApplicationWithdrawWalletConfig;
import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
import com.aucloud.aupay.pojo.vo.ApplicationWithdrawWalletVo;
import com.aucloud.aupay.pojo.vo.FeeWalletVo;
import com.aucloud.aupay.pojo.vo.SysAssetsVo;
import com.aucloud.aupay.pojo.vo.UserWalletVo;
import com.aucloud.aupay.security.annotation.Internal;
import com.aucloud.aupay.service.UserWalletCollectionService;
import com.aucloud.aupay.service.WalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserWalletCollectionService userWalletCollectionService;
    @Autowired
    private AdminClient adminClient;

    //权限待补
    @RequestMapping("findUserWalletList")
    public Result<Pager> findUserWalletList(@RequestBody Pager pager) {
        List<UserWalletVo> list = walletService.findUserWalletList(pager);
        pager.setData(list);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),pager);
    }

    @RequestMapping("getAssetsVo")
    public Result getAssetsVo() {
        List<SysAssetsVo> list = walletService.getAssetsVo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }

    //权限待补
    @RequestMapping("getApplicaitonAssetsWalletInfo")
    public Result getApplicaitonAssetsWalletInfo() {
        List<AupayDigitalCurrencyWallet> list = walletService.getApplicaitonAssetsWalletInfo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }
    //权限待补
    @RequestMapping("getApplicationWalletByApplicationId")
    public Result getApplicationWalletByApplicationId(@RequestParam  String applicationId) {

        List<AupayDigitalCurrencyWallet> list = walletService.getApplicationWalletByApplicationId(applicationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }


    //权限待补
    @RequestMapping("getApplicationWallet")
    public Result getApplicationWallet(@RequestParam String applicationId, @RequestParam Integer currencyId, @RequestParam Integer currencyChain) {
        AupayDigitalCurrencyWallet applicationWallet = walletService.getApplicationWallet(applicationId,currencyId,currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),applicationWallet);
    }
    //权限待补
    @RequestMapping("getApplicaitonWithdrawWalletInfo")
    public Result getApplicaitonWithdrawWalletInfo() {
        List<ApplicationWithdrawWalletVo> list = walletService.getApplicaitonWithdrawWalletInfo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }

    //权限待补
    @RequestMapping("updateApplicationWithdrawWalletConfig")
    public Result updateApplicationWithdrawWalletConfig(@RequestBody AupayApplicationWithdrawWalletConfig aupayApplicationWithdrawWalletConfig) {
        aupayApplicationWithdrawWalletConfig.setApplicationId("ozbet");
        walletService.updateApplicationWithdrawWalletConfig(aupayApplicationWithdrawWalletConfig);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping("getTransferWalletWalletInfo")
    @Operation(value = OperationEnum.GET_TRANSFER_WALLET_INFO,handler = AdminOperationHandler.class)
    public Result getTransferWalletWalletInfo() {
        List<AupayDigitalCurrencyWallet> list = walletService.getTransferWalletWalletInfo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }

    @RequestMapping("getReserveWalletInfo")
    @Operation(value = OperationEnum.GET_RESERVE_WALLET_INFO,handler = AdminOperationHandler.class)
    public Result getReserveWalletInfo() {
        List<AupayDigitalCurrencyWallet> list = walletService.getReserveWalletInfo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }

    @RequestMapping("getWithdrawWalletInfo")
    @Operation(value = OperationEnum.GET_WITHDRAW_WALLET_INFO,handler = AdminOperationHandler.class)
    public Result getWithdrawWalletInfo() {
        List<AupayDigitalCurrencyWallet> list = walletService.getWithdrawWalletInfo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }

    @RequestMapping("getFeeWalletInfo")
    @Operation(value = OperationEnum.GET_FEE_WALLET_INFO,handler = AdminOperationHandler.class)
    public Result getApplicaitonFeeWalletInfo() {
        List<FeeWalletVo> list = walletService.getApplicaitonFeeWalletInfo();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }

    @RequestMapping("findWalletTransferRecord")
    @Operation(value = OperationEnum.FIND_WALLET_TRANSFER_RECORD,handler = AdminOperationHandler.class)
    public Result findWalletTransferRecord(@RequestBody Pager pager) {
        List<AupayWalletTransferRecord> list = walletService.findWalletTransferRecord(pager);
        pager.setData(list);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),pager);
    }

    //权限待补
    @RequestMapping("initSysWallet")
    public Result initSysWallet() {
        walletService.initSysWallet();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    //权限待补
    @RequestMapping("initApplinctionWallet")
    public Result<?> initApplinctionWallet(@RequestParam String applicationId) {
        if(StringUtils.isBlank(applicationId)) {
            Result<AupayApplication> applicationResult = adminClient.getApplicationInfoByMerId(MerchantsConstant.MERCHANT_OZBET);
            if (applicationResult == null || applicationResult.getCode() != ResultCodeEnum.SUCCESS.getCode()) {
                return Result.returnResult(ResultCodeEnum.NOT_FOUND.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
            }
            applicationId = applicationResult.getData().getApplicationId();
        }
        walletService.initApplinctionWallet(applicationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }


    @RequestMapping("initUserAssetsWallet")
    @Internal
    public Result initUserAssetsWallet(@RequestParam String userId) {
        walletService.initUserAssetsWallet(userId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping("regenUserAssetsWallet")
    @Operation(value = OperationEnum.REGEN_USER_ASSETS_WALLET,handler = AdminOperationHandler.class)
    public Result regenUserAssetsWallet(@RequestParam String userId, @RequestParam Integer currencyId, @RequestParam Integer currencyChain) {
        walletService.regenUserAssetsWallet(userId, currencyId, currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping("userAssetsCollect")
    @Operation(value = OperationEnum.USER_ASSETS_COLLECT,handler = AdminOperationHandler.class)
    public Result userAssetsCollect() {
        userWalletCollectionService.userAssetsCollect();
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping("userAutoAssetsCollect")
    public Result userAutoAssetsCollect(@RequestParam("limitAmount") BigDecimal limitAmount) {
        userWalletCollectionService.userAssetsCollect(limitAmount);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
    }

    @RequestMapping("getUserDigitalCurrencyWallet")
    @Internal
    public Result<AupayDigitalCurrencyWallet> getUserDigitalCurrencyWallet(@RequestParam("userId") String userId, @RequestParam(value = "currencyId", required = false) Integer currencyId, @RequestParam(value = "currencyChain",required = false) Integer currencyChain) {
        AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = walletService.getUserDigitalCurrencyWallet(userId, currencyId, currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),aupayDigitalCurrencyWallet);
    }
    @RequestMapping("getApplicationCurrencyWallet")
    @Internal
    public Result<AupayDigitalCurrencyWallet> getApplicationCurrencyWallet(@RequestParam String applicationId, @RequestParam(value = "currencyId",required = false) Integer currencyId, @RequestParam(value = "currencyChain",required = false) Integer currencyChain) {
        AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = walletService.getApplicationWallet(applicationId, currencyId, currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),aupayDigitalCurrencyWallet);
    }


    @RequestMapping("getUserWalletByUserId")
    @Internal
    public Result<List<AupayDigitalCurrencyWallet>> getUserWalletByUserId(@RequestParam String userId) {
        List<AupayDigitalCurrencyWallet> list = walletService.getUserWalletByUserId(userId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }
    @RequestMapping("getApplicationWalletByAppId")
    @Internal
    public Result getApplicationWalletByAppId(@RequestParam String applicationId) {
        List<AupayDigitalCurrencyWallet> list = walletService.getApplicationWalletByApplicationId(applicationId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),list);
    }
    @RequestMapping("getWithdrawWallet")
    @Internal
    public Result<AupayDigitalCurrencyWallet> getWithdrawWallet(@RequestParam("currencyId") Integer currencyId, @RequestParam("currencyChain") Integer currencyChain) {
        AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = walletService.getWithdrawWallet(currencyId, currencyChain);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),aupayDigitalCurrencyWallet);
    }

//    @RequestMapping("addBalance")
//    @Internal
//    public Result addBalance(@RequestParam("walletId") String walletId, @RequestParam("amount") BigDecimal amount) {
//        walletService.addBalance(walletId,amount);
//        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
//    }

//    @RequestMapping("testTransaction")
//    @Internal
//    public Result testTransaction() {
//        walletService.testTransaction();
//        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
//    }

}
