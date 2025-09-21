package com.machine.client.data.material.dto;

import com.machine.sdk.common.envm.data.material.DataMaterIalTextFormatEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataMaterialTextDto {

    @NotNull(message = "文本格式不能为空")
    @Schema(description = "文本格式")
    private DataMaterIalTextFormatEnum format;

    @Schema(description = "内容")
    private  String content;

}
