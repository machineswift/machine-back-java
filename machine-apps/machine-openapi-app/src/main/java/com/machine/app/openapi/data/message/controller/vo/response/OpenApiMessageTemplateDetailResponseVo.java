package com.machine.app.openapi.data.message.controller.vo.response;

import com.machine.client.data.message.dto.output.AppMessageTemplateInfoDto;
import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class OpenApiMessageTemplateDetailResponseVo {

    @Schema(description = "Id")
    private String id;

    @Schema(description = "模版类型(MessageTemplateTypeEnum)")
    private DataMessageTemplateTypeEnum templateType;

    @Schema(description = "模版分类(合同、选址任务等)(MessageTemplateCategoryEnum)")
    private DataMessageTemplateCategoryEnum templateCategory;

    @Schema(description = "通知渠道(站内信、AppPush、企微工作通知)(MessageChannelEnum)")
    private List<DataMessageChannelEnum> channels;

    @Schema(description = "模版信息(通知时间、通知对象等)")
    private AppMessageTemplateInfoDto templateInfoDto;

    @Schema(description = "通知标题")
    private String informTitle;

    @Schema(description = "通知内容")
    private String informContent;

    @Schema(description = "短信内容")
    private String smsContent;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "更新时间")
    private Long updateTime;
}