package com.machine.client.data.filecenter.attachment.dto.input;

import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataFileTempCreateInputDto {

    @NotBlank(message = "原始名称不能为空")
    @Schema(description = "原始名称")
    private String originalName;

    @NotBlank(message = "存储名称不能为空")
    @Schema(description = "存储名称")
    private String storageName;

    @Schema(description = "存储路径")
    private String storagePath;

    @NotBlank(message = "文件信息不能为空")
    @Schema(description = "文件存储信息（JSON格式）")
    private String fileInfo;

    @NotNull(message = "大小不能为空")
    @Schema(description = "大小（字节）")
    private Long size;

    @NotNull(message = "文件类型不能为空")
    @Schema(description = "文件类型")
    private DataFileTypeEnum fileType;

    @NotNull(message = "过期时间不能为空")
    @Schema(description = "过期时间（Unix 毫秒），过期后定时清理")
    private Long expireTime;
}
