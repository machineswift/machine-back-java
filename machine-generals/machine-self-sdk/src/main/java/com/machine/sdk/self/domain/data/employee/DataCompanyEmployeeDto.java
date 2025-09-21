package com.machine.sdk.self.domain.data.employee;

import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataCompanyEmployeeDto {

    private String id;

    private HrmEmployeeStatusEnum employeeStatus;

    public DataCompanyEmployeeDto(String id,
                                  HrmEmployeeStatusEnum employeeStatus) {
        this.id = id;
        this.employeeStatus = employeeStatus;
    }
}
