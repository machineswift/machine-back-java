package com.machine.sdk.self.domain.data.employee;

import com.machine.sdk.base.envm.data.DataShopEmployeeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataShopEmployeeDto {

    private String id;

    private DataShopEmployeeStatusEnum employeeStatus;

    public DataShopEmployeeDto(String id,
                               DataShopEmployeeStatusEnum employeeStatus) {
        this.id = id;
        this.employeeStatus = employeeStatus;
    }
}
