package com.aucloud.aupay.user.controller;

import com.aucloud.aupay.user.feign.FeignWalletService;
import com.aucloud.aupay.user.orm.po.AccountAssets;
import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.aupay.user.service.AssetsService;
import com.aucloud.aupay.user.service.FastSwapService;
import com.aucloud.aupay.validate.annotations.Operation;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.pojo.dto.*;
import com.aucloud.commons.pojo.vo.AccountAssetsVo;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("assets")
public class AssetsController {

    @Autowired
    private AssetsService assetsService;
    @Autowired
    private FeignWalletService feignWalletService;
    @Autowired
    private FastSwapService fastSwapService;

//    @PostMapping("/withdraw/pre-deduct")
//    public Result<String> preDeduct(@RequestBody WithdrawDTO withdrawDTO) {
//        String tradeNo = assetsService.preDeduct(withdrawDTO);
//        return Result.returnResult(ResultCodeEnum.SUCCESS, tradeNo);
//    }

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
    public Result<List<AccountAssetsVo>> getAssetsInfo(@RequestParam(value = "currencyId", required = false) Integer currencyId,
                                                       @RequestParam(value = "currencyChain", required = false) Integer currencyChain) {
        TokenInfo tokenInfoObject = UserRequestHeaderContextHandler.getTokenInfo();
        Long accountId = tokenInfoObject.getAccountId();
        Integer accountType = tokenInfoObject.getAccountType();
        AccountAssetsQuery query = new AccountAssetsQuery();
        query.setAccountId(accountId);
        query.setAccountType(accountType);
        query.setCurrencyId(currencyId);
        query.setCurrencyChain(currencyChain);
        List<AccountAssets> accountAssets = assetsService.getAccountAssets(query);
        Result<List<AccountChainWalletDto>> accountWallets = feignWalletService.getAccountWallets(accountId, accountType);
        List<AccountChainWalletDto> wallets = accountWallets.getData();
        Map<Integer, String> walletmaps = wallets.stream().collect(Collectors.toMap(AccountChainWalletDto::getCurrencyChain, AccountChainWalletDto::getWalletAddress));
        List<AccountAssetsVo> list = accountAssets.stream().map(o -> {
            AccountAssetsVo vo = new AccountAssetsVo();
            BeanUtils.copyProperties(o, vo);
            BigDecimal balance = o.getBalance() == null ? BigDecimal.ZERO : o.getBalance();
            BigDecimal freezeBalance = o.getFreezeBalance() == null ? BigDecimal.ZERO : o.getFreezeBalance();
            vo.setBalance(balance);
            vo.setFreezeBalance(freezeBalance);
            vo.setTotalBalance(balance.add(freezeBalance));
            vo.setWalletAddress(walletmaps.get(o.getCurrencyChain()));
            return vo;
        }).toList();
        return Result.returnResult(ResultCodeEnum.SUCCESS,list);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @RequestMapping(value = "getAssetsRecords",method = RequestMethod.GET)
    public Result<Page<AccountAssetsRecord>> getAssetsRecords(@RequestBody PageQuery<AccountAssetsRecordQuery> pageQuery) {
        TokenInfo tokenInfoObject = UserRequestHeaderContextHandler.getTokenInfo();
        Long accountId = tokenInfoObject.getAccountId();
        Integer accountType = tokenInfoObject.getAccountType();
        AccountAssetsRecordQuery conditions = pageQuery.getConditions();
        conditions.setAccountId(accountId);
        conditions.setAccountType(accountType);
        Page<AccountAssetsRecord> assetsRecords = assetsService.getAssetsRecords(pageQuery);
        List<AccountAssetsRecord> records = assetsRecords.getRecords();
        records.forEach(a -> {
            String tradeNo = a.getTradeNo();
            PageQuery<WithdrawRecordQuery> query = new PageQuery<>();
            WithdrawRecordQuery wdconditions = new WithdrawRecordQuery();
            wdconditions.setAccountId(accountId);
            wdconditions.setAccountType(accountType);
            wdconditions.setTradeNo(tradeNo);
            wdconditions.setAssetsId(a.getAssetsId());
            query.setConditions(wdconditions);
            feignWalletService.listWithdrawRecord(query);
        });
        return Result.returnResult(ResultCodeEnum.SUCCESS,assetsRecords);
    }

    //闪兑
    @RequestMapping(value = "fastSwap",method = RequestMethod.POST)
    @Operation(operation = OperationEnum.FAST_SWAP, verifyMethods = {VerifyMethod.ASSETSPASSWORD,VerifyMethod.GOOGLEAUTHENICATOR})
    public Result<Boolean> fastSwap(@RequestBody FastSwapDTO fastSwapDTO) {
        TokenInfo tokenInfoObject = UserRequestHeaderContextHandler.getTokenInfo();
        Long accountId = tokenInfoObject.getAccountId();
        Integer accountType = tokenInfoObject.getAccountType();
        Long userId = tokenInfoObject.getUserId();
        fastSwapDTO.setAccountId(accountId);
        fastSwapDTO.setAccountType(accountType);
        fastSwapDTO.setEmployeeId(userId);
        fastSwapService.fastSwap(fastSwapDTO);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),true);
    }
}
