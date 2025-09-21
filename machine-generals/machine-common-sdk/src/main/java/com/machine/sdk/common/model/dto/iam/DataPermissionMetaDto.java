package com.machine.sdk.common.model.dto.iam;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class DataPermissionMetaDto {

    @Schema(description = "功能编码")
    @NotBlank(message = "功能编码不能为空")
    private String functionCode;

    @Schema(description = "功能名称")
    @NotBlank(message = "功能名称不能为空")
    private String functionName;

    @Valid
    @Schema(description = "范围")
    @NotNull(message = "范围不能为空")
    private List<Scope> scopeList;

    @Data
    @Schema
    @NoArgsConstructor
    public static class Scope {
        @Schema(description = "范围编码")
        @NotBlank(message = "范围编码不能为空")
        private String scopeCode;

        @Schema(description = "范围名称")
        @NotBlank(message = "范围名称不能为空")
        private String scopeName;
    }

}
