package com.machine.service.iam.permission.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.iam.permission.IamPermissionResourceTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_permission")
public class IamPermissionEntity extends BaseEntity {
    /**
     * 父ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     *
     * 资源类型
     * {@link IamPermissionResourceTypeEnum}
     */
    @TableField("resource_type")
    private IamPermissionResourceTypeEnum resourceType;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 数据权限元数据
     */
    @TableField("data_meta_into")
    private String dataMetaInto;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
