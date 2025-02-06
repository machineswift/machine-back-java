package com.machine.app.iam.user.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class IamUserQueryPageSimpleRequestVo extends PageRequest {

    @Schema(description = "部门ID集合")
    private Set<String> departmentIdSet;

    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @Schema(description = "角色ID集合")
    private Set<String> roleIdSet;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "类型（UserTypeEnum）")
    private List<UserTypeEnum> userTypeList;

}
