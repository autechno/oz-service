package com.aucloud.aupay.wallet.orm.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CollectEventType {

    USER_TO_TRANSFER(1, "用户2中转"),
    TRANSFER_TO_WITHDRAW(2, "中转2提币"),
    TRANSFER_TO_STORE(3, "中转2储备"),
    GAS_TO_OPERATOR(4, "gas补充");

    @EnumValue
    private final Integer code;
    private final String name;

    CollectEventType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
