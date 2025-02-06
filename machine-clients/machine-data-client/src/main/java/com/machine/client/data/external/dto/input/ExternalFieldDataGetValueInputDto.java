package com.machine.client.data.external.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExternalFieldDataGetValueInputDto {

    @NotBlank(message = "表名不能为空")
    private String tableName;

    @NotBlank(message = "字段名不能为空")
    private String fieldName;

    @NotBlank(message = "数据Id不能为空")
    private String dataId;
}
