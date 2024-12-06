package com.aucloud.aupay.wallet.service;

import com.aucloud.aupay.constant.*;
import com.aucloud.aupay.dao.WalletDao;
import com.aucloud.aupay.entity.TxInfo;
import com.aucloud.aupay.exception.ServiceRuntimeException;
import com.aucloud.aupay.feign.BitcoinClient;
import com.aucloud.aupay.feign.EtherenumClient;
import com.aucloud.aupay.feign.SysClient;
import com.aucloud.aupay.feign.TronClient;
import com.aucloud.aupay.mybatis.Pager;
import com.aucloud.aupay.pojo.Result;
import com.aucloud.aupay.pojo.bo.AupayDigitalCurrencyWallet;
import com.aucloud.aupay.pojo.do_.*;
import com.aucloud.aupay.pojo.vo.*;
import com.aucloud.aupay.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class WalletService {
    @Autowired
    private WalletDao walletDao;
    @Autowired
    private BitcoinClient bitcoinClient;
    @Autowired
    private EtherenumClient etherenumClient;
    @Autowired
    private TronClient tronClient;
    @Autowired
    private SysClient sysClient;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public RechargeInfoVo getUserRechargeInfo(String userId, Integer currencyId, Integer currencyChain) {
        RechargeInfoVo rechargeInfoVo = new RechargeInfoVo();
        AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = getUserDigitalCurrencyWallet(userId,currencyId,currencyChain);
        rechargeInfoVo.setCurrencyId(currencyId);
        rechargeInfoVo.setCurrencyChain(currencyChain);
        rechargeInfoVo.setAddress(aupayDigitalCurrencyWallet.getAddress());
        rechargeInfoVo.setCofirmBlockNumber(CurrencyEnum.findById(currencyId).confirmBlockNumber);
        return rechargeInfoVo;
    }

    @Transactional
    public void initUserAssetsWallet(String userId) {
        createWallet(userId, WalletUseWay.USER);
    }

    public int initDigitalCurrencyWallet(AupayDigitalCurrencyWallet wallet) {
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        int i = walletDao.saveDigitalCurrencyWallet(wallet);
        if (wallet.getUseWay().equals(WalletUseWay.USER)) {
            hashOperations.put(RedisCacheKeys.SCAN_WALLET_ADDRESS + "-" + wallet.getCurrencyChain() + "-" + wallet.getCurrencyId(),
                    wallet.getAddress(),
                    wallet.getWalletId().toString());
        }
        return i;
    }

    public List<AupayDigitalCurrencyWallet> getTransferWalletWalletInfo() {
        List<AupayDigitalCurrencyWallet> wallets = new ArrayList<>();
        Result<List<AupayTransferWalletConfig>> result = sysClient.getTransferWalletConfig();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        List<AupayTransferWalletConfig> list = result.getData();
        if(list!=null&&!list.isEmpty()){
            list.forEach(c -> wallets.add(getWalletById(c.getWalletId())));
        }
        return wallets;
    }

    public AupayTransferWalletConfig getTransferWalletConfig(Integer currencyId, Integer currencyChain) {
        Result<List<AupayTransferWalletConfig>> result = sysClient.getTransferWalletConfig();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        List<AupayTransferWalletConfig> list = result.getData();
        if(list!=null&&!list.isEmpty()){
             return list.stream().filter(c -> c.getCurrencyId().equals(currencyId) && c.getCurrencyChain().equals(currencyChain)).findFirst().orElse(null);
        }
        return null;
    }

    public AupayDigitalCurrencyWallet getTransferWallet(Integer currencyId, Integer currencyChain) {
        AupayTransferWalletConfig transferWalletConfig = getTransferWalletConfig(currencyId, currencyChain);
        String walletId = transferWalletConfig.getWalletId();
        return getWalletById(walletId);
    }

    public AupayFeeWalletConfig getFeeWalletConfig(Integer currencyId, Integer currencyChain) {
        Result<List<AupayFeeWalletConfig>> result = sysClient.getFeeWalletConfig();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        List<AupayFeeWalletConfig> list = result.getData();
        if(list!=null&&!list.isEmpty()){
            return list.stream().filter(c -> c.getCurrencyId().equals(currencyId) && c.getCurrencyChain().equals(currencyChain)).findFirst().orElse(null);
        }
        return null;
    }

    public AupayDigitalCurrencyWallet getFeeWallet(Integer currencyId, Integer currencyChain) {
        AupayFeeWalletConfig transferWalletConfig = getFeeWalletConfig(currencyId, currencyChain);
        String walletId = transferWalletConfig.getWalletId();
        return getWalletById(walletId);
    }
    
    public List<AupayDigitalCurrencyWallet> getReserveWalletInfo() {
        List<AupayDigitalCurrencyWallet> wallets = new ArrayList<>();
        Result<List<AupayReserveWalletConfig>> result = sysClient.getReserveWalletInfo();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        List<AupayReserveWalletConfig> list = result.getData();
        if(list!=null&&!list.isEmpty()){
            list.forEach(c -> wallets.add(getWalletById(c.getWalletId())));
        }
        return wallets;
    }

    public AupayDigitalCurrencyWallet getReserveWallet(Integer currencyId, Integer currencyChain) {
        List<AupayDigitalCurrencyWallet> wallets = getReserveWalletInfo();
        return wallets.stream().filter(w -> w.getCurrencyId().equals(currencyId) && w.getCurrencyChain().equals(currencyChain)).findFirst().orElse(null);
    }

    public List<AupayDigitalCurrencyWallet> getWithdrawWalletInfo() {
        List<AupayDigitalCurrencyWallet> wallets = new ArrayList<>();
        Result<List<AupayWithdrawWalletConfig>> result = sysClient.getWithdrawWalletConfig();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        List<AupayWithdrawWalletConfig> list = result.getData();
        if(list!=null&&!list.isEmpty()){
            list.forEach(c -> wallets.add(getWalletById(c.getWalletId())));
        }
        return wallets;
    }

    public AupayUserAssetsCollectionConfig getUserAssetsCollectionConfig(){
        Result<List<AupayUserAssetsCollectionConfig>> result = sysClient.getUserAssetsCollectionConfig();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        return result.getData().get(0);
    }

    
    public List<FeeWalletVo> getApplicaitonFeeWalletInfo() {
        List<FeeWalletVo> feeWalletVos = new ArrayList<>();
        Result<List<AupayFeeWalletConfig>> result = sysClient.getFeeWalletConfig();
        if(!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return null;
        }
        List<AupayFeeWalletConfig> list = result.getData();
        if(list!=null&&!list.isEmpty()){
            list.forEach(c -> {
                AupayDigitalCurrencyWallet wallet = getWalletById(c.getWalletId());
                FeeWalletVo feeWalletVo = new FeeWalletVo();
                feeWalletVo.setWalletId(c.getWalletId());
                feeWalletVo.setCurrencyId(c.getCurrencyId());
                feeWalletVo.setCurrencyChain(c.getCurrencyChain());
                feeWalletVo.setAddress(wallet.getAddress());
//                feeWalletVo.setBalance(wallet.getBalance());
                feeWalletVo.setSupplementAmount(c.getSupplementAmount());
                feeWalletVo.setTriggerAmount(c.getTriggerAmount());
                feeWalletVos.add(feeWalletVo);
            });
        }
        return feeWalletVos;
    }

    
    public List<AupayDigitalCurrencyWallet> getApplicaitonAssetsWalletInfo() {
        return walletDao.getApplicaitonAssetsWalletInfo();
    }

    
    public List<ApplicationWithdrawWalletVo> getApplicaitonWithdrawWalletInfo() {
        return walletDao.getApplicaitonWithdrawWalletInfo();
    }

    
    public List<AupayWalletTransferRecord> findWalletTransferRecord(Pager pager) {
        return walletDao.findWalletTransferRecord(pager);
    }

    
    public AupayDigitalCurrencyWallet getUserDigitalCurrencyWallet(String userId, Integer currencyId, Integer currencyChain) {
        return walletDao.getUserWalletByCurrencyChain(userId,currencyId,currencyChain);
    }
    
    public AupayDigitalCurrencyWallet getWithdrawWallet(Integer currencyId, Integer currencyChain) {
        return getWithdrawWalletInfo().stream().filter(c -> c.getCurrencyId().equals(currencyId) && c.getCurrencyChain().equals(currencyChain)).findFirst().orElse(null);
//        return walletDao.getWithdrawWallet(currencyId,currencyChain);
    }

    private List<AupayDigitalCurrencyWallet> createWallet(String userId, Integer useWay) {
        List<AupayDigitalCurrencyWallet> list = new ArrayList<>();
        CurrencyEnum.CurrencyChainEnum[] chainEnums = CurrencyEnum.CurrencyChainEnum.values();
        Map<Integer, AupayUserChainWallet> chainAddressMap = new HashMap<>(chainEnums.length);
        for (CurrencyEnum.CurrencyChainEnum chainEnum : chainEnums) {
            AupayUserChainWallet chainWallet = createWallet(chainEnum.id);
            chainAddressMap.put(chainEnum.id, chainWallet);
        }
        for(CurrencyEnum currencyEnum : CurrencyEnum.values()) {
            String[] chains = currencyEnum.supportChain.split(",");
            for(String chain : chains) {
                Integer chainId = Integer.parseInt(chain);
                AupayUserChainWallet chainWallet = chainAddressMap.get(chainId);
                list.add(createWallet(userId, currencyEnum.id, chainId, chainWallet.getId(), chainWallet.getAddress(), useWay));
            }
        }
        return list;
    }

    public AupayDigitalCurrencyWallet createWallet(String userId, Integer currencyId, Integer currencyChain,Integer chainWalletId, String address, Integer walletUseWay) {
        AupayDigitalCurrencyWallet aupayDigitalCurrencyWallet = new AupayDigitalCurrencyWallet();
        aupayDigitalCurrencyWallet.setUserId(userId);
        aupayDigitalCurrencyWallet.setCurrencyId(currencyId);
        aupayDigitalCurrencyWallet.setCurrencyChain(currencyChain);
        aupayDigitalCurrencyWallet.setCreateTime(new Date());
        aupayDigitalCurrencyWallet.setIsDel(Boolean.FALSE);
//        aupayDigitalCurrencyWallet.setBalance(BigDecimal.ZERO);
//        aupayDigitalCurrencyWallet.setFeeBalance(BigDecimal.ZERO);
        aupayDigitalCurrencyWallet.setUseWay(walletUseWay);
        aupayDigitalCurrencyWallet.setAddress(address);
        aupayDigitalCurrencyWallet.setWalletChainId(chainWalletId);
        int i = initDigitalCurrencyWallet(aupayDigitalCurrencyWallet);
        return aupayDigitalCurrencyWallet;
    }

    private AupayUserChainWallet createWallet(Integer chainId) {
        String address = null;
        if(chainId.equals(CurrencyEnum.CurrencyChainEnum.TRC.id)) {
            Result<String> wallet = tronClient.createWallet();
            if(!wallet.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
            }
            address = wallet.getData();
        } else if(chainId.equals(CurrencyEnum.CurrencyChainEnum.BTC.id)) {
            Result<String> wallet = bitcoinClient.createWallet();
            if(!wallet.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
            }
            address = wallet.getData();
        } else if(chainId.equals(CurrencyEnum.CurrencyChainEnum.ETH.id)) {
            Result<String> wallet = etherenumClient.createWallet();
            if(!wallet.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
            }
            address = wallet.getData();
        }
        if (StringUtils.isNotBlank(address)) {
            return walletDao.saveChainWallet(chainId, address);
        }
        return null;
    }

    /**
     * 初始化中转地址 提款地址 资产储备地址 矿工费地址
     */
    
    @Transactional
    public void initSysWallet() {
        List<AupayDigitalCurrencyWallet> withdraws = createWallet("0", WalletUseWay.WITHDRAW);
        for (AupayDigitalCurrencyWallet withdraw : withdraws) {
            sysClient.bindWithdrawWallet(withdraw.getCurrencyId(), withdraw.getCurrencyChain(), withdraw.getWalletId().toString());
        }
        List<AupayDigitalCurrencyWallet> assetes = createWallet("0", WalletUseWay.ASSETS);
        for (AupayDigitalCurrencyWallet assets : assetes) {
            sysClient.bindAssetsWallet(assets.getCurrencyId(), assets.getCurrencyChain(), assets.getWalletId().toString());
        }
        List<AupayDigitalCurrencyWallet> transfers = createWallet("0", WalletUseWay.TRANSFER);
        for (AupayDigitalCurrencyWallet transfer : transfers) {
            sysClient.bindTransferWallet(transfer.getCurrencyId(), transfer.getCurrencyChain(), transfer.getWalletId().toString());
        }

        CurrencyEnum.CurrencyChainEnum[] chainEnums = CurrencyEnum.CurrencyChainEnum.values();
        for (CurrencyEnum.CurrencyChainEnum chainEnum : chainEnums) {
            AupayUserChainWallet chainWallet = createWallet(chainEnum.id);
            assert chainWallet != null;
            AupayDigitalCurrencyWallet feeWallet = createWallet("0", chainEnum.chainCurrencyId, chainEnum.id, chainWallet.getId(), chainWallet.getAddress(), WalletUseWay.TRANSFER_FEE);
            sysClient.bindTransferFeeWallet(chainEnum.chainCurrencyId, chainEnum.id, feeWallet.getWalletId().toString());
        }

    }
    /**
     * 初始化商户/应用钱包地址
     */
    
    @Transactional
    public void initApplinctionWallet(String applicationId) {
        List<AupayDigitalCurrencyWallet> applicationWalletByApplicationId = getApplicationWalletByApplicationId(applicationId);
        if(!applicationWalletByApplicationId.isEmpty()){
            return;
        }
        createWallet(applicationId, ComWallet.OZBET);

    }

    @Transactional
    public void regenUserAssetsWallet(String userId, Integer currencyId, Integer currencyChain) {
        AupayUserChainWallet chainWallet = createWallet(currencyChain);
        assert chainWallet != null;
        int count = walletDao.clearUserAssetsWalletRelation(userId,currencyId,currencyChain);
        createWallet(userId, currencyId, currencyChain, chainWallet.getId(), chainWallet.getAddress(), WalletUseWay.USER);
//        count = walletDao.bindUserWallet(aupayDigitalCurrencyWallet.getWalletId(),userId);
    }

    
    public TxInfo getTxInfo(String txId, Integer currencyId, Integer currencyChain) {
        TxInfo txInfo = null;
        if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.TRC.id)) {
            Result txInfoResult = tronClient.getTxInfo(currencyId, txId);
            txInfo = ObjectUtils.convertObject(TxInfo.class, txInfoResult.getData());
        } else if(currencyChain.equals(CurrencyEnum.CurrencyChainEnum.ETH.id)) {
            Result txInfoResult = etherenumClient.getTxInfo(currencyId, txId);
            txInfo = ObjectUtils.convertObject(TxInfo.class, txInfoResult.getData());
        }else if(currencyId.equals(CurrencyEnum.BTC.id)) {
            Result txInfoResult = bitcoinClient.getTxInfo(currencyId, txId);
            txInfo = ObjectUtils.convertObject(TxInfo.class, txInfoResult.getData());
        }

        return txInfo;
    }

    
    public void updateAupayWalletTransferRecord(AupayWalletTransferRecord aupayWalletTransferRecordUpd) {
        int count = walletDao.updateAupayWalletTransferRecord(aupayWalletTransferRecordUpd);
    }

    public BigDecimal getRealBalance(AupayDigitalCurrencyWallet wallet, Integer currencyId, Integer currencyChain) {
        log.info("获取链上余额，walletId:{}, address:{}, currencyId:{}, chain:{}", wallet.getWalletId(),wallet.getAddress(),currencyId,currencyChain);
        Result<BigDecimal> balanceResult = null;
        if(CurrencyEnum.CurrencyChainEnum.TRC.id.equals(currencyChain)) {
            balanceResult = tronClient.getBalance(wallet.getAddress(),currencyId);
        } else if(CurrencyEnum.CurrencyChainEnum.ETH.id.equals(currencyChain)) {
            balanceResult = etherenumClient.getBalance(wallet.getAddress(),currencyId);
        }
        if (balanceResult!=null && balanceResult.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            return balanceResult.getData();
        } else {
            throw new ServiceRuntimeException("获取链上余额异常");
        }
    }

    
    public List<AupayWalletTransferRecord> getPendingWalletTransferRecord() {
        return walletDao.getPendingWalletTransferRecord();
    }

    
//    public List<String> findUserWalletIdGreaterThan(BigDecimal weekAutoTriggerAmount) {
//        return walletDao.findUserWalletIdGreaterThan(weekAutoTriggerAmount);
//    }

    
//    @GlobalTransactional
//    public void testTransaction() {
//        System.out.println(RootContext.getXID());
//        int a = 1/0;
//        walletDao.testTransaction();
//    }
//    public int addBalance(String walletId, BigDecimal amount) {
//        log.info("增加钱包余额，walletId:{}, amount:{}", walletId, amount);
//        return walletDao.addBalance(walletId, amount);
//    }

    AupayDigitalCurrencyWallet getWalletById(String walletId) {
        return walletDao.getWalletById(walletId);
    }

    public List<AupayDigitalCurrencyWallet> getUserWalletByUserId(String userId) {
        return walletDao.getUserWalletByUserId(userId);
    }

    
    public List<AupayDigitalCurrencyWallet> getApplicationWalletByApplicationId(String applicationId) {
        return getUserWalletByUserId(applicationId);
    }
    
    public AupayDigitalCurrencyWallet getApplicationWallet(String applicationId, Integer currencyId, Integer currencyChain) {
        return getUserDigitalCurrencyWallet(applicationId, currencyId, currencyChain);
    }

    
    public void updateApplicationWithdrawWalletConfig(AupayApplicationWithdrawWalletConfig aupayApplicationWithdrawWalletConfig) {
        int i = walletDao.updateApplicationWithdrawWalletConfig(aupayApplicationWithdrawWalletConfig);
    }

    
    public List<UserWalletVo> findUserWalletList(Pager pager) {
        return walletDao.findUserWalletList(pager);
    }

    
    public List<SysAssetsVo> getAssetsVo() {
        ArrayList<SysAssetsVo> sysAssetsVos = new ArrayList<>();
        Integer currencyId = CurrencyEnum.USDT.id;
        Integer TRC20 = CurrencyEnum.CurrencyChainEnum.TRC.id;
        Integer ERC20 = CurrencyEnum.CurrencyChainEnum.ETH.id;
        List<AupayDigitalCurrencyWallet> transferWalletWalletInfo = getTransferWalletWalletInfo();
        List<AupayDigitalCurrencyWallet> reserveWalletInfo = getReserveWalletInfo();
        List<AupayDigitalCurrencyWallet> withdrawWalletInfo = getWithdrawWalletInfo();
        transferWalletWalletInfo.addAll(reserveWalletInfo);
        transferWalletWalletInfo.addAll(withdrawWalletInfo);
//        BigDecimal erc20AssetsBalance = transferWalletWalletInfo.stream().filter(aupayDigitalCurrencyWallet -> aupayDigitalCurrencyWallet.getCurrencyId().equals(currencyId) && aupayDigitalCurrencyWallet.getCurrencyChain().equals(ERC20))
//                .map(AupayDigitalCurrencyWallet::getBalance).reduce(BigDecimal::add).orElse(null);
//        BigDecimal trc20AssetsBalance = transferWalletWalletInfo.stream().filter(aupayDigitalCurrencyWallet -> aupayDigitalCurrencyWallet.getCurrencyId().equals(currencyId) && aupayDigitalCurrencyWallet.getCurrencyChain().equals(TRC20))
//                .map(AupayDigitalCurrencyWallet::getBalance).reduce(BigDecimal::add).orElse(null);
        SysAssetsVo erc20SysAssetsVo = new SysAssetsVo();
        erc20SysAssetsVo.setCurrencyId(currencyId);
        erc20SysAssetsVo.setCurrencyChain(ERC20);
//        erc20SysAssetsVo.setAssetsBalance(erc20AssetsBalance);
        SysAssetsVo trc20SysAssetsVo = new SysAssetsVo();
        trc20SysAssetsVo.setCurrencyId(currencyId);
        trc20SysAssetsVo.setCurrencyChain(TRC20);
//        trc20SysAssetsVo.setAssetsBalance(trc20AssetsBalance);
        sysAssetsVos.add(erc20SysAssetsVo);
        sysAssetsVos.add(trc20SysAssetsVo);
        return sysAssetsVos;
    }

    public int saveAupayWalletTransferRecord(AupayWalletTransferRecord aupayWalletTransferRecord) {
        return walletDao.saveAupayWalletTransferRecord(aupayWalletTransferRecord);
    }
}
