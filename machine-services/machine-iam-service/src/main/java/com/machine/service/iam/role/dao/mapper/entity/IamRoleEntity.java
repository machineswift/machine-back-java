package com.machine.service.iam.role.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.IamRoleTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_iam_role")
@EqualsAndHashCode(callSuper = true)
public class IamRoleEntity extends BaseEntity {
    /**
     * 父ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 状态
     * {@link StatusEnum}
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 类型
     * {@link IamRoleTypeEnum}
     */
    @TableField("type")
    private IamRoleTypeEnum type;


    /**
     * 用户名
     */
    @TableField("name")
    private String name;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 数据权限
     */
    @TableField("data_permission_rule")
    private String dataPermissionRule;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
