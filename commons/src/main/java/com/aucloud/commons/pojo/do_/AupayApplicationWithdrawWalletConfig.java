package com.aucloud.commons.pojo.do_;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("aupay_application_withdraw_wallet_config")
@Data
public class AupayApplicationWithdrawWalletConfig implements Serializable {
    private String applicationId;

    private String walletId;

    private BigDecimal supplementTriggerAmount;

    private BigDecimal supplementAmount;

    private BigDecimal withdrawSettleTriggerAmount;

    private static final long serialVersionUID = 1L;
}