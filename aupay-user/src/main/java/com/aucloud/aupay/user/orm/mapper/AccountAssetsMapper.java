package com.aucloud.aupay.user.orm.mapper;

import com.aucloud.aupay.user.orm.po.AccountAssets;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 账户资产表 Mapper 接口
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@TableName("account_assets")
public interface AccountAssetsMapper extends BaseMapper<AccountAssets> {

}
