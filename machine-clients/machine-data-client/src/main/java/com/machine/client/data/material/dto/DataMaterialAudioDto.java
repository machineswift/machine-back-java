package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterialAudioFormatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialAudioDto {

    @NotNull(message = "音频格式不能为空")
    @Schema(description = "音频格式")
    private DataMaterialAudioFormatEnum format;

    @NotNull(message = "时长(秒)")
    @Schema(description = "时长(秒)")
    private Integer duration;

    @NotNull(message = "比特率(kbps)不能为空")
    @Schema(description = "比特率(kbps)")
    private Integer bitrate;

    @Schema(description = "采样率(Hz)")
    private Integer sampleRate;

    @Schema(description = "声道数(1-单声道,2-立体声)")
    private Integer channels;
}
