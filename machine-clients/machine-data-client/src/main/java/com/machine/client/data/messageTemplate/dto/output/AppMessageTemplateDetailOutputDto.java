package com.machine.client.data.messageTemplate.dto.output;

import com.machine.sdk.common.envm.data.message.MessageChannelEnum;
import com.machine.sdk.common.envm.data.message.MessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.MessageTemplateTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AppMessageTemplateDetailOutputDto {
    private String id;

    /**
     * 模版类型
     */
    private MessageTemplateTypeEnum templateType;

    /**
     * 模版分类(合同、选址任务等)
     */
    private MessageTemplateCategoryEnum templateCategory;

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    private Set<MessageChannelEnum> channels;

    /**
     * 模版信息(通知时间、通知对象等)
     */
    private AppMessageTemplateInfoDto templateInfoDto;

    /**
     * 通知标题
     */
    private String informTitle;

    /**
     * 通知内容
     */
    private String informContent;

    /**
     * 短信内容
     */
    private String smsContent;


    /**
     * 状态
     */
    private String status;
    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Long updateTime;
}