package com.machine.service.iam.auth.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_oauth2_authorization_consent")
public class IamOauth2AuthorizationConsentEntity extends BaseEntity {
    /**
     * 已注册客户端id
     */
    @TableField("registered_client_id")
    private String registeredClientId;
    /**
     * 主体名称
     */
    @TableField("principal_name")
    private String principalName;
    /**
     * authorities
     */
    @TableField("authorities")
    private String authorities;

}
