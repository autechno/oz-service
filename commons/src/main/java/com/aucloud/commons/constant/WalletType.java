package com.aucloud.commons.constant;

import lombok.Getter;

@Getter
public enum WalletType {

    WITHDRAW(1, "提币钱包"),
    TRANSFER(2, "中转钱包"),
    GAS(3, "gas钱包"),
    STORE(4, "储值钱包"),
    OPERATOR(5, "合约调用");

    private final Integer code;
    private final String name;

    WalletType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
