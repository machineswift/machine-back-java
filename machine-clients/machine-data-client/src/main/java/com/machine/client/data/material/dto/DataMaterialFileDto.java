package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterialFileFormatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialFileDto {

    @NotNull(message = "音频格式不能为空")
    @Schema(description = "音频格式")
    private DataMaterialFileFormatEnum format;

    @Schema(description = "文件校验值")
    private String checksum;

}
