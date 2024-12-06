package com.aucloud.aupay.wallet.dao;

import com.aucloud.aupay.mybatis.Pager;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.*;
import com.aucloud.aupay.pojo.vo.ApplicationWithdrawWalletVo;
import com.aucloud.aupay.pojo.vo.UserWalletVo;

import java.util.List;

public interface WalletDao {

//    AupayDigitalCurrencyWallet getUserWallet(String userId, Integer currencyId, Integer currencyChain);

    AupayUserChainWallet saveChainWallet(Integer chainId, String address);
    int saveDigitalCurrencyWallet(AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet);

//    List<AupayDigitalCurrencyWallet> getTransferWalletWalletInfo();

//    List<AupayDigitalCurrencyWallet> getReserveWalletInfo();


//    List<AupayDigitalCurrencyWallet> getWithdrawWalletInfo();
//    List<FeeWalletVo> getApplicaitonFeeWalletInfo();
    List<AupayDigitalCurrencyWallet> getApplicaitonAssetsWalletInfo();
    List<ApplicationWithdrawWalletVo> getApplicaitonWithdrawWalletInfo();

    List<AupayWalletTransferRecord> findWalletTransferRecord(Pager pager);

//    int addBalance(String walletId, BigDecimal amount);
//    int addFeeBalance(String walletId, BigDecimal amount);

//    int bindUserWallet(String walletId, String userId);
//    int bindApplicationWallet(String walletId, String application);

//    AupayDigitalCurrencyWallet getUserDigitalCurrencyWallet(String userId, Integer currencyId, Integer currencyChain);
//    AupayDigitalCurrencyWallet getApplicationCurrencyWallet(String applicationId, Integer currencyId, Integer currencyChain);
    AupayDigitalCurrencyWallet getUserWalletByCurrencyChain(String userId, Integer currencyId, Integer currencyChain);
    AupayDigitalCurrencyWallet getWalletById(String walletId);
    List<AupayDigitalCurrencyWallet> getUserWalletByUserId(String userId);
    List<AupayDigitalCurrencyWallet> getUserWalletByCurrencyChain(Integer currencyId, Integer chainId);

//    AupayDigitalCurrencyWallet getWalletById4Upd(String walletId);

//    AupayUserWalletRelation getWalletUserRelation(String walletId);

//    AupayDigitalCurrencyWallet getWithdrawWallet(Integer currencyId, Integer currencyChain);
    int clearUserAssetsWalletRelation(String userId, Integer currencyId, Integer currencyChain);
    int checkPendingSupplementFeeRecord(String walletId);
    int checkPendingTransferAssets(String walletId);

    int checkPendingCollectUserAssets(String message);

    int checkPendingSupplementWithdrawAddressAssetsRecord(String walletId);

    int saveAupayWalletTransferRecord(AupayWalletTransferRecord aupayWalletTransferRecord);

//    AupayDigitalCurrencyWallet getTransferWallet(Integer currencyId, Integer currencyChain);

//    AupayDigitalCurrencyWallet getFeeWallet(Integer currencyId, Integer currencyChain);

    int saveAupayCollectionUserAssetsRecord(AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord);

    AupayCollectionUserAssetsRecord getAupayCollectionUserAssetsRecord(String id);

    int updateAupayCollectionUserAssetsRecord(AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord);

    int saveAupaySupplementFeeRecord(AupaySupplementFeeRecord aupaySupplementFeeRecord);

    int updateAupayWalletTransferRecord(AupayWalletTransferRecord aupayWalletTransferRecordUpd);

    int updateApplicationWithdrawWalletConfig(AupayApplicationWithdrawWalletConfig aupayApplicationWithdrawWalletConfig);

    List<UserWalletVo> findUserWalletList(Pager pager);

    int updateAupaySupplementFeeRecord(AupaySupplementFeeRecord aupaySupplementFeeRecord);

    List<AupayWalletTransferRecord> getPendingWalletTransferRecord();

//    List<String> findUserWalletIdGreaterThan(BigDecimal weekAutoTriggerAmount);

    int updateAupayReserveTransferWalletRecord(AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord);

    int saveAupayReserveTransferWalletRecord(AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord);

//    AupayDigitalCurrencyWallet getReserveWallet(Integer currencyId, Integer currencyChain);

    int saveAupaySupplementWithdrawWalletRecord(AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord);
    int updateAupaySupplementWithdrawWalletRecord(AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord);

//    int saveAupayApplicationWalletRelation(AupayApplicationAssetsWalletRelation aupayApplicationAssetsWalletRelation) ;

    void testTransaction();

//    List<AupayDigitalCurrencyWallet> getApplicationWalletByApplicationId( String applicationId);
//    AupayDigitalCurrencyWallet getApplicationWallet( String applicationId, Integer currencyId, Integer currencyChain);
}
