package com.machine.client.data.message.dto.output;

import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppMessageListSuperOutputDto {
    /**
     * 消息id
     */
    private String id;

    /**
     * 模版id
     */
    private String messageTemplateId;

    /**
     * 模版类型
     */
    private DataMessageTemplateTypeEnum templateType;

    /**
     * 模版分类(合同、选址任务等)
     */
    private DataMessageTemplateCategoryEnum templateCategory;

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    private DataMessageChannelEnum channel;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 通知内容
     */
    private String informContent;

    /**
     * 短信内容
     */
    private String smsContent;

    /**
     * 是否已读取 0-未读，1-已读
     */
    private Integer readed;

    /**
     * 是否已处理， 0-未处理， 1-已处理
     */
    private Integer dispose;
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

    /**
     * 业务内容
     */
    private AppMessageContentDto businessContentDto;

    /**
     * 业务内容
     */
    private String businessContent;

}