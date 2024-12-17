package com.aucloud.aupay.wallet.controller;

import com.aucloud.aupay.db.orm.po.FastSwapRecord;
import com.aucloud.aupay.wallet.orm.service.FastSwapRecordService;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.PageQuery;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.pojo.dto.FastSwapRecordQuery;
import com.aucloud.commons.pojo.vo.FastSwapRecordDetailVo;
import com.aucloud.commons.utils.Tools;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("trade")
public class FastSwapController {

    @Autowired
    private FastSwapRecordService fastSwapRecordService;

    @PostMapping("generateFastSwapRecord")
    public Result<String> generateFastSwapRecord(@RequestBody FastSwapRecord fastSwapRecord) {
        String tradeNo = Tools.generateRandomString(32);
        fastSwapRecord.setTradeNo(tradeNo);
        fastSwapRecordService.save(fastSwapRecord);
        return Result.returnResult(ResultCodeEnum.SUCCESS, tradeNo);
    }

    @PostMapping("queryFastSwaps")
    public Result<Page<FastSwapRecord>> queryFastSwaps(@RequestBody PageQuery<FastSwapRecordQuery> pageQuery) {
        Page<FastSwapRecord> page = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize());
        LambdaQueryWrapper<FastSwapRecord> queryWrapper = new LambdaQueryWrapper<>();
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        queryWrapper.eq(FastSwapRecord::getAccountId, tokenInfo.getAccountId());
        queryWrapper.eq(FastSwapRecord::getAccountType, tokenInfo.getAccountType());
        FastSwapRecordQuery conditions = pageQuery.getConditions();
        Integer cashOutCurrencyId = conditions.getCashOutCurrencyId();
        if (cashOutCurrencyId != null) {
            queryWrapper.eq(FastSwapRecord::getCashOutCurrencyId, cashOutCurrencyId);
        }
        Integer cashOutCurrencyChain = conditions.getCashOutCurrencyChain();
        if (cashOutCurrencyChain != null) {
            queryWrapper.eq(FastSwapRecord::getCashOutChain, cashOutCurrencyChain);
        }
        Integer cashInCurrencyId = conditions.getCashInCurrencyId();
        if (cashInCurrencyId != null) {
            queryWrapper.eq(FastSwapRecord::getCashInCurrencyId, cashInCurrencyId);
        }
        Integer cashInCurrencyChain = conditions.getCashInCurrencyChain();
        if (cashInCurrencyChain != null) {
            queryWrapper.eq(FastSwapRecord::getCashInChain, cashInCurrencyChain);
        }
        Date startTime = conditions.getStartTime();
        if (startTime != null) {
            queryWrapper.ge(FastSwapRecord::getCreateTime, startTime);
        }
        Date endTime = conditions.getEndTime();
        if (endTime != null) {
            queryWrapper.le(FastSwapRecord::getCreateTime, endTime);
        }
        Page<FastSwapRecord> page1 = fastSwapRecordService.page(page, queryWrapper);
        return Result.returnResult(ResultCodeEnum.SUCCESS, page1);
    }

    @GetMapping("getFastSwapDetail")
    public Result<FastSwapRecordDetailVo> getFastSwapDetail(@RequestParam("id") Long id) {
        FastSwapRecord record = fastSwapRecordService.getOptById(id).orElseThrow(() -> new ServiceRuntimeException(ResultCodeEnum.NOT_FOUND));
        FastSwapRecordDetailVo detail = new FastSwapRecordDetailVo();
        BeanUtils.copyProperties(record, detail);
        //detail.setId(0L);
        return Result.returnResult(ResultCodeEnum.SUCCESS, detail);
    }
}
