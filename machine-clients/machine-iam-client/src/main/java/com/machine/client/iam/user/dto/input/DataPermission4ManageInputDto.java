package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataPermission4ManageInputDto {

    @NotBlank(message = "模块编码不能为空")
    @Schema(description = "模块编码")
    private String moduleCode;

    @NotBlank(message = "数据权限编码不能为空")
    @Schema(description = "数据权限编码")
    public String dataPermissionCode;

    public DataPermission4ManageInputDto(String moduleCode,
                                         String dataPermissionCode) {
        this.moduleCode = moduleCode;
        this.dataPermissionCode = dataPermissionCode;
    }

}
