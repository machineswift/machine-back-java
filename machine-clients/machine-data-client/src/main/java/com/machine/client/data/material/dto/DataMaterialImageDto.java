package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterIalImageFormatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialImageDto {

    @NotNull(message = "图片格式不能为空")
    @Schema(description = "图片格式")
    private DataMaterIalImageFormatEnum format;

    @NotNull(message = "图片宽度不能为空")
    @Schema(description = "图片宽度(像素)")
    private Integer width;

    @NotNull(message = "图片高度不能为空")
    @Schema(description = "图片高度(像素)")
    private Integer height;

    @Schema(description = "分辨率")
    private  Integer dpi;
}
