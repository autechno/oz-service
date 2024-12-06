package com.aucloud.eth.controller;

import com.aucloud.constant.CurrencyEnum;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.entity.TxInfo;
import com.aucloud.eth.contracts.WalletManagerContract;
import com.aucloud.eth.service.AupayWalletManagerService;
import com.aucloud.pojo.Result;
import com.aucloud.eth.service.RpcService;
import com.aucloud.eth.service.ScanTransactionService;
import com.aucloud.eth.service.TransferService;
import com.aucloud.pojo.dto.WithdrawBatchDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("")
public class IndexController {

    @Autowired
    private RpcService rpcService;
    @Autowired
    private TransferService transferService;
    @Autowired
    private ScanTransactionService scanTransactionService;
    @Autowired
    private AupayWalletManagerService aupayWalletManagerService;

//    @RequestMapping("createWallet")
//    public Result<String> createWallet() {
//        AupayDigitalCurrencyWallet wallet = rpcService.createWallet();
//        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),wallet.getAddress());
//    }
//    @RequestMapping("importWallet")
//    public Result importWallet(@RequestParam("address") String address,@RequestParam("privateKey") String privateKey) {
//        rpcService.importWallet(address, privateKey);
//        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),null);
//    }

    @RequestMapping("transfer")
    public Result<String> transfer(@RequestParam("currencyId") Integer currencyId,
                           @RequestParam("fromAddress") String fromAddress,
                           @RequestParam("toAddress") String toAddress,
                           @RequestParam("amount") BigDecimal amount) {
        log.info("transfer from {} to {}, amount:{}, currencyId:{}", fromAddress, toAddress, amount, currencyId);
        String transHash = transferService.transferToken(fromAddress,toAddress,amount, currencyId);
        if (transHash == null || transHash.isEmpty()) {
            return Result.returnResult(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getLabel_zh_cn());
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),transHash);
    }

    @RequestMapping("getTxInfo")
    public Result<TxInfo> getTxInfo(@RequestParam("currencyId") Integer currencyId, @RequestParam("txId") String txId) {
        log.info("getTxInfo currencyId={} txId={}", currencyId, txId);
        TxInfo txInfo = rpcService.getTxInfo(currencyId,txId);
        if (txInfo == null) {
            return Result.error(ResultCodeEnum.NOT_FOUND.getLabel_zh_cn(), ResultCodeEnum.NOT_FOUND.getCode());
        }
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(), txInfo);
    }

    @RequestMapping("getBalance")
    public Result<BigDecimal> getBalance(@RequestParam("address") String address,@RequestParam("currencyId") Integer currencyId) throws Exception {
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

    @RequestMapping("")
    public String index() {
        return "index";
    }

    @RequestMapping("pushTransaction")
    public Result<?> pushTrans(@RequestParam("txId") String txId) throws Exception {
        scanTransactionService.pushTranscation(txId);
        return Result.returnResult(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getLabel_zh_cn(),true);
    }

}
