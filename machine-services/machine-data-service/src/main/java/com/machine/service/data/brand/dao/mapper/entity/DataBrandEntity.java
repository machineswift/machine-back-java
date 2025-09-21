package com.machine.service.data.brand.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.starter.mybatis.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_brand")
@EqualsAndHashCode(callSuper = true)
public class DataBrandEntity extends BaseEntity {

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
    @TableField("status")
    private StatusEnum status;

    /**
     * LOGO 素材Id
     */
    @TableField("logo_material_id")
    private String logoMaterialId;


    /**
     * 描述
     */
    @TableField("description")
    private String description;


}
