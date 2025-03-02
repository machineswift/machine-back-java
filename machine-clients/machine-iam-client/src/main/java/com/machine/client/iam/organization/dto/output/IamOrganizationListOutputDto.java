package com.machine.client.iam.organization.dto.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IamOrganizationListOutputDto {
    private String id;

    private String parentId;

    private String code;

    private String name;

    /**
     * 排序
     */
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
