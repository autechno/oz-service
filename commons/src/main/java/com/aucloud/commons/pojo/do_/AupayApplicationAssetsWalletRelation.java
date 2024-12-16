package com.aucloud.commons.pojo.do_;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("aupay_application_assets_wallet_relation")
public class AupayApplicationAssetsWalletRelation implements Serializable {

    private String applicationId;

    private String walletId;

    private static final long serialVersionUID = 1L;
}