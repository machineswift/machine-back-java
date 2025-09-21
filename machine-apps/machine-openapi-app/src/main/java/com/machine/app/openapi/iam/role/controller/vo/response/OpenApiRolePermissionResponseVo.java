package com.machine.app.openapi.iam.role.controller.vo.response;

import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class OpenApiRolePermissionResponseVo {

    @Schema(description = "权限Id列表")
    private Set<String> permissionIdSet;

    @Schema(description = "数据权限(只有菜单有)")
    private Map<String, List<DataPermissionRuleDto>> dataPermissionRuleMap;
}
