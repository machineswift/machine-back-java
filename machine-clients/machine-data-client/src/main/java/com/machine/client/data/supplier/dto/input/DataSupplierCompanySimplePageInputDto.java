package com.machine.client.data.supplier.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataSupplierCompanySimplePageInputDto extends PageRequest {

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private StatusEnum status;

}
