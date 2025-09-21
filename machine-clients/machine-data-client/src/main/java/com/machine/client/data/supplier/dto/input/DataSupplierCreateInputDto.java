package com.machine.client.data.supplier.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@Data
@NoArgsConstructor
public class DataSupplierCreateInputDto {

    @NotBlank(message = "用户Id不能为空")
    private String userId;

    /**
     * 归属公司Id集合
     */
    private LinkedHashSet<String> companyIdSet;

}
