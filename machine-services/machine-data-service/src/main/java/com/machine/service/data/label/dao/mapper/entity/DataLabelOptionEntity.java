package com.machine.service.data.label.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_data_label_option")
public class DataLabelOptionEntity extends BaseEntity {
    /**
     * 人工标签ID
     */
    @TableField("label_id")
    private String labelId;

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
     * 状态
     * {@link StatusEnum}
     */
    private StatusEnum status;

    /**
     * 排序
     */
    @TableField("sort")
    private Long sort;
}
