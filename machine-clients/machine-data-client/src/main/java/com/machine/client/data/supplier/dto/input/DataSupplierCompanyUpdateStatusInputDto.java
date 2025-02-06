package com.machine.client.data.supplier.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSupplierCompanyUpdateStatusInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotNull(message = "状态不能为空")
    private StatusEnum status;

}
