package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterialDocumentFormatEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_document")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialDocumentEntity extends BaseEntity {
    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 文档格式
     */
    @TableField("format")
    private DataMaterialDocumentFormatEnum format;

    /**
     * 页数
     */
    @TableField("page_count")
    private Integer pageCount;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}