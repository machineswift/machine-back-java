package com.machine.client.data.franchisee.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class OpenApiFranchiseeUpdatePhoneInputDto {

    @NotNull(message = "加盟商ID不能为空")
    @Schema(description = "加盟商ID")
    private String id;

    @NotNull(message = "加盟商手机号不能为空")
    @Schema(description = "加盟商手机号")
    private String phone;
}
