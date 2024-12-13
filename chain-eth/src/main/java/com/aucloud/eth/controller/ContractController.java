package com.aucloud.eth.controller;

import com.aucloud.commons.constant.CurrencyEnum;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.eth.service.AupayWalletManagerService;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.WithdrawBatchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("contract")
public class ContractController {

    @Autowired
    private AupayWalletManagerService aupayWalletManagerService;

    @GetMapping("getAllUserWallets")
    public Result<List<String>> getAllUserWallets() throws Exception {
        List<String> allUserWallets = aupayWalletManagerService.getAllUserWallets();
        return Result.returnResult(ResultCodeEnum.SUCCESS, allUserWallets);
    }
    @GetMapping("generateUserWalletsBatch")
    public Result<List<String>> generateUserWalletsBatch(@RequestParam("count") int count) throws Exception {
        List<String> allUserWallets = aupayWalletManagerService.createUserWallet(count);
        return Result.returnResult(ResultCodeEnum.SUCCESS, allUserWallets);
    }

    @RequestMapping("recycleUserWallet")
    Result<?> recycleUserWallet(@RequestParam("address") String address) throws Exception {
        String hash = aupayWalletManagerService.recycleUserWallet(address);
        return Result.returnResult(ResultCodeEnum.SUCCESS, hash);
    }

    @GetMapping("generateOperatorWallet")
    public Result<String> generateOperatorWallet() {
        String operator = aupayWalletManagerService.generateOperator();
        return Result.returnResult(ResultCodeEnum.SUCCESS, operator);
    }

    @RequestMapping("getBalance")
    public Result<BigDecimal> getBalance(@RequestParam("address") String address, @RequestParam("currencyId") Integer currencyId) throws Exception {
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        BigDecimal balance = aupayWalletManagerService.getWalletBalance(address, currencyEnum);
        log.info("getBalance. address:{} currencyId:{} . balance {}", address, currencyId, balance);
        if (balance == null) {
            throw new RuntimeException("查询余额失败");
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),balance);
    }

    @PostMapping("withdrawBatch")
    public Result<String> withdrawBatch(@RequestBody WithdrawBatchDto dto) throws Exception {
        String txHash = aupayWalletManagerService.withdrawBatch(dto);
        return Result.returnResult(ResultCodeEnum.SUCCESS,txHash);
    }

    @PostMapping("user2collect")
    public Result<String> user2collect(@RequestParam("currencyId") Integer currencyId, @RequestParam("limit") BigDecimal limit) throws Exception {
        String txHash = "";
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        try {
            txHash = aupayWalletManagerService.user2collect(currencyEnum, limit);
        } catch (Exception e) {
            log.error("", e);
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS,txHash);
    }

    @PostMapping("collect2withdraw")
    public Result<String> collect2withdraw(@RequestParam("currencyId") Integer currencyId, @RequestParam("limit") BigDecimal limit) throws Exception {
        String txHash = "";
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        try {
            txHash = aupayWalletManagerService.collect2withdraw(currencyEnum, limit);
        } catch (Exception e) {
            log.error("", e);
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS,txHash);
    }

    @PostMapping("collect2store")
    public Result<String> collect2store(@RequestParam("storeWallet") String storeWallet, @RequestParam("currencyId") Integer currencyId, @RequestParam("limit") BigDecimal limit) throws Exception {
        String txHash = "";
        CurrencyEnum currencyEnum = CurrencyEnum.findById(currencyId);
        try {
            txHash = aupayWalletManagerService.collect2storage(storeWallet, currencyEnum, limit);
        } catch (Exception e) {
            log.error("", e);
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS,txHash);
    }
}
