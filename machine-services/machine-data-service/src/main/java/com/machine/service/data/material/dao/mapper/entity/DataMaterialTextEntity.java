package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterIalTextFormatEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_text")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialTextEntity extends BaseEntity {
    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 字数统计
     */
    @TableField("word_count")
    private Integer wordCount;

    /**
     * 文本格式
     */
    @TableField("format")
    private DataMaterIalTextFormatEnum format;

    /**
     * 内容
     */
    @TableField("content")
    private String content;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}