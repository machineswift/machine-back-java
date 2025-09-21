package com.machine.app.iam.userbk.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class IamShopUserQueryPageExpandRequestVo extends PageRequest {

    @Schema(description = "组织类型")
    private IamOrganizationTypeEnum organizationType;

    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @Schema(description = "角色ID")
    private Set<String> roleIdSet;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "系统账号(用户名)")
    private String username;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "更新开始时间")
    private Long updateStartTime;

    @Schema(description = "更新结束时间")
    private Long updateEndTime;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

}
