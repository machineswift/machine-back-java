package com.machine.client.data.employee.dto.input;

import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopEmployeeUpdateStatusInputDto {

    @NotBlank(message = "门店员工ID不能为空")
    @Schema(description = "门店员工ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @NotNull(message = "门店员工状态不能为空")
    @Schema(description = "门店员工状态（ShopEmployeeStatusEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private ShopEmployeeStatusEnum employeeStatus;

    public DataShopEmployeeUpdateStatusInputDto(String id,
                                                ShopEmployeeStatusEnum employeeStatus) {
        this.id = id;
        this.employeeStatus = employeeStatus;
    }
}
