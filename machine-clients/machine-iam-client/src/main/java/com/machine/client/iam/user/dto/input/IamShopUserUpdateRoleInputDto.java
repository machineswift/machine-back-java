package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamShopUserUpdateRoleInputDto {

    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "用户角色集合")
    @NotNull(message = "用户角色不能为空")
    private List<UserRoleUpdateDto> userRoleList;

    @Data
    @Schema
    @NoArgsConstructor
    public static class UserRoleUpdateDto {

        @Schema(description = "角色ID")
        private String roleId;

        @Schema(description = "门店ID集合")
        private Set<String> shopIdSet;
    }
}
