package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class IamCompanyUserCreateInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "用户名（系统账号)不能为空")
    private String userName;

    @NotBlank(message = "编码（工号）不能为空")
    private String code;

    @NotNull(message = "状态不能为空")
    private StatusEnum status;

    @NotNull(message = "员工状态不能为空")
    private CompanyEmployeeStatusEnum employeeStatus;

    @NotBlank(message = "用户手机号不能为空")
    private String phone;

    @NotBlank(message = "用户姓名不能为空")
    private String name;

    @NotNull(message = "用户性别不能为空")
    private GenderEnum gender;

    /**
     * 描述信息
     */
    private String description;


}
