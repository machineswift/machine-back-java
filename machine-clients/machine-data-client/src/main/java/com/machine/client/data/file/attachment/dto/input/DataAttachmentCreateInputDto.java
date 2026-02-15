package com.machine.client.data.file.attachment.dto.input;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataAttachmentCreateInputDto {

    @NotNull(message = "实体不能为空")
    @Schema(description = "实体（ModuleEntityEnum）")
    private ModuleEntityEnum entity;

    @NotBlank(message = "实体Id不能为空")
    @Schema(description = "实体Id）")
    private String entityId;

    @NotNull(message = "文件类型不能为空")
    @Schema(description = "文件类型")
    private  DataFileTypeEnum fileType;

    @NotBlank(message = "原始名称不能为空")
    @Schema(description = "原始名称")
    private String originalName;

    @NotBlank(message = "存储名称不能为空")
    @Schema(description = "存储名称")
    private String storageName;

    @NotBlank(message = "文件MD5不能为空")
    @Schema(description = "文件MD5-用于去重和秒传")
    private String md5Hash;

    @NotBlank(message = "文件信息不能为空")
    @Schema(description = "文件信息")
    private String fileInfo;

    @NotNull(message = "大小（字节）不能为空")
    @Schema(description = "大小（字节）")
    private Long size;

    @NotNull(message = "过期时间不能为空")
    @Schema(description = "过期时间")
    private Long expireTime;
}


