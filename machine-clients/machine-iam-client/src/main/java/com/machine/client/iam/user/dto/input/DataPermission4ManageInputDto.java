package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataPermission4ManageInputDto {

    @NotBlank(message = "权限编码不能空")
    @Schema(description = "权限编码")
    public String permissionCode;

}
