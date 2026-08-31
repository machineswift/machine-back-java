package com.machine.app.iam.biam.user.controller.vo.response;

import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamUserConfigResponseVo {

    @Schema(description = "配置键")
    private BIamUserConfigKeyEnum configKey;

    @Schema(description = "配置值（JSON字符串）")
    private String configValue;

}
