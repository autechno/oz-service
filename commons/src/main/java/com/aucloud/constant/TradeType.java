package com.aucloud.constant;

import lombok.Getter;

@Getter
public enum TradeType {

    RECHARGE(1, "冲值"),
    WITHDRAW(2, "提款");

    private final Integer code;
    private final String name;

    TradeType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
