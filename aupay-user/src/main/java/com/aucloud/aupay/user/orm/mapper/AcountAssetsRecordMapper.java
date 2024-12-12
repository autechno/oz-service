package com.aucloud.aupay.user.orm.mapper;

import com.aucloud.aupay.user.orm.po.AccountAssetsRecord;
import com.aucloud.commons.pojo.dto.AccountAssetsRecordQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资金流水表 Mapper 接口
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
public interface AcountAssetsRecordMapper extends BaseMapper<AccountAssetsRecord> {


    Page<AccountAssetsRecord> getAssetsRecords(Page<AccountAssetsRecord> page, @Param("conditions") AccountAssetsRecordQuery recordQuery);

}
