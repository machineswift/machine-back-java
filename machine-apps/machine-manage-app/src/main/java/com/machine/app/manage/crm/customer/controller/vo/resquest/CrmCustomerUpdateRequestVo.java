package com.machine.app.manage.crm.customer.controller.vo.resquest;

import com.machine.sdk.common.envm.base.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class CrmCustomerUpdateRequestVo {

    @NotBlank(message = "id不能为空")
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "姓名")
    private String name;

    @NotNull(message = "性别不能为空")
    @Schema(description = "性别（CrmGenderEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private GenderEnum gender;

}
