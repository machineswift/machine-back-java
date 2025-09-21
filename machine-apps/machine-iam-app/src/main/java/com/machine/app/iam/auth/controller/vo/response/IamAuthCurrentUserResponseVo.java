package com.machine.app.iam.auth.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IamAuthCurrentUserResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "系统账号(用户名)")
    private String username;

    @Schema(description = "编码（工号）")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "手机号")
    private String phone;
}
