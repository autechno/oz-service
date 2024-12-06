package com.aucloud.aupay.wallet.dao.mapper;


import com.aucloud.aupay.pojo.do_.AupayCollectionUserAssetsRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AupayCollectionUserAssetsRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(AupayCollectionUserAssetsRecord record);

    int insertSelective(AupayCollectionUserAssetsRecord record);

    AupayCollectionUserAssetsRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AupayCollectionUserAssetsRecord record);

    int updateByPrimaryKey(AupayCollectionUserAssetsRecord record);

    int checkPendingCollectUserAssets(String walletId);
}