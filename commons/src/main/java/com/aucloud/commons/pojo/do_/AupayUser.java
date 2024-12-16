package com.aucloud.commons.pojo.do_;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @since 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("aupay_user")
public class AupayUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long id;

    private String userId;

    private String username;

    private String headPortrait;

    private Date createTime;

    private Date loginTime;

    private Integer state;

    private Integer activeState;

    private Integer onlineState;

    private Integer openChannel;

    private String password;

    private String assetsPassword;

    private String googleSecret;

    private String email;

    private String mobile;

    private String regIp;

    private Integer withdrawWhiteList;

    private String nickname;

    private Integer currencyUnit;

    private String telegram;

    private String provider;

    private String providerId;
}