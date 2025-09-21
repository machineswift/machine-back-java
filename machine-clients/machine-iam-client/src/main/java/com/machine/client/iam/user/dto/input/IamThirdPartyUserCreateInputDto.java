package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamThirdPartyUserCreateInputDto {

    @NotNull(message = "来源不能为空")
    @Schema(description = "来源")
    private IamAuth2SourceEnum source;

    @NotBlank(message = "uuid不能为空")
    @Schema(description = "uuid")
    private String uuid;

    @NotBlank(message = "用户名（账号）不能为空")
    @Schema(description = "用户名（账号）")
    private String userName;

    @NotBlank(message = "昵称不能为空")
    @Schema(description = "昵称")
    private String displayName;

    @NotBlank(message = "头像不能为空")
    @Schema(description = "头像")
    private String headPictureUrl;

    @Schema(description = "内容")
    private String content;

}
