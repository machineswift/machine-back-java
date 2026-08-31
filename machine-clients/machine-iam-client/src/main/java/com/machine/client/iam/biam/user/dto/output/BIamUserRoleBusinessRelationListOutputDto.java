package com.machine.client.iam.biam.user.dto.output;

import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BIamUserRoleBusinessRelationListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户角色关系ID")
    private String userRoleRelationId;

    @Schema(description = "业务id")
    private String businessId;

    @Schema(description = "类型（UserRoleBusinessTypeEnum）")
    private BIamUserRoleBusinessTypeEnum businessType;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
