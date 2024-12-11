package com.aucloud.aupay.wallet.orm.service;

import com.aucloud.aupay.wallet.feign.FeignEthContractService;
import com.aucloud.aupay.wallet.orm.po.EthUserWalletPool;
import com.aucloud.aupay.wallet.orm.mapper.EthUserWalletPoolMapper;
import com.aucloud.constant.ResultCodeEnum;
import com.aucloud.exception.ServiceRuntimeException;
import com.aucloud.pojo.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * Eth链用户钱包池子 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-06
 */
@Slf4j
@Service
public class EthUserWalletPoolService extends ServiceImpl<EthUserWalletPoolMapper, EthUserWalletPool> implements IService<EthUserWalletPool> {

    private final FeignEthContractService feignEthContractService;

    public EthUserWalletPoolService(FeignEthContractService feignEthContractService) {
        this.feignEthContractService = feignEthContractService;
    }

    public EthUserWalletPool getUnusedWalletAddress() {
        List<EthUserWalletPool> list = lambdaQuery().eq(EthUserWalletPool::getStatus, 0).orderByDesc(EthUserWalletPool::getCreateTime).first("limit 1").list();
        if (list == null || list.isEmpty()) {
            Result<List<String>> result = feignEthContractService.generateUserWalletsBatch(100);
            if (result!=null&&result.getCode()== ResultCodeEnum.SUCCESS.getCode()) {
                List<EthUserWalletPool> pools = new ArrayList<>();
                List<String> data = result.getData();
                data.forEach(e->{
                    EthUserWalletPool pool = new EthUserWalletPool();
                    pool.setWalletAddress(e);
                    pool.setStatus(0);
                    pools.add(pool);
                });
                saveBatch(pools);
                return pools.get(0);
            } else {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL);
            }
        } else {
            return list.get(0);
        }
    }

    public void recycleUserWallet(EthUserWalletPool ethUserWalletPool) {
        String walletAddress = ethUserWalletPool.getWalletAddress();
        ethUserWalletPool.setStatus(2);
        ethUserWalletPool.setUserId(null);
        ethUserWalletPool.setRecycleTime(new Date());
        updateById(ethUserWalletPool);
        Result<?> result = feignEthContractService.recycleUserWallet(walletAddress);
        if (result != null && result.getCode() == ResultCodeEnum.SUCCESS.getCode()) {
            log.info("recycle user wallet address. success :{}", walletAddress);
        } else {
            log.error("recycle user wallet address. fail :{}", walletAddress);
//            throw new ServiceRuntimeException(ResultCodeEnum.FAIL);
        }
    }
}
