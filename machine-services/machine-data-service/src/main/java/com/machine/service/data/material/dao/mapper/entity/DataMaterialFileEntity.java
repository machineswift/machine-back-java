package com.machine.service.data.material.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.material.DataMaterialFileFormatEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_material_file")
@EqualsAndHashCode(callSuper = true)
public class DataMaterialFileEntity extends BaseEntity {
    /**
     * 素材ID
     */
    @TableField("material_id")
    private String materialId;

    /**
     * 文件格式
     */
    @TableField("format")
    private DataMaterialFileFormatEnum format;

    /**
     * 文件校验值
     */
    @TableField("checksum")
    private String checksum;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}