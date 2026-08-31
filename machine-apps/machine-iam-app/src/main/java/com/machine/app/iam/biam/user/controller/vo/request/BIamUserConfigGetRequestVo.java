package com.machine.app.iam.biam.user.controller.vo.request;

import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamUserConfigGetRequestVo {

    @NotNull(message = "配置键不能为空")
    @Schema(description = "配置键", requiredMode = Schema.RequiredMode.REQUIRED)
    private BIamUserConfigKeyEnum configKey;

}
