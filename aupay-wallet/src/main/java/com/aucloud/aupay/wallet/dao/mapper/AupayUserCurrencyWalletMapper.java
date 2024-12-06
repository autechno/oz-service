package com.aucloud.aupay.wallet.dao.mapper;

import com.aucloud.aupay.pojo.do_.AupayUserCurrencyWallet;
import com.aucloud.aupay.pojo.vo.UserWalletVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface AupayUserCurrencyWalletMapper extends BaseMapper<AupayUserCurrencyWallet> {

    public Page<UserWalletVo> findUserWalletList(Page<UserWalletVo> rowPage, @Param("conditions") Map<String,String> param);
}
