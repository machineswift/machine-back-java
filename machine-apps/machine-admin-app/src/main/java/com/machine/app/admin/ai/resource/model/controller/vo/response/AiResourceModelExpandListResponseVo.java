package com.machine.app.admin.ai.resource.model.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class AiResourceModelExpandListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "厂商ID")
    private String providerId;

    @Schema(description = "模型名称")
    private String name;

    @Schema(description = "模型编码")
    private String code;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
