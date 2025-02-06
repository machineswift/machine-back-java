package com.machine.app.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.iam.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class ShopUserSimpleListResponseVo {

    @Schema(description = "区域经理/督导")
    private List<ShopUserInfo> areaManagementList;

    @Schema(description = "门店员工")
    private List<ShopUserInfo> storeManagerList;


    @Data
    @Schema
    public static class ShopUserInfo {

        @Schema(description = "ID")
        private String id;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "手机号")
        private String phone;

        @Schema(description = "类型（UserTypeEnum）")
        private List<UserTypeEnum> userTypeList;

        @Schema(description = "用户角色信息")
        private List<IamUserRoleInfoResponse> userRoleList;
    }
}
