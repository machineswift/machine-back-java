package com.machine.sdk.common.model.dto.iam;

import com.machine.sdk.common.envm.iam.DataPermissionScopeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema
public class DataPermissionMetaDto {

    @Schema(description = "模块编码")
    @NotBlank(message = "模块编码不能为空")
    private String moduleCode;

    @Schema(description = "模块名称")
    @NotBlank(message = "模块名称不能为空")
    private String moduleName;

    @Schema(description = "类型（DataPermissionScopeTypeEnum）")
    private DataPermissionScopeTypeEnum scopeType;

}
