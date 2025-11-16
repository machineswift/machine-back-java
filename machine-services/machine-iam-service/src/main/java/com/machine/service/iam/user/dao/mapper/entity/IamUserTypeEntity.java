package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.user.IamUserTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_iam_user_type_relation")
@EqualsAndHashCode(callSuper = true)
public class IamUserTypeEntity extends BaseEntity {
    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户类型
     * {@link IamUserTypeEnum}
     */
    @TableField("user_type")
    private IamUserTypeEnum userType;
}
