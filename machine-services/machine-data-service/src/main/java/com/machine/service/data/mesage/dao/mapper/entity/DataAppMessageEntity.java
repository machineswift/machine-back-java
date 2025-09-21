package com.machine.service.data.mesage.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_app_message")
@EqualsAndHashCode(callSuper = true)
public class DataAppMessageEntity extends BaseEntity {
    /**
     * 批次编码
     */
    @TableField(value = "batch_code")
    private String batchCode;

    /**
     * 模版id
     */
    @TableField(value = "message_template_id")
    private String messageTemplateId;


    /**
     * 操作人
     */
    @TableField(value = "operator_id")
    private String operatorId;

    /**
     * 操作人手机号
     */
    @TableField(value = "operator_phone")
    private String operatorPhone;

    /**
     * 接收人
     */
    @TableField(value = "receiver")
    private String receiver;

    /**
     * 接收人手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 是否已读取
     */
    @TableField(value = "readed")
    private Boolean readed;

    /**
     * 消息标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 消息内容
     */
    @TableField(value = "inform_content")
    private String informContent;

    /**
     * 是否已处理
     */
    @TableField(value = "dispose")
    private Boolean dispose;

    /**
     * 渠道 PC、APP
     */
    @TableField(value = "channel")
    private String channel;


    /**
     * 可查询来源
     */
    @TableField(value = "query_source")
    private String querySource;

    /**
     * 业务内容
     */
    @TableField(value = "business_content")
    private String businessContent;

    @TableField(value = "dr")
    private Integer dr;
}