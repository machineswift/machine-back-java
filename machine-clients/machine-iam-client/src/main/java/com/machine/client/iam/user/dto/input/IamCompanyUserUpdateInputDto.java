package com.machine.client.iam.user.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class IamCompanyUserUpdateInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotNull(message = "用户角色不能为空")
    private List<IamUserRoleInfoUpdateInputDto> userRoleList;
}
