package com.aucloud.commons.constant;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS("成功", "Success", 200),
    IDENTITY_FAILURE("身份认证失败", "", 401),
    OTHER("未知错误", "Unknown Error", 201),
    FAIL("失败", "fail", 202),
    USER_AUTH_FAILURE("用户名密码错误", "", 1001),
    NO_USER_INFO("请先登录", "No User Information", 1002),
    LIMITED_USER("受限制的用户", "Limited User", 1003),
    LIMITED_PERMISSIONS("权限受限", "Limited Permissions", 1004),
    LIMITED_IP("IP受限", "Limited IP", 1005),
    LIMITED_FREQUENCY("访问频率受限", "Limited Frequency", 1006),
    ILLEGAL_OPERATION("非法操作", "Illegal OperationEnum", 1007),
    ILLEGAL_PARAMETER("非法参数", "Illegal Parameter", 1008),
    ILLEGAL_SIGN("签名错误", "Illegal Sign", 1009),
    MERCHANT_NOEXISTS("商户不存在", "Merchant NoExists", 1010),
    FAIL_TO_SAVE("保存失败", "", 1011),
    FAIL_TO_UPDATE("保存失败", "", 1012),
    FAIL_TO_VERIFY("验证失败", "", 1013),
    USER_NOT_EXISTS("用户不存在", "", 1014),
    DATA_FORMART("数据格式异常", "", 1015),
    DUPLICATE_KEY("重复主键", "", 1016),
    UNKNOW("未知错误", "", 1017),
    NOT_FOUND("未找到", "", 1018),
    INSUFFICIENT_BALANCE("余额不足", "", 1019),
    ENVIRONMENTAL_ANOMALY("环境异常", "", 1020),
    USERNAME_EXISTS("用户名已被占用", "", 1021),
    LOCK_FAIL("加锁失败", "", 1022),
    FAIL_TO_PASSWORD("密码错误", "", 1023),
    WALLET_NOT_EXISTS("钱包不存在", "", 1024),
    TOKEN_EXPIRATION("token失效", "", 1025),
    FAIL_TO_APPLICATION_INIT("应用/商户初始化失败", "", 1026),
    EMAIL_BE_BIND("邮箱已绑定其他用户", "", 1027),
    NOT_BIND_ACCOUNT(" 账号未绑定", "", 1028),
    ACCOUNT_BOUND(" 账号已绑定", "", 1029),
    NON_USER_REGISTERED_EMAIL(" 非用户注册邮箱", "", 1030),
    NON_SUPPORTED_CURRENCY(" 不支持的币种", "", 1031),
    NON_SUPPORTED_CHAIN(" 不支持的链", "", 1032),
    NON_SUPPORTED_OPERATE(" 不支持的操作码", "", 1033),
    ;

    private final String label_zh_cn;

    private final String label_en_us;

    private final int code;

    ResultCodeEnum(String label_zh_cn, String label_en_us, int code) {
        this.label_zh_cn = label_zh_cn;
        this.label_en_us = label_en_us;
        this.code = code;
    }
}