package com.machine.app.manage.data.message.controller.vo.request;

import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ManageAppMessagePageReqVo extends PageRequest {

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    @Schema(description = "通知渠道(MessageChannelEnum)")
    private DataMessageChannelEnum channel;

    @Schema(description = "模版类型(MessageTemplateTypeEnum)")
    private DataMessageTemplateTypeEnum templateType;

    /**
     * 模版分类(合同、选址任务等)
     */
    @Schema(description = "模版分类(MessageTemplateCategoryEnum)")
    private DataMessageTemplateCategoryEnum templateCategory;

    @Schema(description = "是否已处理， 0-未处理， 1-已处理")
    private Integer dispose;

    @Schema(description = "通知内容")
    private String informContent;

    @Schema(description = "更新开始时间")
    private Long updateStartTime;

    @Schema(description = "更新结束时间")
    private String updateEndTime;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

}