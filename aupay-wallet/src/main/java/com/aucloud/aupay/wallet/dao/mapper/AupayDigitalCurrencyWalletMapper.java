package com.aucloud.aupay.wallet.dao.mapper;//package com.aucloud.aupay.dao.mapper;
//
//
//import com.aucloud.aupay.mybatis.Pager;
//import com.aucloud.aupay.pojo.do_.AupayDigitalCurrencyWallet;
//import com.aucloud.aupay.pojo.do_.AupayWalletTransferRecord;
//import com.aucloud.aupay.pojo.vo.ApplicationWithdrawWalletVo;
//import com.aucloud.aupay.pojo.vo.FeeWalletVo;
//import com.aucloud.aupay.pojo.vo.UserWalletVo;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Mapper
//public interface AupayDigitalCurrencyWalletMapper {
//
//    int deleteByPrimaryKey(String walletId);
//
//    int insert(AupayDigitalCurrencyWallet record);
//
//    int insertSelective(AupayDigitalCurrencyWallet record);
//
//    AupayDigitalCurrencyWallet selectByPrimaryKey(String walletId);
//
//    int updateByPrimaryKeySelective(AupayDigitalCurrencyWallet record);
//
//    int updateByPrimaryKey(AupayDigitalCurrencyWallet record);
//
//    AupayDigitalCurrencyWallet getUserWallet(@Param("userId") String userId, @Param("currencyId") Integer currencyId, @Param("currencyChain") Integer currencyChain);
//
//    List<AupayDigitalCurrencyWallet> getTransferWalletWalletInfo();
//
//    List<AupayDigitalCurrencyWallet> getReserveWalletInfo();
//
//    List<AupayDigitalCurrencyWallet> getWithdrawWalletInfo();
//
//    List<FeeWalletVo> getApplicaitonFeeWalletInfo();
//
//    List<AupayDigitalCurrencyWallet> getApplicaitonAssetsWalletInfo();
//
//    int addBalance(@Param("walletId") String walletId, @Param("amount") BigDecimal amount);
//
//    List<ApplicationWithdrawWalletVo> getApplicaitonWithdrawWalletInfo();
//
//    AupayDigitalCurrencyWallet getWithdrawWallet(@Param("currencyId") Integer currencyId, @Param("currencyChain") Integer currencyChain);
//
//    AupayDigitalCurrencyWallet getTransferWallet(@Param("currencyId") Integer currencyId, @Param("currencyChain") Integer currencyChain);
//
//    AupayDigitalCurrencyWallet getFeeWallet(@Param("currencyId") Integer currencyId, @Param("currencyChain") Integer currencyChain);
//
//    AupayDigitalCurrencyWallet getWalletById4Upd(@Param("walletId") String walletId);
//
//    List<UserWalletVo> findUserWalletList(Pager pager);
//
//    int addFeeBalance(@Param("walletId") String walletId, @Param("amount") BigDecimal amount);
//
//    List<AupayDigitalCurrencyWallet> findAllUserWalletList(@Param("currencyId") Integer currencyId, @Param("chainId") Integer chainId);
//
//    List<String> findUserWalletIdGreaterThan(BigDecimal weekAutoTriggerAmount);
//
//    AupayDigitalCurrencyWallet getReserveWallet(@Param("currencyId") Integer currencyId, @Param("currencyChain") Integer currencyChain);
//
//    List<AupayDigitalCurrencyWallet> getApplicationWalletByApplicationId(@Param("applicationId") String applicationId);
//    List<AupayDigitalCurrencyWallet> getWalletByUserId(@Param("userId") String userId);
//
//    AupayDigitalCurrencyWallet getApplicationWallet(@Param("applicationId") String applicationId,@Param("currencyId") Integer currencyId,@Param("currencyChain") Integer currencyChain);
//
//}