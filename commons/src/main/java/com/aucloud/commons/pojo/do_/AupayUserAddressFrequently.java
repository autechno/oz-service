package com.aucloud.commons.pojo.do_;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

@TableName("aupay_user_address_frequently")
@Data
public class AupayUserAddressFrequently implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private Integer type;
    private Integer currencyId;
    private Integer currencyChain;
    private String address;
    private String remark;
    private Date createTime;
    private Boolean del;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}