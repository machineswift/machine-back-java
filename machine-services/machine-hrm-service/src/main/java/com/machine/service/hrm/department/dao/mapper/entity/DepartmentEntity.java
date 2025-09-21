package com.machine.service.hrm.department.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.beisen.envm.BeiSenDepartmentStatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_hrm_department")
public class DepartmentEntity extends BaseEntity {

    /**
     * 父ID
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 编码
     */
    @TableField("code")
    private String code;

    /**
     * 状态
     */
    @TableField("status")
    private BeiSenDepartmentStatusEnum status;

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

