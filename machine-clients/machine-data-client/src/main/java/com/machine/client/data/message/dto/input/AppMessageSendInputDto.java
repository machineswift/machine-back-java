package com.machine.client.data.message.dto.input;

import com.machine.client.data.message.dto.output.AppMessageContentDto;
import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AppMessageSendInputDto {

    /**
     * 模版类型
     */
    @NotNull(message = "模版类型不能为空")
    private DataMessageTemplateTypeEnum messageTemplateTypeEnum;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 接收人
     */
    @NotEmpty(message = "接收人不能为空")
    private Set<String> receiverSet;

    /**
     * 是否已读取 0-未读，1-已读
     */
    @NotNull(message = "是否已读不能为空")
    private Integer readed;

    /**
     * 消息标题
     */
    @NotBlank(message = "消息标题不能为空")
    private String title;

    /**
     * 消息内容
     */
    @NotBlank(message = "消息内容不能为空")
    private String informContent;

    /**
     * 是否已处理， 0-未处理， 1-已处理
     */
    @NotNull(message = "是否已处理不能为空")
    private Integer dispose;

    /**
     * 业务内容
     */
    private AppMessageContentDto businessContentDto;

}