package com.machine.service.data.mesage.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName("t_data_app_message_template")
@EqualsAndHashCode(callSuper = true)
public class DataAppMessageTemplateEntity extends BaseEntity {

    /**
     * 模版类型
     */
    @TableField(value = "template_type")
    private String templateType;

    /**
     * 模版类型
     */
    @TableField(value = "template_category")
    private String templateCategory;

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    @TableField(value = "channel")
    private String channel;

    /**
     * 模版信息(通知时间、通知对象等)
     */
    @TableField(value = "template_info")
    private String templateInfo;

    /**
     * 通知标题
     */
    @TableField(value = "inform_title")
    private String informTitle;

    /**
     * 通知内容
     */
    @TableField(value = "inform_content")
    private String informContent;

    /**
     * 短信内容
     */
    @TableField(value = "sms_content")
    private String smsContent;

    /**
     * 状态
     */
    @TableField(value = "status")
    private String status;

    @TableField(value = "dr")
    private Integer dr;

}