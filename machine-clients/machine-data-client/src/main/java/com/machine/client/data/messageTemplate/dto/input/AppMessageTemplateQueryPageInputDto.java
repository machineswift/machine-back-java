package com.machine.client.data.messageTemplate.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.message.MessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.MessageTemplateTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppMessageTemplateQueryPageInputDto extends PageRequest {

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
    private Set<String> channels;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 修改人ID集合
     */
    private Set<String> updateUserIdSet;

    /**
     * 更新开始时间
     */
    private Long updateStartTime;

    /**
     * 更新结束时间
     */
    private String updateEndTime;
}