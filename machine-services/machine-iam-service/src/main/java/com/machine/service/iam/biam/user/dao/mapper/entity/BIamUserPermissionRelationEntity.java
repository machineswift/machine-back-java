package com.machine.service.iam.biam.user.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.biam.permission.BIamPermissionTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_biam_user_permission_relation")
public class BIamUserPermissionRelationEntity extends BaseEntity {

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
     * {@link BIamPermissionTypeEnum}
     */
    @TableField("type")
    private BIamPermissionTypeEnum type;


    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
