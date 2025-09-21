package com.machine.client.data.supplier.dto.input;

import com.machine.sdk.common.envm.data.DataSupplierBusinessCategoryEnum;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataSupplierCompanyCreateInputDto {

    @Hidden
    private String id;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @NotNull(message = "供应商业务类别不能为空")
    @Schema(description = "供应商业务类别")
    private DataSupplierBusinessCategoryEnum businessCategory;

    @NotBlank(message = "联系人不能为空")
    @Schema(description = "联系人")
    private String contactName;

    @NotBlank(message = "联系电话不能为空")
    @Schema(description = "联系电话")
    private String contactPhone;

    @NotBlank(message = "通讯地址不能为空")
    @Schema(description = "通讯地址")
    private String correspondenceAddress;

    @Schema(description = "财务信息")
    private String financialInformation;

    @Schema(description = "合同信息")
    private String contractInformation;

}
