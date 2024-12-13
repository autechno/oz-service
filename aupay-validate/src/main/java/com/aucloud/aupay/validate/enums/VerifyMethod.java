package com.aucloud.aupay.validate.enums;

public enum VerifyMethod {

    GOOGLEAUTHENICATOR("Google-Auth-Token"),
    ASSETSPASSWORD("Assets-Password-Token"),
    PASSWORD("Password-Token"),
    MOBILE("Mobile-Token"),
    PIN("Pin-Token"),
    EMAIL("Email-Token"),
    MOBILE_OR_EMAIL("MobileOrEmail-Token");

    public String tokenName;

    VerifyMethod(String tokenName) {
        this.tokenName = tokenName;
    }

    public final static String googleAuthName = "googleAuth";

    public final static String assetsPassWordName = "assetsPassWord";

    public final static String passWordName = "passWord";

    public final static String mobileName = "mobile";

    public final static String pinName = "pin";

    public final static String emailName = "email";

    public final static String mobileOrEmail = "mobileOrEmail";

}
