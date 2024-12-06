package com.aucloud.aupay.wallet.dao.mapper;

import com.aucloud.aupay.pojo.do_.AupaySupplementFeeRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AupaySupplementFeeRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AupaySupplementFeeRecord record);

    int insertSelective(AupaySupplementFeeRecord record);

    AupaySupplementFeeRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AupaySupplementFeeRecord record);

    int updateByPrimaryKey(AupaySupplementFeeRecord record);

    int checkPendingSupplementFeeRecord(String walletId);
}