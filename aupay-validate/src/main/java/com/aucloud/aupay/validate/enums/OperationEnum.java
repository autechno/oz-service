package com.aucloud.aupay.validate.enums;

import com.aucloud.commons.constant.ResultCodeEnum;
import com.aucloud.commons.constant.Terminal;
import com.aucloud.commons.exception.ServiceRuntimeException;

public enum OperationEnum {

    INDEX(1,"引导","index"),
    REGISTER(2,"注册","注册"),
    ADMIN_LOGIN(3,"登录","{0},登录成功","","", Terminal.ADMIN),
    USER_LOGIN(4,"登录","用户{0},登录成功","","",Terminal.USER),
    GET_USER_INFO(5,"获取用户自身信息","获取用户自身信息","","", Terminal.USER),
    SET_USER_INFO(6,"用户修改个人信息","用户修改个人信息","","", Terminal.USER),
    ADMIN_LOGIN_OUT(7,"管理员登出","{0},登出"),
    USER_LOGIN_OUT(8,"用户登出","{0},登出"),
    FIND_WITHDRAW_ADDRESS(9,"获取提款地址","获取提款地址","","", Terminal.USER),
    FIND_USER_WITHDRAW_ADDRESS(92,"获取提款地址","获取提款地址","","", Terminal.ADMIN),
    ADD_USER_WITHDRAW_ADDRESS(91,"添加提款地址","添加提款地址","EMAIL,ASSETSPASSWORD,GOOGLEAUTHENICATOR","", Terminal.USER),
    SWITCH_WHITE_ADDRESS(10,"开关白名单提币","开关白名单提币","EMAIL,ASSETSPASSWORD,GOOGLEAUTHENICATOR","", Terminal.USER),
    DELETE_WITHDRAW_ADDRES(101,"删除白名单","删除白名单","","", Terminal.USER),
    ADD_WITHDRAW_ADDRES(102,"添加白名单","添加白名单","EMAIL,ASSETSPASSWORD,GOOGLEAUTHENICATOR","", Terminal.USER),
    FIND_USER_APPLY_APPCATION(11,"查看授权应用","查看授权应用","","", Terminal.USER),
    UNBIND_USER_APPLY_APPCATION(12,"解绑授权应用","解绑授权应用","EMAIL,GOOGLEAUTHENICATOR","", Terminal.USER),
    GET_RECHARGE_INFO(13,"获取充币信息","获取充币信息","","", Terminal.USER),
    GET_ASSETS_INFO(14,"获取资产信息","获取资产信息","","", Terminal.USER),
    GET_CURRENCY_ASSETS_INFO(14,"获取某币种资产信息","获取某币种资产信息","","", Terminal.USER),
    FIND_ADMIN_IP(15,"获取管理员IP","获取管理员IP","","", Terminal.ADMIN),
    ADD_ADMIN_IP(151,"添加管理员IP","添加管理员IP","","PIN", Terminal.ADMIN),
    DELETE_ADMIN_IP(152,"删除管理员IP","删除管理员IP","","PIN", Terminal.ADMIN),
    SWITCH_DISABLE_ADMIN_IP(153,"开启禁用管理员IP","开启禁用管理员IP","PIN","", Terminal.ADMIN),
    FIND_ADMIN_IP_LOG(16,"获取管理员IP记录","获取管理员IP记录","","", Terminal.ADMIN),
    ADMIN_UPDATE_PASSWORD(17,"修改密码","修改密码","","", Terminal.ADMIN),
    ADMIN_UPDATE_PIN(17,"修改PIN码","修改PIN码","","", Terminal.ADMIN),
    ADMIN_BIND_GOOGLE_AUTH(18,"绑定谷歌验证","绑定谷歌验证","","", Terminal.ADMIN),
    ADMIN_UPDATE_BASE_INFO(19,"修改个人资料","修改个人资料","","", Terminal.ADMIN),
    VERIFY_PIN(20,"验证PIN码","验证PIN码","","", Terminal.ADMIN),
    VERIFY_GOOGLE_AUTH(21,"验证谷歌验证","验证谷歌验证","","", Terminal.ADMIN),
    USER_VERIFY_GOOGLE_AUTH(211,"验证谷歌验证","验证谷歌验证","","", Terminal.USER),
    FIND_PERMISSION_LIST(22,"获取自身权限列表","获取自身权限列表","","", Terminal.ADMIN),
    OPEN_ADMIN(230,"新增员工","新增员工","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    FIND_ADMIN(23,"获取管理员列表","获取管理员列表","","", Terminal.ADMIN),
    FIND_ADMIN_LOGIN_LOG(24,"获取管理员登录日志","获取管理员登录日志","","", Terminal.ADMIN),
    FIND_ADMIN_PERMISSION_LIST(25,"获取管理员权限列表","获取管理员权限列表","","", Terminal.ADMIN),
    SET_ADMIN_PERMISSION(251,"设置管理员权限","设置管理员权限","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    FIND_ADMIN_OPERATION_LOG(26,"获取管理员操作日志","获取管理员操作日志","","", Terminal.ADMIN),
    CLOSE_ADMIN(27,"关闭管理员账号","关闭管理员账号","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    SWITCH_FREEZE_ADMIN(28,"开关冻结管理员","开关冻结管理员","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    SET_ADMIN_DEPARTMENT(29,"修改管理员部门","修改管理员部门","","", Terminal.ADMIN),
    RESET_ADMIN_PIN(30,"重置管理员PIN码","重置管理员PIN码","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    RESET_ADMIN_PASSWORD(301,"重置管理员密码","重置管理员密码","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    RESET_ADMIN_GOOGLE_AUTH(31,"重置管理员谷歌验证","重置管理员谷歌验证","PIN","", Terminal.ADMIN),
    FIND_ANNOUNCEMENT_LIST(32,"获取公告列表","获取公告列表","","", Terminal.ADMIN),
    ADD_ANNOUNCEMENT(33,"添加公告","添加公告","","", Terminal.ADMIN),
    UPDATE_ANNOUNCEMENT(34,"修改公告","修改公告","","", Terminal.ADMIN),
    DELETE_ANNOUNCEMENT(35,"删除公告","删除公告","","", Terminal.ADMIN),
    FIND_CURRENCY_EXCHANGE_RATE(36,"获取汇率","获取汇率","","", Terminal.ADMIN),
    FIND_WITHDRAW_CONFIG(37,"获取提款配置","获取提款配置","","", Terminal.ADMIN),
    UPDATE_WITHDRAW_CONFIG(371,"修改提款配置","修改提款配置","","", Terminal.ADMIN),
    FIND_USER_ASSETS_COLLECTION_CONFIG(38,"获取用户资产归集配置","获取用户资产归集配置","","", Terminal.ADMIN),
    FIND_TRANSFER_WALLET_CONFIG(39,"获取中转钱包配置","获取中转钱包配置","","", Terminal.ADMIN),
    UPDATE_TRANSFER_WALLET_CONFIG(391,"修改中转钱包配置","修改中转钱包配置","","", Terminal.ADMIN),
    FIND_RESERVE_WALLET_CONFIG(40,"获取储备钱包配置","获取储备钱包配置","","", Terminal.ADMIN),
    UPDATE_RESERVE_WALLET_CONFIG(401,"修改储备钱包配置","获取储备钱包配置","","", Terminal.ADMIN),
    UPDATE_FEE_WALLET_CONFIG(402,"修改手续费钱包配置","修改手续费钱包配置","","", Terminal.ADMIN),
    FIND_USER_LIST(41,"查看用户列表","查看用户列表","","", Terminal.ADMIN),
    GET_USER_DETAIL(42,"查看用户详情","查看用户详情","","", Terminal.ADMIN),
    SWITCH_FREEZE_USER(421,"冻结解冻用户","冻结解冻用户","","", Terminal.ADMIN),
    FIND_USER_TRADE_RECORD_LIST(43,"查看用户交易记录","查看用户交易记录","","", Terminal.ADMIN),
    FIND_USER_RECHARGE_RECORD_LIST(44,"查看用户充币记录","查看用户充币记录","","", Terminal.ADMIN),
    FIND_USER_WITHDRAW_RECORD_LIST(45,"查看用户提币记录","查看用户提币记录","","", Terminal.ADMIN),
    FIND_USER_ASSETS_CHANGE_RECORD_LIST(46,"查看用户资产变动记录","查看用户资产变动记录","","", Terminal.ADMIN),
    USER_RESET_ASSETS_PASSWORD(47,"重置资金密码","重置资金密码","GOOGLEAUTHENICATOR,EMAIL","", Terminal.USER),
    USER_RESET_GOOGLE_AUTH(48,"重置谷歌验证","重置谷歌验证","ASSETSPASSWORD,EMAIL","", Terminal.USER),
    USER_UPDATE_PASSWORD(49,"用户修改密码","用户修改密码","","", Terminal.USER),
    USER_UPDATE_ASSETS_PASSWORD(491,"用户修改资金密码","用户修改资金密码","","", Terminal.USER),
    USER_FIND_TRADE_RECORD_LIST(50,"用户查看交易记录","用户查看交易记录","","", Terminal.USER),
    CHECK_TRADE_RECORD_DETAIL(501,"用户查看交易记录详情","用户查看交易记录详情","","", Terminal.USER),
    USER_FIND_RECHARGE_RECORD_LIST(51,"用户查看充币记录","用户查看交易记录","","", Terminal.USER),
    USER_FIND_WITHDRAW_RECORD_LIST(52,"用户查看提款记录","用户查看提款记录","","", Terminal.USER),
    USER_FIND_ASSETS_CHANGE_RECORD_LIST(53,"用户查看帐变记录","用户查看帐变记录","","", Terminal.USER),
    FIND_WALLET_TRANSFER_RECORD(54,"查看钱包转账记录","查看钱包转账记录","","", Terminal.ADMIN),
    GET_TRANSFER_WALLET_INFO(55,"查看中转钱包信息","查看中转钱包信息","","", Terminal.ADMIN),
    GET_RESERVE_WALLET_INFO(56,"查看储备钱包信息","查看储备钱包信息","","", Terminal.ADMIN),
    GET_WITHDRAW_WALLET_INFO(57,"查看提币钱包信息","查看提币钱包信息","","", Terminal.ADMIN),
    GET_FEE_WALLET_INFO(58,"查看手续费钱包信息","查看手续费钱包信息","","", Terminal.ADMIN),
    GET_RECENT_LOGIN_LOG(59,"获取最近登录日志","获取最近登录日志","","", Terminal.USER),
    USER_BIND_GOOGLE_AUTH(60,"绑定谷歌验证器","绑定谷歌验证器","","", Terminal.USER),
    SEND_EMAIL_CODE(61,"用户发送邮箱验证码","用户发送邮箱验证码","","", Terminal.USER),
    VERIFY_EMAIL(62,"验证邮箱","验证邮箱","","", Terminal.USER),
    VERIFY_ASSETS_PASSWORD(63,"验证资金密码","验证资金密码","","", Terminal.USER),
    WITHDRAW(64,"提币","提币","ASSETSPASSWORD","", Terminal.USER),
    USER_RESET_PASSWORD(65,"用户重置密码","用户重置密码","","", Terminal.USER),
    SEND_RESET_PASSWORD_EMAIL_CODE(66,"发送重置密码邮箱验证","发送重置密码邮箱验证","","", Terminal.USER),
    REGEN_USER_ASSETS_WALLET(67,"重新生成用户地址","重新生成用户地址","PIN,GOOGLEAUTHENICATOR","", Terminal.ADMIN),
    USER_ASSETS_COLLECT(68,"用户资产归集","用户资产归集","","", Terminal.ADMIN),
    FAST_SWAP(69,"闪兑","闪兑","","", Terminal.USER),
    USER_ADDRESS_FREQUENTLY(70,"用户地址管理","用户地址管理","","", Terminal.USER),
    OZBET_ASSETS_EXCHARGE(12100,"aupay钱包转账","aupay钱包转账","","", Terminal.USER),

    ;

    public final Integer operationId;

    public final String operationName;

    public final String operationContentTemplate;

    public String verifyMethods;

    public String permission;

    public Integer terminal;

    OperationEnum(Integer operationId, String operationName, String operationContentTemplate) {
        this.operationId = operationId;
        this.operationName = operationName;
        this.operationContentTemplate = operationContentTemplate;
    }

    OperationEnum(Integer operationId, String operationName, String operationContentTemplate, String verifyMethods) {
        this.operationId = operationId;
        this.operationName = operationName;
        this.operationContentTemplate = operationContentTemplate;
        this.verifyMethods = verifyMethods;
    }

    OperationEnum(Integer operationId, String operationName, String operationContentTemplate, String verifyMethods, String permission) {
        this.operationId = operationId;
        this.operationName = operationName;
        this.operationContentTemplate = operationContentTemplate;
        this.verifyMethods = verifyMethods;
        this.permission = permission;
    }

    OperationEnum(Integer operationId, String operationName, String operationContentTemplate, String verifyMethods, String permission, Integer terminal) {
        this.operationId = operationId;
        this.operationName = operationName;
        this.operationContentTemplate = operationContentTemplate;
        this.verifyMethods = verifyMethods;
        this.permission = permission;
        this.terminal = terminal;
    }

    public static OperationEnum getById(Integer operationId) {
        OperationEnum[] values = OperationEnum.values();
        for (OperationEnum operationEnum : values) {
            if(operationEnum.operationId.equals(operationId)) {
                return operationEnum;
            }
        }
        throw new ServiceRuntimeException(ResultCodeEnum.NON_SUPPORTED_OPERATE);
    }

}

