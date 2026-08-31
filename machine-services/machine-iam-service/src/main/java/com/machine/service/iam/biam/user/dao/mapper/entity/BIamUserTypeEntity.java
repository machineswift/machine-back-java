package com.machine.service.iam.biam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_biam_user_type_relation")
@EqualsAndHashCode(callSuper = true)
public class BIamUserTypeEntity extends BaseEntity {
    /**
     * 用户Id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户类型
     * {@link BIamUserTypeEnum}
     */
    @TableField("user_type")
    private BIamUserTypeEnum userType;
}
