package com.machine.service.data.leaf.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_leaf_alloc")
public class DataLeafEntity extends BaseEntity {
    /**
     * 业务标签
     */
    @TableField("biz_tag")
    private String bizTag;

    /**
     * 当前已分配的最大ID
     */
    @TableField("max_id")
    private Long maxId;

    /**
     * 每次分配的步长，默认为100
     */
    @TableField("step")
    private Integer step;


    /**
     * 过期时间
     */
    @TableField("expire_time")
    private Long expireTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}