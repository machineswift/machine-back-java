package com.machine.service.iam.biam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_biam_user_config")
@EqualsAndHashCode(callSuper = true)
public class BIamUserConfigEntity extends BaseEntity {
    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 配置键
     * {@link BIamUserConfigKeyEnum}
     */
    @TableField("config_key")
    private BIamUserConfigKeyEnum configKey;

    /**
     * 配置值（JSON字符串）
     */
    @TableField("config_value")
    private String configValue;
}
