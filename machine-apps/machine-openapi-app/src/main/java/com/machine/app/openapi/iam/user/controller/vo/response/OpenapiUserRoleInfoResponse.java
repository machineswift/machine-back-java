package com.machine.app.openapi.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.iam.role.IamRoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class OpenapiUserRoleInfoResponse {

    @Schema(description = "角色ID")
    private String id;

    @Schema(description = "角色类型（RoleTypeEnum）")
    private IamRoleTypeEnum type;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "门店集合")
    private List<BusinessInfo> shopList;

    @Data
    @Schema
    public static class BusinessInfo {

        @Schema(description = "id")
        private String id;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "排序")
        private Long sort;
    }

}
