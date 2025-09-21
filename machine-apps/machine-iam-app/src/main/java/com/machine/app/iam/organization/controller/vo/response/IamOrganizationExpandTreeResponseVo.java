package com.machine.app.iam.organization.controller.vo.response;

import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class IamOrganizationExpandTreeResponseVo extends TreeNode<IamOrganizationExpandTreeResponseVo> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "组织机构中的组织数")
    private Integer organizationNumber;

    @Schema(description = "组织机构中的门店数")
    private Integer shopNumber;

    @Schema(description = "组织机构中的人数")
    private Integer userNumber;

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
