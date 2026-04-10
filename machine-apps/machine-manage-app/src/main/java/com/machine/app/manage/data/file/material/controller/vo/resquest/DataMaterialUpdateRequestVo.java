package com.machine.app.manage.data.file.material.controller.vo.resquest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataMaterialUpdateRequestVo {

    @NotBlank(message = "素材ID不能为空")
    @Schema(description = "素材ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotBlank(message = "素材标题不能为空")
    @Schema(description = "素材标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "所属分类ID集合，可多选")
    private Set<String> categoryIdSet;

    @NotBlank(message = "附件ID不能为空")
    @Schema(description = "附件ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String attachmentId;

}
