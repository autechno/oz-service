package com.aucloud.aupay.wallet.dao.impl;

import com.aucloud.aupay.constant.WalletUseWay;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.dao.mapper.*;
import com.aucloud.aupay.exception.ServiceRuntimeException;
import com.aucloud.aupay.mybatis.Pager;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.*;
import com.aucloud.aupay.pojo.vo.ApplicationWithdrawWalletVo;
import com.aucloud.aupay.pojo.vo.UserWalletVo;
import com.aucloud.aupay.utils.IdGenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class WalletDaoImpl implements WalletDao {

//    @Autowired
//    private AupayDigitalCurrencyWalletMapper aupayDigitalCurrencyWalletMapper;
    //    @Autowired
//    private AupayUserWalletRelationMapper aupayUserWalletRelationMapper;

    @Autowired
    private AupayWalletTransferRecordMapper aupayWalletTransferRecordMapper;

    @Autowired
    private AupaySupplementFeeRecordMapper aupaySupplementFeeRecordMapper;

    @Autowired
    private AupayReserveTransferWalletRecordMapper aupayReserveTransferWalletRecordMapper;

    @Autowired
    private AupayCollectionUserAssetsRecordMapper aupayCollectionUserAssetsRecordMapper;

    @Autowired
    private AupaySupplementWithdrawWalletRecordMapper aupaySupplementWithdrawWalletRecordMapper;

    @Autowired
    private AupayApplicationWithdrawWalletConfigMapper aupayApplicationWithdrawWalletConfigMapper;

    @Autowired
    private AupayApplicationAssetsWalletRelationMapper aupayApplicationAssetsWalletRelationMapper;

    @Autowired
    private AupayUserChainWalletMapper aupayUserChainWalletMapper;
    @Autowired
    private AupayUserCurrencyWalletMapper aupayUserCurrencyWalletMapper;

    public AupayUserChainWallet saveChainWallet(Integer chainId, String address) {
        AupayUserChainWallet entity0 = new AupayUserChainWallet();
        entity0.setAddress(address);
        entity0.setCurrencyChain(chainId);
        entity0.setFeeBalance(BigDecimal.ZERO);
        entity0.setIsDel(Boolean.FALSE);
        entity0.setCreateTime(new Date());
//        entity0.setUseWay(aupayDigitalCurrencyWallet.getUseWay());
        aupayUserChainWalletMapper.insert(entity0);
        return entity0;
    }
    @Override
    public int saveDigitalCurrencyWallet(AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet) {
        AupayUserCurrencyWallet entity1 = new AupayUserCurrencyWallet();
        entity1.setCurrencyId(aupayDigitalCurrencyWallet.getCurrencyId());
        entity1.setCurrencyChain(aupayDigitalCurrencyWallet.getCurrencyChain());
//        entity1.setBalance(aupayDigitalCurrencyWallet.getBalance());
        entity1.setIsDel(aupayDigitalCurrencyWallet.getIsDel());
        entity1.setCreateTime(new Date());
        entity1.setUserId(aupayDigitalCurrencyWallet.getUserId());
        entity1.setUseWay(aupayDigitalCurrencyWallet.getUseWay());
        entity1.setWalletChainId(aupayDigitalCurrencyWallet.getWalletChainId());
        aupayUserCurrencyWalletMapper.insert(entity1);
        aupayDigitalCurrencyWallet.setWalletId(entity1.getId());
        return 1;
    }

//    @Override
//    public int addBalance(String walletId, BigDecimal amount) {
//        if (StringUtils.isBlank(walletId)) {
//            return 0;
//        }
//        int id = Integer.parseInt(walletId);
//        AupayUserCurrencyWallet aupayUserCurrencyWallet = aupayUserCurrencyWalletMapper.selectById(id);
//        AupayUserCurrencyWallet entity = new AupayUserCurrencyWallet();
//        entity.setId(id);
//        BigDecimal balance = aupayUserCurrencyWallet.getBalance();
//        balance = balance == null ? BigDecimal.ZERO : balance;
//        entity.setBalance(balance.add(amount));
//        aupayUserCurrencyWalletMapper.updateById(entity);
//        return 1;
//    }

//    @Override
//    public int addFeeBalance(String walletId, BigDecimal amount) {
//        if (StringUtils.isBlank(walletId)) {
//            return 0;
//        }
//        int id = Integer.parseInt(walletId);
//        AupayUserCurrencyWallet aupayUserCurrencyWallet = aupayUserCurrencyWalletMapper.selectById(id);
//        BigDecimal feeBalance = aupayUserCurrencyWallet.getFeeBalance();
//        feeBalance = feeBalance==null?BigDecimal.ZERO: feeBalance;
//        AupayUserCurrencyWallet entity = new AupayUserCurrencyWallet();
//        entity.setId(id);
//        entity.setFeeBalance(feeBalance.add(amount==null?BigDecimal.ZERO:amount));
//        aupayUserCurrencyWalletMapper.updateById(entity);
//        return 1;
//    }

//    @Override
//    public List<FeeWalletVo> getApplicaitonFeeWalletInfo() {
//        return aupayDigitalCurrencyWalletMapper.getApplicaitonFeeWalletInfo();
//    }
    @Override
    public List<AupayDigitalCurrencyWallet> getApplicaitonAssetsWalletInfo() {
        List<AupayDigitalCurrencyWallet> digitalCurrencyWalletList = new ArrayList<>();
        List<AupayApplicationAssetsWalletRelation> relations = aupayApplicationAssetsWalletRelationMapper.selectList(new QueryWrapper<>());
        relations.forEach(r -> {
            AupayDigitalCurrencyWallet entity = getWalletById(r.getWalletId());
            digitalCurrencyWalletList.add(entity);
        });
        return digitalCurrencyWalletList;
    }
    @Override
    public List<ApplicationWithdrawWalletVo> getApplicaitonWithdrawWalletInfo() {
        List<ApplicationWithdrawWalletVo> applicationWithdrawWalletVos = new ArrayList<>();
        List<AupayApplicationWithdrawWalletConfig> configs = aupayApplicationWithdrawWalletConfigMapper.selectList(new QueryWrapper<>());
        configs.forEach(aupayApplicationWithdrawWalletConfig -> {
            String walletId = aupayApplicationWithdrawWalletConfig.getWalletId();
            AupayDigitalCurrencyWallet wallet = getWalletById(walletId);
            ApplicationWithdrawWalletVo vo = new ApplicationWithdrawWalletVo();
            vo.setAddress(wallet.getAddress());
//            vo.setBalance(wallet.getBalance());
//            vo.setFeeBalance(wallet.getFeeBalance());
            vo.setWalletId(walletId);
            vo.setCurrencyChain(wallet.getCurrencyChain());
            vo.setCurrencyId(wallet.getCurrencyId());
            vo.setSupplementAmount(aupayApplicationWithdrawWalletConfig.getSupplementAmount());
            vo.setSupplementTriggerAmount(aupayApplicationWithdrawWalletConfig.getSupplementTriggerAmount());
            vo.setWithdrawSettleTriggerAmount(aupayApplicationWithdrawWalletConfig.getWithdrawSettleTriggerAmount());
            applicationWithdrawWalletVos.add(vo);
        });
        return applicationWithdrawWalletVos;
    }

    private AupayDigitalCurrencyWallet toAupayDigitalCurrencyWallet(AupayUserCurrencyWallet currencyWallet, AupayUserChainWallet chainWallet) {
        AupayDigitalCurrencyWallet digitalCurrencyWallet = new AupayDigitalCurrencyWallet();
        digitalCurrencyWallet.setUserId(currencyWallet.getUserId());
        digitalCurrencyWallet.setCurrencyId(currencyWallet.getCurrencyId());
        digitalCurrencyWallet.setCurrencyChain(chainWallet.getCurrencyChain());
        digitalCurrencyWallet.setWalletChainId(chainWallet.getId());
//        digitalCurrencyWallet.setBalance(currencyWallet.getBalance());
//        digitalCurrencyWallet.setFeeBalance(chainWallet.getFeeBalance());
        digitalCurrencyWallet.setIsDel(currencyWallet.getIsDel());
        digitalCurrencyWallet.setCreateTime(chainWallet.getCreateTime());
        digitalCurrencyWallet.setUseWay(currencyWallet.getUseWay());
        digitalCurrencyWallet.setWalletId(currencyWallet.getId());
        digitalCurrencyWallet.setAddress(chainWallet.getAddress());
        return digitalCurrencyWallet;
    }

    @Override
    public AupayDigitalCurrencyWallet getUserWalletByCurrencyChain(String userId, Integer currencyId, Integer currencyChain) {
        QueryWrapper<AupayUserCurrencyWallet> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("currency_id", currencyId).eq("is_del", Boolean.FALSE);
        if (currencyChain != null) {
            query.eq("currency_chain", currencyChain);
        }
        AupayUserCurrencyWallet currencyWallet = aupayUserCurrencyWalletMapper.selectList(query).stream().findFirst().orElseThrow(()-> new ServiceRuntimeException("钱包不存在"));
        AupayUserChainWallet chainWallet = aupayUserChainWalletMapper.selectById(currencyWallet.getWalletChainId());
        return toAupayDigitalCurrencyWallet(currencyWallet, chainWallet);
    }

    @Override
    public AupayDigitalCurrencyWallet getWalletById(String walletId) {
        AupayUserCurrencyWallet currencyWallet = aupayUserCurrencyWalletMapper.selectById(Integer.parseInt(walletId));
        AupayUserChainWallet chainWallet = aupayUserChainWalletMapper.selectById(currencyWallet.getWalletChainId());
        return toAupayDigitalCurrencyWallet(currencyWallet, chainWallet);
    }

    @Override
    public List<AupayDigitalCurrencyWallet> getUserWalletByUserId(String userId) {
        List<AupayDigitalCurrencyWallet> list = new ArrayList<>();
        QueryWrapper<AupayUserCurrencyWallet> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("is_del", Boolean.FALSE);;
        List<AupayUserCurrencyWallet> walletList = aupayUserCurrencyWalletMapper.selectList(query);
        walletList.forEach(currencyWallet -> {
            AupayUserChainWallet chainWallet = aupayUserChainWalletMapper.selectById(currencyWallet.getWalletChainId());
            list.add(toAupayDigitalCurrencyWallet(currencyWallet, chainWallet));
        });
        return list;
    }

    /**
     * 查用户钱包
     */
    @Override
    public List<AupayDigitalCurrencyWallet> getUserWalletByCurrencyChain(Integer currencyId, Integer chainId) {
        List<AupayDigitalCurrencyWallet> list = new ArrayList<>();
        QueryWrapper<AupayUserCurrencyWallet> query = new QueryWrapper<>();
        query.eq("currency_id", currencyId).eq("currency_chain", chainId).eq("use_way", WalletUseWay.USER).eq("is_del", Boolean.FALSE);
        List<AupayUserCurrencyWallet> walletList = aupayUserCurrencyWalletMapper.selectList(query);
        walletList.forEach(currencyWallet -> {
            AupayUserChainWallet chainWallet = aupayUserChainWalletMapper.selectById(currencyWallet.getWalletChainId());
            list.add(toAupayDigitalCurrencyWallet(currencyWallet, chainWallet));
        });
        return list;
    }

    @Override
    public int clearUserAssetsWalletRelation(String userId, Integer currencyId, Integer currencyChain) {
        QueryWrapper<AupayUserCurrencyWallet> query = new QueryWrapper<>();
        query.eq("user_id", userId).eq("currency_id", currencyId).eq("currency_chain", currencyChain).eq("is_del", Boolean.FALSE);
        AupayUserCurrencyWallet entity = new AupayUserCurrencyWallet();
        entity.setIsDel(true);
        return aupayUserCurrencyWalletMapper.update(entity, query);
    }

    @Override
    public List<UserWalletVo> findUserWalletList(Pager pager) {
//        List<UserWalletVo> list = new ArrayList<>();
//        QueryWrapper<AupayUserCurrencyWallet> query = new QueryWrapper<>();
//        Map conditions = pager.getConditions();
//        if (conditions!=null&&!conditions.isEmpty()) {
//            if (conditions.containsKey("userId")) {
//                query.lambda().eq(AupayUserCurrencyWallet::getUserId, conditions.get("userId"));
//            }
//            if (conditions.containsKey("currencyId")) {
//                query.lambda().eq(AupayUserCurrencyWallet::getCurrencyId, conditions.get("currencyId"));
//            }
//        }
//        IPage<AupayUserCurrencyWallet> page = new Page<>(pager.getPageNo(), pager.getPageSize());
//        IPage<AupayUserCurrencyWallet> page1 = aupayUserCurrencyWalletMapper.selectPage(page, query);
//        page1.getRecords().forEach(wallet -> {
//            UserWalletVo vo = new UserWalletVo();
//            vo.setUserId(wallet.getUserId());
//            vo.setCurrencyId(wallet.getCurrencyId());
//            vo.setCurrencyChain(wallet.getCurrencyChain());
//            vo.setAddress("");
//            vo.setFeeBalance(wallet.getFeeBalance());
//            vo.setCreateTime(wallet.getCreateTime());
//            vo.setWalletId(wallet.getId().toString());
//            vo.setBalance(wallet.getBalance());
//            vo.setBindTime(wallet.getCreateTime());
//            list.add(vo);
//        });

        Map conditions = pager.getConditions();
        Page<UserWalletVo> userWalletList = aupayUserCurrencyWalletMapper.findUserWalletList(new Page<>(pager.getPageNo(), pager.getPageSize()), conditions);
        return userWalletList.getRecords();
    }

    @Override
    public List<AupayWalletTransferRecord> findWalletTransferRecord(Pager pager) {
        return aupayWalletTransferRecordMapper.findWalletTransferRecord(pager);
    }

    @Override
    public int checkPendingSupplementFeeRecord(String walletId) {
        return aupaySupplementFeeRecordMapper.checkPendingSupplementFeeRecord(walletId);
    }

    @Override
    public int checkPendingCollectUserAssets(String id) {
        return aupayCollectionUserAssetsRecordMapper.checkPendingCollectUserAssets(id);
    }

    @Override
    public int checkPendingSupplementWithdrawAddressAssetsRecord(String walletId) {
        return aupaySupplementWithdrawWalletRecordMapper.checkPendingSupplementWithdrawAddressAssetsRecord(walletId);
    }

    @Override
    public int saveAupayWalletTransferRecord(AupayWalletTransferRecord aupayWalletTransferRecord) {
        return aupayWalletTransferRecordMapper.insertSelective(aupayWalletTransferRecord);
    }

    @Override
    public int saveAupaySupplementWithdrawWalletRecord(AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord) {
        return aupaySupplementWithdrawWalletRecordMapper.insertSelective(aupaySupplementWithdrawWalletRecord);
    }

    @Override
    public int updateAupaySupplementWithdrawWalletRecord(AupaySupplementWithdrawWalletRecord aupaySupplementWithdrawWalletRecord) {
        return aupaySupplementWithdrawWalletRecordMapper.updateByPrimaryKeySelective(aupaySupplementWithdrawWalletRecord);
    }

    @Override
    public void testTransaction() {
        aupaySupplementWithdrawWalletRecordMapper.testTransaction();
    }

    @Override
    public int saveAupayCollectionUserAssetsRecord(AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord) {
        return aupayCollectionUserAssetsRecordMapper.insertSelective(aupayCollectionUserAssetsRecord);
    }

    @Override
    public AupayCollectionUserAssetsRecord getAupayCollectionUserAssetsRecord(String id) {
        return aupayCollectionUserAssetsRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateAupayCollectionUserAssetsRecord(AupayCollectionUserAssetsRecord aupayCollectionUserAssetsRecord) {
        return aupayCollectionUserAssetsRecordMapper.updateByPrimaryKeySelective(aupayCollectionUserAssetsRecord);
    }

    @Override
    public int saveAupaySupplementFeeRecord(AupaySupplementFeeRecord aupaySupplementFeeRecord) {
        return aupaySupplementFeeRecordMapper.insertSelective(aupaySupplementFeeRecord);
    }

    @Override
    public int updateAupayWalletTransferRecord(AupayWalletTransferRecord aupayWalletTransferRecordUpd) {
        return aupayWalletTransferRecordMapper.updateByPrimaryKeySelective(aupayWalletTransferRecordUpd);
    }

    @Override
    public int updateApplicationWithdrawWalletConfig(AupayApplicationWithdrawWalletConfig aupayApplicationWithdrawWalletConfig) {
        return aupayApplicationWithdrawWalletConfigMapper.updateApplicationWithdrawWalletConfig(aupayApplicationWithdrawWalletConfig);
    }

    @Override
    public int updateAupaySupplementFeeRecord(AupaySupplementFeeRecord aupaySupplementFeeRecord) {
        return aupaySupplementFeeRecordMapper.updateByPrimaryKeySelective(aupaySupplementFeeRecord);
    }

    @Override
    public List<AupayWalletTransferRecord> getPendingWalletTransferRecord() {
        return aupayWalletTransferRecordMapper.getPendingWalletTransferRecord();
    }

    @Override
    public int updateAupayReserveTransferWalletRecord(AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord) {
        return aupayReserveTransferWalletRecordMapper.updateByPrimaryKeySelective(aupayReserveTransferWalletRecord);
    }

    @Override
    public int saveAupayReserveTransferWalletRecord(AupayReserveTransferWalletRecord aupayReserveTransferWalletRecord) {
        aupayReserveTransferWalletRecord.setId(IdGenUtil.getUUID());
        return aupayReserveTransferWalletRecordMapper.insertSelective(aupayReserveTransferWalletRecord);
    }

    @Override
    public int checkPendingTransferAssets(String walletId) {
        return aupayReserveTransferWalletRecordMapper.checkPendingTransferAssets(walletId);
    }
}
