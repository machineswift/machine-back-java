package com.machine.app.admin.data.brand.controller.vo.request;

import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandUpdateRequestVo {

    @NotBlank(message = "ID不能为空")
    @Schema(description = "ID")
    private String id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Valid
    @Schema(description = "LOGO图片文件（不传则不修改LOGO）")
    private DataFileTempCreateDto logoFile;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "描述")
    private String description;
}


