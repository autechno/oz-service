package com.aucloud.aupay.wallet.dao.mapper;


import com.aucloud.aupay.pojo.do_.AupayReserveTransferWalletRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AupayReserveTransferWalletRecordMapper {

    int deleteByPrimaryKey(String id);

    int insert(AupayReserveTransferWalletRecord record);

    int insertSelective(AupayReserveTransferWalletRecord record);

    AupayReserveTransferWalletRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AupayReserveTransferWalletRecord record);

    int updateByPrimaryKey(AupayReserveTransferWalletRecord record);

    int checkPendingTransferAssets(String walletId);
}