package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.orm.po.WalletTransferRecord;
import com.aucloud.aupay.wallet.orm.mapper.WalletTransferRecordMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 链上交易流水表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Service
public class WalletTransferRecordService extends ServiceImpl<WalletTransferRecordMapper, WalletTransferRecord> implements IService<WalletTransferRecord> {

}
