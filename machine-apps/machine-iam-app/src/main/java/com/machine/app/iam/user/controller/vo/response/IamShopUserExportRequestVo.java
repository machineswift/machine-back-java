package com.machine.app.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamShopUserExportRequestVo {

    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @Schema(description = "角色ID")
    private Set<String> roleIdSet;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "系统账号(用户名)")
    private String userName;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "更新开始时间")
    private Long updateStartTime;

    @Schema(description = "更新结束时间")
    private String updateEndTime;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private String createEndTime;

}
