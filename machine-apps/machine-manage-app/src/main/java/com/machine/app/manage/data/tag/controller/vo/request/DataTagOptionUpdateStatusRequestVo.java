package com.machine.app.manage.data.tag.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagOptionUpdateStatusRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "状态不能为空")
    @Schema(description = "状态（StatusEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private StatusEnum status;
}
