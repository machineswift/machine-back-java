package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterIalImageFormatEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_image")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialImageEntity extends BaseEntity {
    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 图片宽度(像素)
     */
    @TableField("width")
    private Integer width;

    /**
     * 图片高度(像素)
     */
    @TableField("height")
    private Integer height;

    /**
     * 图片格式
     */
    @TableField("format")
    private DataMaterIalImageFormatEnum format;

    /**
     * 分辨率
     */
    @TableField("dpi")
    private Integer dpi;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}