package com.machine.app.manage.data.tag.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataTagOptionQueryListRequestVo  {

    @NotBlank(message = "智能标签ID不能为空")
    @Schema(description = "智能标签ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tagId;

}
