package com.machine.app.iam.userbk.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@Data
@Schema
@NoArgsConstructor
public class IamUserRoleUpdateRequestVo {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "门店ID集合")
    private LinkedHashSet<String> shopIdSet;
}
