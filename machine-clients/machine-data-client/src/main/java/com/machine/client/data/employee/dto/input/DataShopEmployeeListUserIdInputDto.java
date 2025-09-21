package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class DataShopEmployeeListUserIdInputDto {

    @Schema(description = "用户ID集合")
    private Set<String> userIdSet;

    @Schema(description = "门店员工状态（门店员工状态）")
    private DataShopEmployeeStatusEnum employeeStatus;


    public DataShopEmployeeListUserIdInputDto(Set<String> userIdSet,
                                              DataShopEmployeeStatusEnum employeeStatus) {
        this.userIdSet = userIdSet;
        this.employeeStatus = employeeStatus;
    }
}
