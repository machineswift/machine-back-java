package com.machine.app.manage.data.tag.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagOptionUpdateSortRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "排序不能为空")
    @Schema(description = "排序，sort值大的排序靠前", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long sort;
}
