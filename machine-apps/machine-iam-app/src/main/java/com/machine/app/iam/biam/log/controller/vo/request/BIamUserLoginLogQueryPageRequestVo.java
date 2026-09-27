package com.machine.app.iam.biam.log.controller.vo.request;

import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthMethodEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BIamUserLoginLogQueryPageRequestVo extends PageRequest {

    @Schema(description = "用户id集合")
    private Set<String> userIdSet;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "IP地址")
    private String ipAddress;

    @Schema(description = "姓名（模糊）")
    private String realName;

    @Schema(description = "操作(IamAuthActionEnum)")
    private BIamAuthActionEnum authAction;

    @Schema(description = "登录方式(IamAuthMethodEnum)")
    private BIamAuthMethodEnum authMethod;

    @Schema(description = "认证结果(IamAuthResultEnum)")
    private BIamAuthResultEnum authResult;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

}
