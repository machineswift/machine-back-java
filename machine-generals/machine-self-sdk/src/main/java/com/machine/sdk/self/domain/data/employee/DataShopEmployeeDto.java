package com.machine.sdk.self.domain.data.employee;

import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataShopEmployeeDto {

    private String id;

    private ShopEmployeeStatusEnum employeeStatus;

    public DataShopEmployeeDto(String id,
                               ShopEmployeeStatusEnum employeeStatus) {
        this.id = id;
        this.employeeStatus = employeeStatus;
    }
}
