package com.aucloud.aupay.user.feign;

import com.aucloud.aupay.db.orm.po.FastSwapRecord;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.AccountChainWalletDto;
import com.aucloud.commons.pojo.dto.WithdrawDTO;
import com.aucloud.commons.pojo.dto.WithdrawRecordQuery;
import com.aucloud.commons.pojo.vo.WalletTransferRecordVo;
import com.aucloud.commons.pojo.vo.WithdrawRecordVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "aupay-wallet")
public interface FeignWalletService {

    @PostMapping("/withdraw/generateWithdrawTask")
    Result<?> generateWithdrawTask(@RequestBody WithdrawDTO withdrawDTO);

    @RequestMapping(value = "wallet/getAccountWallets",method = RequestMethod.GET)
    Result<List<AccountChainWalletDto>> getAccountWallets(@RequestParam Long accountId, @RequestParam Integer accountType);

    @RequestMapping("wallet/generateAccountWallet")
    Result<List<AccountChainWalletDto>> generateAccountWallet(@RequestParam Long accountId, @RequestParam Integer accountType);

    @PostMapping("/trade/generateFastSwapRecord")
    Result<String> generateFastSwapRecord(@RequestBody FastSwapRecord fastSwapRecord);

    @PostMapping("withdraw/listWithdrawRecord")
    Result<Page<WithdrawRecordVo>> listWithdrawRecord(@RequestBody PageQuery<WithdrawRecordQuery> query);

    @GetMapping("transfer/getTransferRecordsByTradeNo")
    Result<List<WalletTransferRecordVo>> getTransferRecordsByTradeNo(@RequestParam(value = "tradeNo") String tradeNo);
}
