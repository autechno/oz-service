package com.aucloud.aupay.wallet.dao.mapper;//package com.aucloud.aupay.dao.mapper;
//
//
//import com.aucloud.aupay.pojo.do_.AupayUserWalletRelation;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//@Mapper
//public interface AupayUserWalletRelationMapper {
//
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(AupayUserWalletRelation record);
//
//    int insertSelective(AupayUserWalletRelation record);
//
//    AupayUserWalletRelation selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(AupayUserWalletRelation record);
//
//    int updateByPrimaryKey(AupayUserWalletRelation record);
//
//    AupayUserWalletRelation getWalletUserRelation(@Param("walletId") String walletId);
//
//    int clearUserAssetsWalletRelation(@Param("userId") String userId, @Param("currencyId") Integer currencyId, @Param("currencyChain") Integer currencyChain);
//}