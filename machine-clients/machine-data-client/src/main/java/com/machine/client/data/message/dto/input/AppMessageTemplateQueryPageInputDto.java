package com.machine.client.data.message.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
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
public class AppMessageTemplateQueryPageInputDto extends PageRequest {

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