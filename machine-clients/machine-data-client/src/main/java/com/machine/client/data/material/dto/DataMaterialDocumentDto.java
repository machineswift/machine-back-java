package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterialDocumentFormatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialDocumentDto {

    @NotNull(message = "音频格式不能为空")
    @Schema(description = "音频格式")
    private DataMaterialDocumentFormatEnum format;

    @Schema(description = "页数")
    private Integer pageCount;
}
