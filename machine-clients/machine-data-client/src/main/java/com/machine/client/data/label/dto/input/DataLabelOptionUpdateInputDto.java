package com.machine.client.data.label.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DataLabelOptionUpdateInputDto {

    @NotBlank(message = "Id不能为空")
    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;
}
