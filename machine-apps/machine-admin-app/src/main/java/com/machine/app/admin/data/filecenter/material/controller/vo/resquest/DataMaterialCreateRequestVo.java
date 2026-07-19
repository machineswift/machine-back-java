package com.machine.app.admin.data.filecenter.material.controller.vo.resquest;

import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataMaterialCreateRequestVo {

    @NotNull(message = "文件类型不能为空")
    @Schema(description = "文件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataFileTypeEnum fileType;

    @NotBlank(message = "素材标题不能为空")
    @Schema(description = "素材标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @NotNull(message = "临时文件不能为空")
    @Schema(description = "临时文件")
    private DataFileTempCreateDto fileTemp;

    @Schema(description = "所属分类ID集合，可多选")
    private Set<String> categoryIdSet;

}
