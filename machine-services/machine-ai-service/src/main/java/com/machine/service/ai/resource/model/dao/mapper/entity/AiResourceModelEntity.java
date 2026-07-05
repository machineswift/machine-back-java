package com.machine.service.ai.resource.model.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.dto.ai.AiModelFeaturesDto;
import com.machine.starter.mybatis.BaseEntity;
import com.machine.starter.mybatis.type.JsonbTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_ai_resource_model", autoResultMap = true)
public class AiResourceModelEntity extends BaseEntity {

    /**
     * 状态
     * {@link StatusEnum}
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 厂商ID
     */
    @TableField("provider_id")
    private String providerId;

    /**
     * 模型名称
     */
    @TableField("name")
    private String name;

    /**
     * 模型编码(如deepseek-v4-flash, deepseek-v4-pro)
     */
    @TableField("code")
    private String code;

    /**
     * 扩展特性JSON
     * {@link AiModelFeaturesDto}
     */
    @TableField(value = "features", typeHandler = JsonbTypeHandler.class)
    private AiModelFeaturesDto features;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
