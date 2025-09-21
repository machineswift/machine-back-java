package com.machine.client.data.supplier.dto.input;

import com.machine.sdk.common.envm.data.DataSupplierBusinessCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSupplierCompanyUpdateInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotNull(message = "供应商业务类别不能为空")
    private DataSupplierBusinessCategoryEnum businessCategory;

    @NotBlank(message = "联系人不能为空")
    private String contactName;

    @NotBlank(message = "联系电话不能为空")
    private String contactPhone;

    @NotNull(message = "通讯地址不能为空")
    private String correspondenceAddress;

    /**
     * 财务信息
     */
    private String financialInformation;

    /**
     * 合同信息
     */
    private String contractInformation;

}
