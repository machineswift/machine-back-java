package com.machine.client.iam.role.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamRoleCodeInputDto {

    @Schema(description = "编码")
    @NotBlank(message = "编码不能为空")
    private String code;

    public IamRoleCodeInputDto(String code) {
        this.code = code;
    }
}
