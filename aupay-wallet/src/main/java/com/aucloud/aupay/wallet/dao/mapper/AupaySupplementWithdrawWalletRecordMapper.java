package com.aucloud.aupay.wallet.dao.mapper;


import com.aucloud.aupay.pojo.do_.AupaySupplementWithdrawWalletRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AupaySupplementWithdrawWalletRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AupaySupplementWithdrawWalletRecord record);

    int insertSelective(AupaySupplementWithdrawWalletRecord record);

    AupaySupplementWithdrawWalletRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AupaySupplementWithdrawWalletRecord record);

    int updateByPrimaryKey(AupaySupplementWithdrawWalletRecord record);

    int checkPendingSupplementWithdrawAddressAssetsRecord(String walletId);

    void testTransaction();
}