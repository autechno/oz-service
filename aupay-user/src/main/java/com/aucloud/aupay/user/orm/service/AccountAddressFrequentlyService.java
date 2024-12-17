package com.aucloud.aupay.user.orm.service;

import com.alibaba.fastjson2.JSON;
import com.aucloud.aupay.user.orm.mapper.AccountAddressFrequentlyMapper;
import com.aucloud.aupay.user.orm.po.AccountAddressFrequently;
import com.aucloud.aupay.user.orm.po.AupayEnterprise;
import com.aucloud.commons.constant.AccountType;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 用户地址簿 服务实现类
 * </p>
 *
 * @author yang.li@autech.one
 * @since 2024-12-09
 */
@Service
public class AccountAddressFrequentlyService extends ServiceImpl<AccountAddressFrequentlyMapper, AccountAddressFrequently> implements IService<AccountAddressFrequently> {

    @Autowired
    private AupayEnterpriseService aupayEnterpriseService;

    @Override
    public boolean saveOrUpdate(AccountAddressFrequently addressFrequently) {
        boolean b = true;
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Integer accountType = tokenInfo.getAccountType();
        Long accountId = tokenInfo.getAccountId();
        Long id = addressFrequently.getId();
        if (id == null) {
            addressFrequently.setAccountId(accountId);
            addressFrequently.setAccountType(accountType);
        }
        addressFrequently.setStatus(1);
        addressFrequently.setLock(true);
        addressFrequently.setUnlockTime(DateUtils.addHours(new Date(), 24));
        if (needApprove(tokenInfo)) {
            //企业管理账户验证
            addressFrequently.setStatus(-1);
            String json = JSON.toJSONString(addressFrequently);
            //执行参数
            if (id != null) {
                AccountAddressFrequently old = new AccountAddressFrequently();
                old.setId(id);
                old.setStatus(1);
                old.setLock(true);
                old.setUnlockTime(DateUtils.addHours(new Date(), 24));
                b = updateById(old);
            }
        } else {
            b = super.saveOrUpdate(addressFrequently);
        }
        return b;
    }

    public boolean whiteToggle(Long id) {
        AccountAddressFrequently address = getOptById(id).orElseThrow(()->new ServiceRuntimeException(ResultCodeEnum.NOT_FOUND));
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Boolean _white = !address.getWhite();
        address.setStatus(1);
        address.setLock(true);
        address.setUnlockTime(DateUtils.addHours(new Date(), 24));
        if (needApprove(tokenInfo)) {
            //企业管理账户验证
            address.setStatus(-1);
            String json = JSON.toJSONString(address);
            //执行参数
        } else {
            address.setWhite(_white);
        }

        return updateById(address);
    }

    public boolean deleteById(Long id) {
        boolean b = false;
        AccountAddressFrequently address = getOptById(id).orElseThrow(()->new ServiceRuntimeException(ResultCodeEnum.NOT_FOUND));
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        address.setDel(1);
        address.setLock(true);
        address.setUnlockTime(DateUtils.addHours(new Date(), 24));
        if (needApprove(tokenInfo)) {
            //企业管理账户验证
            address.setStatus(-1);
            b= updateById(address);
            String json = JSON.toJSONString(address);
            //执行参数
        } else {
            b = removeById(id);
        }
        return b;
    }
    public void rejectSaveOrUpdate(String json) {
        AccountAddressFrequently address = JSON.parseObject(json, AccountAddressFrequently.class);
        if (address.getId() == null) {
            //啥页也不做
        } else {
            Long id = address.getId();
            AccountAddressFrequently old = new AccountAddressFrequently();
            old.setId(id);
            old.setStatus(1);
//            old.setLock(true);
//            old.setUnlockTime(DateUtils.addHours(new Date(), 24));
            updateById(old);
        }
    }
    public void rejectWhiteToggle(String json) {
        AccountAddressFrequently address = JSON.parseObject(json, AccountAddressFrequently.class);
        Long id = address.getId();
        AccountAddressFrequently old = new AccountAddressFrequently();
        old.setId(id);
        old.setStatus(1);
//            old.setLock(true);
//            old.setUnlockTime(DateUtils.addHours(new Date(), 24));
        updateById(old);
    }
    public void rejectDelete(String json) {
        AccountAddressFrequently address = JSON.parseObject(json, AccountAddressFrequently.class);
        Long id = address.getId();
        AccountAddressFrequently old = new AccountAddressFrequently();
        old.setId(id);
        old.setStatus(1);
//            old.setLock(true);
//            old.setUnlockTime(DateUtils.addHours(new Date(), 24));
        updateById(old);
    }

    public void approveSaveOrUpdate(String json) {
        AccountAddressFrequently address = JSON.parseObject(json, AccountAddressFrequently.class);
        address.setStatus(1);
        address.setLock(true);
        address.setUnlockTime(DateUtils.addHours(new Date(), 24));
        super.updateById(address);
    }
    public void approveWhiteToggle(String json) {
        AccountAddressFrequently address = JSON.parseObject(json, AccountAddressFrequently.class);
        address.setWhite(!address.getWhite());
        address.setStatus(1);
        address.setLock(true);
        address.setUnlockTime(DateUtils.addHours(new Date(), 24));
        super.updateById(address);
    }
    public void approveDelete(String json) {
        AccountAddressFrequently address = JSON.parseObject(json, AccountAddressFrequently.class);
        Long id = address.getId();
        removeById(id);
    }

    private boolean needApprove(TokenInfo tokenInfo) {
        if (Objects.equals(tokenInfo.getAccountType(), AccountType.COMPANY_USER)) {
            AupayEnterprise enterprise = aupayEnterpriseService.getById(tokenInfo.getAccountId());
            return !Objects.equals(tokenInfo.getUserId(), enterprise.getEnterpriseOwner());
        }
        return false;
    }
}
