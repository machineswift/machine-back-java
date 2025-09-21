package com.machine.client.data.message.dto.input;

import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppMessagePageSuperInputDto extends PageRequest {

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    private DataMessageChannelEnum channel;

    private DataMessageTemplateTypeEnum templateType;

    private Set<DataMessageTemplateTypeEnum> templateTypeEnums;

    private DataMessageTemplateCategoryEnum templateCategory;

    private String receiver;

    /**
     * 是否已处理， 0-未处理， 1-已处理
     */
    private Integer dispose;

    /**
     * 是否已读取 0-未读，1-已读
     */
    private Integer readed;

    /**
     * 通知内容
     */
    private String informContent;

    /**
     * 更新开始时间
     */
    private Long updateStartTime;

    /**
     * 更新结束时间
     */
    private String updateEndTime;

}