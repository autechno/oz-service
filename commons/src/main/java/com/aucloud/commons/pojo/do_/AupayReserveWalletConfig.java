package com.aucloud.commons.pojo.do_;

import java.io.Serializable;

public class AupayReserveWalletConfig extends AupayReserveWalletConfigKey implements Serializable {
    private String walletId;

    private static final long serialVersionUID = 1L;

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }
}