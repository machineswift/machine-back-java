package com.machine.service.iam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.IamPermissionTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_user_permission_relation")
public class IamUserPermissionRelationEntity extends BaseEntity {

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 权限id
     */
    @TableField("permission_id")
    private String permissionId;

    /**
     * {@link IamPermissionTypeEnum}
     */
    @TableField("type")
    private IamPermissionTypeEnum type;


    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
