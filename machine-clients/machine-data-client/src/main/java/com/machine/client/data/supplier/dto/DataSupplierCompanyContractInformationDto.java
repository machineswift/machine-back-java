package com.machine.client.data.supplier.dto;

import com.machine.sdk.common.model.dto.data.MaterialDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DataSupplierCompanyContractInformationDto {

    /**
     * 开始日期
     */
    private Long startDate;

    /**
     * 到期日期
     */
    private Long expiryDate;

    /**
     * 合同附件
     */
    private List<MaterialDto> contractMaterialDtoList;

}
