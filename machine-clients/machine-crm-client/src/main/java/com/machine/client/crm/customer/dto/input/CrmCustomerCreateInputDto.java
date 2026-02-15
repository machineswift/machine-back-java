package com.machine.client.crm.customer.dto.input;

import com.machine.sdk.common.envm.base.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmCustomerCreateInputDto {

    @NotBlank(message = "身份证号不能为空")
    @Schema(description = "身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String identityCardNumber;

    @Schema(description = "姓名")
    private String name;

    @NotNull(message = "性别不能为空")
    @Schema(description = "性别（CrmGenderEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private GenderEnum gender;

}
