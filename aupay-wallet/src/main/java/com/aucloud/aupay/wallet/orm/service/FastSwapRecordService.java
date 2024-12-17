package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.db.orm.po.FastSwapRecord;
import com.aucloud.aupay.wallet.orm.mapper.FastSwapRecordMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 闪兑记录表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-17
 */
@Service
public class FastSwapRecordService extends ServiceImpl<FastSwapRecordMapper, FastSwapRecord> implements IService<FastSwapRecord> {

}
