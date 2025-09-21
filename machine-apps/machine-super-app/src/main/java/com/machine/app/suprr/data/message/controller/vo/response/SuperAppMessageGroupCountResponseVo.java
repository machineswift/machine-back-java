package com.machine.app.suprr.data.message.controller.vo.response;

import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SuperAppMessageGroupCountResponseVo {

    /**
     * 模版分类(合同、选址任务等)
     */
    @Schema(description = "模版分类(MessageTemplateCategoryEnum)")
    private DataMessageTemplateCategoryEnum templateCategory;

    /**
     * 通知内容
     */
    @Schema(description = "通知内容")
    private String informContent;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Long updateTime;

    /**
     * 数量
     */
    @Schema(description = "数量")
    private Integer count;
}