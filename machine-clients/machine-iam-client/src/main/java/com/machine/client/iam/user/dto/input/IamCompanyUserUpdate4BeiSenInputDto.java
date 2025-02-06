package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamCompanyUserUpdate4BeiSenInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "用户手机号不能为空")
    private String phone;

    @NotNull(message = "员工状态不能为空")
    private CompanyEmployeeStatusEnum employeeStatus;
}
