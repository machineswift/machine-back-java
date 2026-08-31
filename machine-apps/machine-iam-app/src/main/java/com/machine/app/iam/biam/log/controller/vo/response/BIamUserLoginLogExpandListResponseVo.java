
package com.machine.app.iam.biam.log.controller.vo.response;

import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthMethodEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamUserLoginLogExpandListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "系统账号(用户名)")
    private String username;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "操作(IamAuthActionEnum)")
    private BIamAuthActionEnum authAction;

    @Schema(description = "登录方式(IamAuthMethodEnum)")
    private BIamAuthMethodEnum authMethod;

    @Schema(description = "结果(IamAuthResultEnum)")
    private BIamAuthResultEnum authResult;

    @Schema(description = "IP 地址")
    private String ipAddress;

    @Schema(description = "平台")
    private String  platform;

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
