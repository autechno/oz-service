package com.aucloud.aupay.user.orm.service;

import com.aucloud.aupay.user.orm.mapper.AupayUserMapper;
import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.pojo.dto.UserInfoDTO;
import com.aucloud.commons.pojo.vo.UserInfoVo;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-11
 */
@Service
public class AupayUserService extends ServiceImpl<AupayUserMapper, AupayUser> implements IService<AupayUser> {

    public UserInfoVo getUserInfo(Long userId) {
        AupayUser userById = getById(userId);
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUsername(userById.getUsername());
        userInfoVo.setHeadPortrait(userById.getHeadPortrait());
        userInfoVo.setMobile(userById.getMobile());
        userInfoVo.setEmail(userById.getEmail());
        userInfoVo.setTelegram(userById.getTelegram());
        userInfoVo.setNickname(userById.getNickname());
        userInfoVo.setCurrencyUnit(userById.getCurrencyUnit());
        userInfoVo.setIsSetAssetsPassword(!StringUtils.isEmpty(userById.getAssetsPassword()));
        userInfoVo.setIsBindGoogleAuth(!StringUtils.isEmpty(userById.getGoogleSecret()));
        return userInfoVo;
    }

    public void setUserInfo(UserInfoDTO userInfoDTO) {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
        AupayUser aupayUser = new AupayUser();
        aupayUser.setId(userId);
        aupayUser.setHeadPortrait(userInfoDTO.getHeadPortrait());
        aupayUser.setNickname(userInfoDTO.getNickname());
        aupayUser.setMobile(userInfoDTO.getMobile());
        aupayUser.setTelegram(userInfoDTO.getTelegram());
        aupayUser.setCurrencyUnit(userInfoDTO.getCurrencyUnit());
        updateById(aupayUser);
    }

    public void switchWhiteAddress(Long userId) {
        AupayUser userById = getById(userId);
        if (userById.getWithdrawWhiteList() == null) {
            userById.setWithdrawWhiteList(0);
        }else {
            if (userById.getWithdrawWhiteList() == 1) {
                userById.setWithdrawWhiteList(0);
            } else {
                userById.setWithdrawWhiteList(1);
            }
        }
        updateById(userById);
    }

    public int getSwitchWhite(Long userId) {
        AupayUser userById = getById(userId);
        return userById.getWithdrawWhiteList();
    }
}
