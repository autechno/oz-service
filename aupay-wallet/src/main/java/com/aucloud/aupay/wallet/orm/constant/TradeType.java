package com.aucloud.aupay.wallet.orm.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum TradeType {

    RECHARGE(1, "冲值"),
    WITHDRAW(2, "提款"),
    USER_TO_TRANSFER(3, "用户资金归集"),
    TRANSFER_TO_WITHDRAW(4, "提款钱包补充资金"),
    TRANSFER_TO_STORE(5, "中转钱包资产归集"),
    GAS_TO_OPERATOR(6, "补充gas费"),
    ;


    @EnumValue
    private final Integer code;
    private final String name;

    TradeType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
