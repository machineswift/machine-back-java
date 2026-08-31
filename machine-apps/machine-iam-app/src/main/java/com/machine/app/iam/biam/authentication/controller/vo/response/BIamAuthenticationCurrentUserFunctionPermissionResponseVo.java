package com.machine.app.iam.biam.authentication.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class BIamAuthenticationCurrentUserFunctionPermissionResponseVo {
    public BIamAuthenticationCurrentUserFunctionPermissionResponseVo(List<String> roleCodeList,
                                                                     List<String> permissionCodeList) {
        this.roleCodeList = roleCodeList;
        this.permissionCodeList = permissionCodeList;
    }

    @Schema(description = "角色编码集合")
    private List<String> roleCodeList;

    @Schema(description = "权限编码集合")
    private List<String> permissionCodeList;
}
