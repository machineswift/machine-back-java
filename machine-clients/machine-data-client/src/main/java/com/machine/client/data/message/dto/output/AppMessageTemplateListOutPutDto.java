package com.machine.client.data.message.dto.output;

import com.machine.client.data.message.dto.input.AppMessageTemplateDto;
import com.machine.sdk.common.envm.data.message.DataMessageChannelEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class AppMessageTemplateListOutPutDto {

    private String id;

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
    private Set<DataMessageChannelEnum> channels;

    /**
     * 通知标题
     */
    private String informTitle;

    /**
     * 模版信息(通知时间、通知对象等)
     */
    private AppMessageTemplateInfoDto templateInfoDto;

    /**
     * 通知内容
     */
    private List<AppMessageTemplateDto> informContentDto;

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