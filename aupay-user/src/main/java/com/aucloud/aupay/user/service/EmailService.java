package com.aucloud.aupay.user.service;

import com.alibaba.fastjson2.JSON;
import com.aucloud.aupay.user.orm.po.AupayUser;
import com.aucloud.aupay.user.orm.service.AupayUserService;
import com.aucloud.commons.constant.EmailCodeType;
import com.aucloud.commons.constant.QueueConstant;
import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.pojo.bo.EmailMessage;
import com.aucloud.commons.exception.ServiceRuntimeException;
import com.aucloud.commons.pojo.bo.TokenInfo;
import com.aucloud.commons.utils.IpUtils;
import com.aucloud.aupay.validate.enums.OperationEnum;
import com.aucloud.aupay.validate.enums.VerifyMethod;
import com.aucloud.aupay.validate.service.CodeCheckService;
import com.aucloud.aupay.validate.service.OperationTokenService;
import com.aucloud.commons.utils.UserRequestHeaderContextHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AupayUserService aupayUserService;
    @Autowired
    private CodeCheckService codeCheckService;
    @Autowired
    private OperationTokenService operationTokenService;

    public Integer sendResetPasswordEmailCode(String username) {
        AupayUser userByUsername = aupayUserService.lambdaQuery().eq(AupayUser::getUsername, username).one();
        String email = userByUsername.getEmail();
        Integer emailCode = codeCheckService.getEmailCode(EmailCodeType.RESET_PASSWORD, email);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setEmail(email);
//        emailMessage.setContent("邮箱验证码： " + emailCode + "<br/>" + "您的账号 " + username + "，正在进行重置登录密码操作；" + "<br/>" + "为保障您的账号安全，请勿转发或泄露。");
        emailMessage.setContent(getEmailCodeContent(emailCode));
        emailMessage.setTitle("重置登录密码");
        rabbitTemplate.convertAndSend(QueueConstant.SEND_EMAIL, JSON.toJSONString(emailMessage));
        return emailCode;
    }

    public void sendLoginEmailCode(String username) {
        AupayUser userByUsername = aupayUserService.lambdaQuery().eq(AupayUser::getUsername, username).one();
        String email = userByUsername.getEmail();
        Integer emailCode = codeCheckService.getEmailCode(EmailCodeType.LOGIN, email);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setEmail(email);
//        emailMessage.setContent("邮箱验证码： " + emailCode + "<br/>" + "您的账号 " + username + "，正在进行登录操作；" + "<br/>" + "为保障您的账号安全，请勿转发或泄露。");
        emailMessage.setContent(getEmailCodeContent(emailCode));
        emailMessage.setTitle("登录验证");
        rabbitTemplate.convertAndSend(QueueConstant.SEND_EMAIL, JSON.toJSONString(emailMessage));
    }

    public void sendRegisterEmailCode(String email) {
        Integer emailCode = codeCheckService.getEmailCode(EmailCodeType.REGISTER, email);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setEmail(email);
//        emailMessage.setContent("邮箱验证码： " + emailCode + "，正在进行注册操作；" + "<br/>" + "为保障您的账号安全，请勿转发或泄露。");
        emailMessage.setContent(getEmailCodeContent(emailCode));
        emailMessage.setTitle("注册验证");
        rabbitTemplate.convertAndSend(QueueConstant.SEND_EMAIL, JSON.toJSONString(emailMessage));
    }

    public Integer sendEmailCode(Integer operationId) {
        OperationEnum operationEnum = OperationEnum.getById(operationId);

        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();

        AupayUser userById = aupayUserService.getById(userId);
        String email = userById.getEmail();
        Integer emailCode = codeCheckService.getEmailCode(operationEnum.name(), email);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setEmail(email);
//        emailMessage.setContent("邮箱验证码： " + emailCode + "；" + "<br/>" + "为保障您的账号安全，请勿转发或泄露。");
        emailMessage.setContent(getEmailCodeContent(emailCode));
        emailMessage.setTitle("邮箱验证");
        rabbitTemplate.convertAndSend(QueueConstant.SEND_EMAIL, JSON.toJSONString(emailMessage));
        return emailCode;
    }

    public String verifyEmail(Integer emailCode, Integer operationId) {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
        AupayUser userById = aupayUserService.getById(userId);
        String email = userById.getEmail();
        OperationEnum operationEnum = OperationEnum.getById(operationId);
        if (!codeCheckService.checkEmailCode(operationEnum.name(), email, emailCode.toString())) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL_TO_VERIFY.getLabel_zh_cn(), ResultCodeEnum.FAIL_TO_VERIFY.getCode());
        }
        return operationTokenService.signToken(VerifyMethod.EMAIL, operationEnum, userId, IpUtils.getIpAddress());
    }

    public void sendWithdrawEmailCode() {
        TokenInfo tokenInfo = UserRequestHeaderContextHandler.getTokenInfo();
        Long userId = tokenInfo.getUserId();
        AupayUser userById = aupayUserService.getById(userId);
        String email = userById.getEmail();
        Integer emailCode = codeCheckService.getEmailCode(EmailCodeType.WITHDRAW, email);
        EmailMessage emailMessage = new EmailMessage();
        emailMessage.setEmail(email);
//        emailMessage.setContent("邮箱验证码： " + emailCode + "，正在进行提币操作；" + "<br/>" + "为保障您的账号安全，请勿转发或泄露。");
        emailMessage.setContent(getEmailCodeContent(emailCode));
        emailMessage.setTitle("提币验证");
        rabbitTemplate.convertAndSend(QueueConstant.SEND_EMAIL, JSON.toJSONString(emailMessage));
    }

    private String getEmailCodeContent(Integer emailCode) {
        return "亲爱的 auPay 用户：" +
                "<br>" +
                "您的验证码是：" + emailCode +
                "<br>" +
                "有效期10分钟，请在有效期内使用。" +
                "<br>" +
                "请知悉，为了您的账号安全，切勿转发该邮件。" +
                "<br>" +
                "如果您未提出此请求，请忽略这封电子邮件。";
    }
}
