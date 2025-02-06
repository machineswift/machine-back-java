package com.machine.app.iam.permission.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamPermissionQueryAppListRequestVo {

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

}
