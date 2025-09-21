package com.machine.client.data.label.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataLabelOptionUpdateInputDto {

    @NotBlank(message = "Id不能为空")
    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;
}
