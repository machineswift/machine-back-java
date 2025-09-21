package com.machine.app.manage.data.message.controller.vo.response;

import com.machine.client.data.message.dto.input.AppMessageTemplateDto;
import com.machine.client.data.message.dto.output.AppMessageTemplateInfoDto;
import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ManageAppMessageTemplateListResVo {

    @Schema(description = "模版id")
    private String id;

    /**
     * 模版类型
     */
    @Schema(description = "模版类型(MessageTemplateTypeEnum)")
    private DataMessageTemplateTypeEnum templateType;

    /**
     * 模版分类(合同、选址任务等)
     */
    @Schema(description = "模版分类(MessageTemplateCategoryEnum)")
    private DataMessageTemplateCategoryEnum templateCategory;

    /**
     * 通知渠道(站内信、AppPush、企微工作通知)
     */
    @Schema(description = "通知渠道(MessageChannelEnum)")
    private Set<DataMessageChannelEnum> channels;

    /**
     * 模版信息(通知时间、通知对象等)
     */
    @Schema(description = "模版信息")
    private AppMessageTemplateInfoDto templateInfoDto;

    /**
     * 通知标题
     */
    private String informTitle;

    /**
     * 通知内容
     */
    @Schema(description = "通知内容")
    private List<AppMessageTemplateDto> informContentDto;

    /**
     * 短信内容
     */
    @Schema(description = "短信内容")
    private String smsContent;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private String status;
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Long createTime;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updateBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Long updateTime;
}