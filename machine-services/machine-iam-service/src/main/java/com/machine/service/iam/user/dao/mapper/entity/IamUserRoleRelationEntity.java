package com.machine.service.iam.user.dao.mapper.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_iam_user_role_relation")
public class IamUserRoleRelationEntity extends BaseEntity {
    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 角色id
     */
    @TableField("role_id")
    private String roleId;


    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;

}
