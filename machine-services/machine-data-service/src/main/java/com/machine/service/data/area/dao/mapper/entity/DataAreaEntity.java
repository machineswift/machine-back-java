package com.machine.service.data.area.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_area")
@EqualsAndHashCode(callSuper = true)
public class DataAreaEntity extends BaseEntity {
    /**
     * 父id
     */
    @TableField("parent_id")
    private String parentId;

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
     * 排序
     */
    @TableField("sort")
    private Long sort;
}