package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterialVideoFormatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialVideoDto {

    @NotNull(message = "音频格式不能为空")
    @Schema(description = "音频格式")
    private DataMaterialVideoFormatEnum format;

    @NotNull(message = "时长(秒)")
    @Schema(description = "时长(秒)")
    private Integer duration;

    @NotNull(message = "视频宽度(像素)不能为空")
    @Schema(description = "视频宽度(像素)")
    private Integer width;

    @NotNull(message = "视频高度(像素)不能为空")
    @Schema(description = "视频高度(像素)")
    private Integer height;

    @Schema(description = "比特率(kbps)")
    private Integer bitrate;

    @Schema(description = "帧率")
    private Integer fps;

    @Schema(description = "视频编码格式")
    private String codec;

    @Schema(description = "音频编码格式")
    private String audioCodec;
}
