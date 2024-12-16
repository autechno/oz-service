package com.aucloud.commons.pojo.do_;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("aupay_user_currency_wallet")
@Data
public class AupayUserCurrencyWallet implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private Integer walletChainId;
    private Integer currencyId;
    private Integer currencyChain;
    private BigDecimal balance;
    private BigDecimal feeBalance;
    private Integer useWay;
    private Date createTime;
    private Boolean isDel;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}