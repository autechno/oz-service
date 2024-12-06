package com.aucloud.aupay.wallet.dao.mapper;


import com.aucloud.aupay.mybatis.Pager;
import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AupayWalletTransferRecordMapper {

    int deleteByPrimaryKey(String id);

    int insert(AupayWalletTransferRecord record);

    int insertSelective(AupayWalletTransferRecord record);

    AupayWalletTransferRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AupayWalletTransferRecord record);

    int updateByPrimaryKey(AupayWalletTransferRecord record);

    List<AupayWalletTransferRecord> findWalletTransferRecord(Pager pager);

    List<AupayWalletTransferRecord> getPendingWalletTransferRecord();
}