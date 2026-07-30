package com.machine.app.iam.identity.controller.vo.request;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamAuth2RegisteredClientUpdateStatusRequestVo {

    @NotBlank(message = "id 不能为空")
    @Schema(description = "ID")
    private String id;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

}
