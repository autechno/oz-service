package com.aucloud.aupay.wallet.dao.mapper;


import com.aucloud.aupay.pojo.do_.AupayApplicationWithdrawWalletConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AupayApplicationWithdrawWalletConfigMapper extends BaseMapper<AupayApplicationWithdrawWalletConfig> {

    int updateApplicationWithdrawWalletConfig(AupayApplicationWithdrawWalletConfig aupayApplicationWithdrawWalletConfig);

}