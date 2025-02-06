package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamUserUpdatePhoneInputDto {

    @NotNull(message = "ID不能为空")
    @Schema(description = "ID")
    private String id;

    @NotNull(message = "手机号不能为空")
    @Schema(description = "手机号")
    private String phone;

    public IamUserUpdatePhoneInputDto(String id,
                                      String phone) {
        this.id = id;
        this.phone = phone;
    }
}
