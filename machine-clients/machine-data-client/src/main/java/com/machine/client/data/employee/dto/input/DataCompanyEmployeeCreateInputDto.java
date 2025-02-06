package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DataCompanyEmployeeCreateInputDto {

    @NotBlank(message = "用户Id不能为空")
    private String userId;

    @NotNull(message = "员工状态不能为空")
    private CompanyEmployeeStatusEnum employeeStatus;

}
