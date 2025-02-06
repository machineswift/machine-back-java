package com.machine.sdk.self.domain.data.employee;

import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataCompanyEmployeeDto {

    private String id;

    private CompanyEmployeeStatusEnum employeeStatus;

    public DataCompanyEmployeeDto(String id,
                                  CompanyEmployeeStatusEnum employeeStatus) {
        this.id = id;
        this.employeeStatus = employeeStatus;
    }
}
