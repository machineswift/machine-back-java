package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;

@Data
@NoArgsConstructor
public class IamUserRoleInfoUpdateInputDto {

    @Schema(description = "角色ID")
    private String roleId;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "门店ID集合")
    private LinkedHashSet<String> shopIdSet;
}
