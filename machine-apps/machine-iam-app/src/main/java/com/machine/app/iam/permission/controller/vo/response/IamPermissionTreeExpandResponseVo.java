package com.machine.app.iam.permission.controller.vo.response;

import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionMetaDto;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class IamPermissionTreeExpandResponseVo extends TreeNode<IamPermissionTreeExpandResponseVo> {

    @Schema(description = "资源类型（IamPermissionResourceTypeEnum）")
    private IamPermissionResourceTypeEnum resourceType;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Long sort;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "数据权限元数据（只有菜单才有值）")
    private List<DataPermissionMetaDto> dataPermissionMetaList;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
