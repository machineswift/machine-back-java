package com.machine.client.data.label.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DataLabelOptionCreateInputDto {

    @NotBlank(message = "人工标签ID不能为空")
    private String labelId;

    @NotBlank(message = "名称不能为空")
    private String name;
}
