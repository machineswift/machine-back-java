package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataCompanyEmployeeListUserIdInputDto {

    @Schema(description = "用户ID集合")
    private Set<String> userIdSet;

    @Schema(description = "公司员工状态（公司员工状态）")
    private HrmEmployeeStatusEnum employeeStatus;


    public DataCompanyEmployeeListUserIdInputDto(HrmEmployeeStatusEnum employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
}
