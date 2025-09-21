package com.machine.client.iam.userbk.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamFranchiseeUserCreateInputDto {

    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    public IamFranchiseeUserCreateInputDto(String phone,
                                           String name) {
        this.phone = phone;
        this.name = name;
    }
}
