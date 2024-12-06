package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.po.WithdrawTask;
import com.aucloud.aupay.wallet.orm.mapper.WithdrawTaskMapper;
import com.aucloud.pojo.dto.WithdrawDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 提币任务表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class WithdrawTaskService extends ServiceImpl<WithdrawTaskMapper, WithdrawTask> implements IService<WithdrawTask> {

    public void saveWithdrawTask(WithdrawDTO withdrawDTO) {
        WithdrawTask task = new WithdrawTask();
        task.setAmount(withdrawDTO.getAmount());
        task.setFee(withdrawDTO.getFee());
        task.setCurrencyId(withdrawDTO.getCurrencyId());
        task.setCurrencyChain(withdrawDTO.getCurrencyChain());
        task.setToAddress(withdrawDTO.getToAddress());
        task.setTradeNo(withdrawDTO.getTradeNo());
        task.setCreateTime(new Date());
        save(task);
    }
}
