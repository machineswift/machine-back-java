package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamThirdPartyUserBindInputDto {

    @NotBlank(message = "用户Id不能为空")
    @Schema(description = "用户Id")
    private String userId;

    @NotBlank(message = "第三方用户id不能为空")
    @Schema(description = "第三方用户id")
    private String thirdPartyUserId;

}
