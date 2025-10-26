package com.machine.app.manage.crm.customer.controller.vo.resquest;

import com.machine.sdk.common.envm.common.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmCustomerCreateRequestVo {

    @NotBlank(message = "身份证号不能为空")
    @Pattern(regexp = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)", message = "身份证号格式不正确")
    @Schema(description = "身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String identityCardNumber;

    @Schema(description = "姓名")
    private String name;

    @NotNull(message = "性别不能为空")
    @Schema(description = "性别（CrmGenderEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private GenderEnum gender;

}
