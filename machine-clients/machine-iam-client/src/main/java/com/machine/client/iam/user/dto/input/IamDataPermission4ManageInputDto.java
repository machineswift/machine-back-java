package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamDataPermission4ManageInputDto {

    @NotBlank(message = "权限编码不能为空")
    @Schema(description = "权限编码")
    private String permissionCode;

    @NotBlank(message = "功能编码不能为空")
    @Schema(description = "功能编码")
    private String functionCode;

    @NotBlank(message = "数据权限编码不能为空")
    @Schema(description = "数据权限编码")
    private String dataPermissionCode;

    public IamDataPermission4ManageInputDto(String permissionCode,
                                            String functionCode,
                                            String dataPermissionCode) {
        this.permissionCode = permissionCode;
        this.functionCode = functionCode;
        this.dataPermissionCode = dataPermissionCode;
    }

}
