package com.aucloud.commons.pojo.do_;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("aupay_user_assets")
@Data
public class AupayUserAssets implements Serializable {
    @TableId
    private String id;
    private String userId;
    private Integer currencyId;
    private Integer currencyChain;
    private BigDecimal balance;
    private BigDecimal freezeBalance;

    private static final long serialVersionUID = 1L;

}