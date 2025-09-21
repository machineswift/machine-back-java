package com.machine.client.iam.user.dto.output;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IamUserOrganizationRelationOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户Id")
    private String userId;

    @Schema(description = "组织Id")
    private String organizationId;

    @Schema(description = "组织类型（IamOrganizationTypeEnum）")
    private IamOrganizationTypeEnum organizationType;

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