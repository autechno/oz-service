package com.aucloud.aupay.user.service;

import com.aucloud.aupay.user.feign.FeignWalletService;
import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AcountAssetsService;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.commons.constant.*;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.Result;
import com.aucloud.commons.pojo.dto.AccountChainWalletDto;
import com.aucloud.commons.pojo.dto.RegisterDTO;
import com.aucloud.commons.service.CodeCheckService;
import com.aucloud.commons.utils.Encryption;
import com.aucloud.commons.utils.IpUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserRegisterService {

    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private CodeCheckService codeCheckService;
    @Autowired
    private FeignWalletService feignWalletService;
    @Autowired
    private AssetsService assetsService;

    @Transactional
    public AupayUser register(RegisterDTO registerDTO) {
        if (registerDTO.getIsBindPay() != 1) {
            if (!codeCheckService.checkEmailCode(EmailCodeType.REGISTER, registerDTO.getEmail(), registerDTO.getEmailCode())) {
                throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
            }
        }
        registerDTO.setUsername(registerDTO.getEmail());

        Long count = aupayUserService.lambdaQuery().eq(AupayUser::getUsername,registerDTO.getUsername()).count();
        if (count!=null && count > 0) {
            throw new ServiceRuntimeException(ResultCodeEnum.USERNAME_EXISTS.getLabel_zh_cn(), ResultCodeEnum.USERNAME_EXISTS.getCode());
        }else {
            count = aupayUserService.lambdaQuery().eq(AupayUser::getEmail,registerDTO.getEmail()).count();
            if (count!=null && count > 0) {
                throw new ServiceRuntimeException(ResultCodeEnum.EMAIL_BE_BIND.getLabel_zh_cn(), ResultCodeEnum.EMAIL_BE_BIND.getCode());
            }
        }

        AupayUser user = new AupayUser();
        user.setPassword(Encryption.getSaltMD5(registerDTO.getPassword()));
//        user.setAssetsPassword(Encryption.getSaltMD5(DEFAULT_ASSETS_PASSWORD));
        user.setUsername(registerDTO.getUsername());
        if (Objects.nonNull(registerDTO.getHeadPortrait())) {
            user.setHeadPortrait(registerDTO.getHeadPortrait());
        }
        if (Objects.nonNull(registerDTO.getProvider())) {
            user.setProvider(registerDTO.getProvider());
        }
        if (Objects.nonNull(registerDTO.getProviderId())) {
            user.setProviderId(registerDTO.getProviderId());
        }
        user.setCurrencyUnit(CurrencyUnit.CNY);
        user.setWithdrawWhiteList(0);
        user.setEmail(registerDTO.getEmail());
//        Integer randomHeadPortrait = new Random().nextInt(5);
//        user.setHeadPortrait(UserDefaultHeadPortrait.arr[randomHeadPortrait]);
        user.setOpenChannel(UserOpenChannel.DIRECT);
        user.setCreateTime(new Date());
        user.setState(UserState.NORMAL);
        user.setRegIp(IpUtils.getIpAddress());
        user.setUserCode(IdWorker.getIdStr());
        boolean b = aupayUserService.save(user);
        if (!b) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_UPDATE.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_UPDATE.getCode());
        }
        //生成用户资产信息
        Result<List<AccountChainWalletDto>> result = feignWalletService.generateAccountWallet(user.getId(), AccountType.USER);
        if (!result.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL.getLabel_zh_cn(), ResultCodeEnum.FAIL.getCode());
        }
        assetsService.creaateAccountAssets(user.getId(), AccountType.USER);

        registerDTO.setRelationEmail(user.getEmail());
        registerDTO.setRelationUsername(user.getUsername());
        registerDTO.setRelationUserId(user.getUserCode());
//        if (registerDTO.getIsBindPay() == 1) {
//            this.bindOzbetAccount(registerDTO);
//        }
        return user;
    }
}
