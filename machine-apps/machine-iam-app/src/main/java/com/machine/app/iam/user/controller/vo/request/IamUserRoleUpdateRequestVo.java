package com.machine.app.iam.user.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamUserRoleUpdateRequestVo {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "门店ID集合")
    private Set<String> shopIdSet;
}
