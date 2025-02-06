package com.machine.app.iam.dictionary.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamEnumQueryEnumInfoRequestVo {

    @NotNull(message = "枚举类名字不能为空")
    @Schema(description = "枚举类名字")
    private String enumName;
}
