package com.machine.app.admin.data.brand.controller.vo.request;

import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataBrandCreateRequestVo {

    @Schema(description = "父品牌ID，为空时挂载到根节点")
    private String parentId;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @Schema(description = "排序")
    private Long sort;

    @Valid
    @NotNull(message = "LOGO不能为空")
    @Schema(description = "LOGO图片文件", requiredMode = Schema.RequiredMode.REQUIRED)
    private DataFileTempCreateDto logoFile;

    @Schema(description = "描述")
    private String description;
}


