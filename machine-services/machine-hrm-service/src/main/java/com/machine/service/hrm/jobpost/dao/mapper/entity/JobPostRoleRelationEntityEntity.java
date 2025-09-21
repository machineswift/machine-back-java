package com.machine.service.hrm.jobpost.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_hrm_job_post_role_relation")
public class JobPostRoleRelationEntityEntity extends BaseEntity {

    /**
     * 职务ID
     */
    @TableField("job_post_id")
    private String jobPostId;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}