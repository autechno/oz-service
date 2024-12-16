package com.aucloud.aupay.user.service;

import com.aucloud.aupay.security.token.SecurityTokenHandler;
import com.aucloud.aupay.security.token.TokenHeadInfo;
import com.aucloud.aupay.security.token.TokenInfo;
import com.aucloud.aupay.user.orm.mapper.AupayUserMapper;
import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.po.AupayUserLoginLog;
import com.aucloud.aupay.user.orm.service.AupayUserLoginLogService;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.commons.constant.ApplicationConstant;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.constant.Terminal;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.dto.UserLoginDTO;
import com.aucloud.commons.utils.Encryption;
import com.aucloud.commons.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private AupayUserMapper aupayUserMapper;
    @Autowired
    private SecurityTokenHandler securityTokenHandler;
    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private AupayUserLoginLogService aupayUserLoginLogService;

    public String login(UserLoginDTO userLoginDTO) {
        String ipAddress = IpUtils.getIpAddress();
        Object asscessKey = redisTemplate.opsForValue().get(ipAddress + ApplicationConstant.ACCESS_KEY_CACHE_SUFFIX);
        if (asscessKey == null) {
            throw new ServiceRuntimeException(ResultCodeEnum.ENVIRONMENTAL_ANOMALY.getLabel_zh_cn(), ResultCodeEnum.ENVIRONMENTAL_ANOMALY.getCode());
        }
        AupayUser aupayUser = aupayUserService.lambdaQuery().eq(AupayUser::getUsername, userLoginDTO.getUsername()).oneOpt().orElseThrow(()->new ServiceRuntimeException(ResultCodeEnum.USER_NOT_EXISTS));

        String password = userLoginDTO.getPassword();
        password = Encryption.decryptByDES(password, asscessKey.toString());
        boolean loginCheck = Encryption.getSaltverifyMD5(password, aupayUser.getPassword());
        if (!loginCheck) {
            throw new ServiceRuntimeException(ResultCodeEnum.USER_AUTH_FAILURE.getLabel_zh_cn(), ResultCodeEnum.USER_AUTH_FAILURE.getCode());
        }
        String userId = aupayUser.getId().toString();
        String tokenHead = TokenHeadInfo.getTokenHead(new Date(System.currentTimeMillis() + 86400000));
        String tokenInfo = TokenInfo.makeTokenInfo(userId, Terminal.USER);
        String token = securityTokenHandler.genToken(userId, tokenHead, tokenInfo, ApplicationConstant.SECRET);//签名头 签名体 密钥
//        aupayUser.setLoginTime(new Date());
//        aupayUserService.updateById(aupayUser);
//        saveUserLoginLog(userId);
        AupayUserLoginLog aupayUserLoginLog = new AupayUserLoginLog();
        aupayUserLoginLog.setUserId(aupayUser.getId());
        aupayUserLoginLog.setLoginIp(IpUtils.getIpAddress());
        aupayUserLoginLogService.save(aupayUserLoginLog);

//        if(StringUtils.isNotBlank(userLoginDTO.getThirdPartMerchant())) {
//            Map<String,String> map = new HashMap<>();
//            map.put("aupayId",aupayUser.getUserId());
//            map.put("aupayEmail",aupayUser.getEmail());
//            map.put("aupayUsername",aupayUser.getUsername());
//            AupayOzbetUser ozbetUser = userDao.getOzBetUserByUserId(userId);
//            if(ozbetUser != null) {
//                String ozbetUserId = ozbetUser.getOzbetUserId();
//                map.put("ozbetUserId",ozbetUserId);
//            }
//            token = securityTokenHandler.secret2ozbet(JSON.toJSONString(map));
//        }
        return token;
    }
}
