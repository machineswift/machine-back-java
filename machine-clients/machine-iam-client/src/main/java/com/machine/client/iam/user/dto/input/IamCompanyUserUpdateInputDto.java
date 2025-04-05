package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class IamCompanyUserUpdateInputDto {

    @NotBlank(message = "id不能为空")
    @Schema(description = "用户ID")
    private String id;

    @NotNull(message = "组织ID集合不能为空")
    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @NotNull(message = "用户角色集合不能为空")
    @Schema(description = "用户角色集合")
    private List<IamUserRoleInfoUpdateInputDto> userRoleList;

    public IamCompanyUserUpdateInputDto(String id,
                                        Set<String> organizationIdSet,
                                        List<IamUserRoleInfoUpdateInputDto> userRoleList) {
        this.id = id;
        this.organizationIdSet = organizationIdSet;
        this.userRoleList = userRoleList;
    }
}
