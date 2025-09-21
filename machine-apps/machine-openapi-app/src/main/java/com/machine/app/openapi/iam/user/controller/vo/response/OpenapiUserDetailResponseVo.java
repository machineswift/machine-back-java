package com.machine.app.openapi.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class OpenapiUserDetailResponseVo {

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

    @Schema(description = "用户状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "类型（UserTypeEnum）")
    private List<IamUserTypeEnum> userTypeList;

    @Schema(description = "用户角色信息")
    private List<OpenapiUserRoleInfoResponse> userRoleList;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
