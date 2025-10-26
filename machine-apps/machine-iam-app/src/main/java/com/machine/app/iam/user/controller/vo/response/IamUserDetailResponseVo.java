
package com.machine.app.iam.user.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.common.GenderEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Schema
@NoArgsConstructor
public class IamUserDetailResponseVo {

    @Schema(description = "ID")
    private String id;
    
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

    @Schema(description = "组织ID集合")
    private Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap;

    @Schema(description = "用户角色信息")
    private List<IamUserRoleInfoResponse> userRoleInfoList;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
