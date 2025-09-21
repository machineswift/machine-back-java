package com.machine.service.plugin.xgj.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("xgj_user")
public class XgjUserEntity {

    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户中心的用户ID
    @TableField("center_user_id")
    private Long centerUserId;

    // 邮箱地址
    @TableField("email")
    private String email;

    // 电话号码
    @TableField("phone")
    private String phone;

    // 用户状态
    @TableField("status")
    private Integer status;

    // 用户名
    @TableField("user_name")
    private String username;

    // 用户类型（1-加盟商，2-供应商）
    @TableField("user_type")
    private Integer userType;

    // 工号
    @TableField("position")
    private String position;

    // 归属信息Json
    @TableField("attribution_info")
    private String attributionInfo;

    // 系统ID
    @TableField("system_id")
    private Long systemId;

    // 用户名
    @TableField("account")
    private String account;

    // 密码
    @TableField("password")
    private String password;

    // 创建人
    @TableField("create_person")
    private String createPerson;

    // 创建时间
    @TableField("create_time")
    private Date createTime;

    // 备用字段
    @TableField("dr")
    private Integer dr;

    // 更新人
    @TableField("update_person")
    private String updatePerson;

    // 更新时间
    @TableField("update_time")
    private Date updateTime;

    // 实例ID
    @TableField("instance_id")
    private Long instanceId;

    // 租户ID
    @TableField("tenant_id")
    private Long tenantId;
}

