package com.machine.app.admin.data.brand.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataBrandExpandListResponseVo  extends TreeNode<DataBrandExpandListResponseVo> {

    @Schema(description = "是否有子节点")
    private Boolean hasChildren;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "LOGO 附件Id")
    private String logoAttachmentId;

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


