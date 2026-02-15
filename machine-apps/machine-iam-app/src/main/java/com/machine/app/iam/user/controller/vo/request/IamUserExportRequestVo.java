package com.machine.app.iam.user.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.base.GenderEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamUserExportRequestVo {

    @Schema(description = "ID集合")
    private Set<String> userIdSet;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别(GenderEnum)")
    private GenderEnum gender;

    @Schema(description = "门店ID集合")
    private Set<String> shopIdSet;

    @Schema(description = "部门ID集合")
    private Set<String> departmentIdSet;

    @Schema(description = "组织类型")
    private IamOrganizationTypeEnum organizationType;

    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @Schema(description = "角色ID集合")
    private Set<String> roleIdSet;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

}
