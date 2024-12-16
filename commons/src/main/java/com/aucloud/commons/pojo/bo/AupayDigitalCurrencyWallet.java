package com.aucloud.commons.pojo.bo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

@Data
public class AupayDigitalCurrencyWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private Integer currencyId;
    private Integer currencyChain;
    private Integer walletId;
    private Integer walletChainId;
    private String address;
//    private String path;
//    private BigDecimal balance;
//    private BigDecimal feeBalance;
    private Integer useWay;
    private Date createTime;
    private Boolean isDel;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}