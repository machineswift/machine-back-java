
package com.machine.app.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.iam.auth.AuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.AuthResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class IamUserLoginLogPageResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "系统账号(用户名)")
    private String userName;

    @Schema(description = "操作(AuthActionEnum)")
    private AuthActionEnum authAction;

    @Schema(description = "登录方式(AuthMethodEnum)")
    private AuthMethodEnum authMethod;

    @Schema(description = "结果(AuthResultEnum)")
    private AuthResultEnum authResult;

    @Schema(description = "编码（工号）")
    private String code;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "IP 地址")
    private String ipAddress;

    @Schema(description = "平台")
    private String  platform;

    @Schema(description = "浏览器和操作系统等信息")
    private String userAgent;

    @Schema(description = "失败原因")
    private String failReason;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
