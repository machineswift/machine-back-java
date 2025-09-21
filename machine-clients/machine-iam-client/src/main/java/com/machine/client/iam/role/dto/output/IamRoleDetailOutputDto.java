package com.machine.client.iam.role.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.IamRoleTypeEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IamRoleDetailOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "父ID")
    private String parentId;

    @Schema(description = "类型（IamRoleTypeEnum）")
    private IamRoleTypeEnum type;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "数据权限")
    private DataPermissionRuleDto dataPermissionRule;

    @Schema(description = "描述")
    private String description;

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
