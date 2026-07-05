package com.machine.client.data.filecenter.attachment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataFileTempCreateDto {

    @NotBlank(message = "文件ID不能为空")
    @Schema(description = "文件ID")
    private String fileId;

    @Schema(description = "排序，sort值大的排序靠前")
    private Long sort;

    @Schema(description = "扩展信息JSON")
    private String features;

}
