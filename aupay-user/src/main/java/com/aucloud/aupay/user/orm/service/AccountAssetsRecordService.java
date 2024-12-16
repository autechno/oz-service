package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.aupay.user.orm.mapper.AccountAssetsRecordMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资金流水表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class AccountAssetsRecordService extends ServiceImpl<AccountAssetsRecordMapper, AccountAssetsRecord> implements IService<AccountAssetsRecord> {

}
