package com.machine.service.ai.resource.model.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.ai.AiProviderEnum;
import com.machine.starter.mybatis.BaseEntity;
import com.machine.starter.mybatis.type.EncryptTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_ai_resource_provider", autoResultMap = true)
public class AiResourceProviderEntity extends BaseEntity {

    /**
     * 状态
     * {@link StatusEnum}
     */
    @TableField("status")
    private StatusEnum status;

    /**
     * 厂商标识
     * {@link AiProviderEnum}
     */
    @TableField("provider")
    private AiProviderEnum provider;

    /**
     * API基础地址
     */
    @TableField("base_url")
    private String baseUrl;

    /**
     * API密钥
     */
    @TableField(value = "api_key", typeHandler = EncryptTypeHandler.class)
    private String apiKey;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}
