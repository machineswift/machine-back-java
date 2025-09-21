package com.machine.app.iam.organization.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.DataShopBusinessStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopOperationStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopPhysicalStatusEnum;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class IamOrganizationWithShopTreeResponseVo extends TreeNode<IamOrganizationWithShopTreeResponseVo> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "组织机构中的组织数")
    private Integer organizationNumber;

    @Schema(description = "组织机构中的门店数")
    private Integer shopNumber;

    @Schema(description = "绑定的门店集合")
    private List<ShopDto> bindShopList;

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

    @Data
    @Schema
    @NoArgsConstructor
    public static class ShopDto {

        @Schema(description = "门店ID")
        private String id;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "经营状态（DataShopBusinessStatusEnum）")
        private DataShopBusinessStatusEnum businessStatus;

        @Schema(description = "运营状态（DataShopOperationStatusEnum）")
        private DataShopOperationStatusEnum operationStatus;

        @Schema(description = "物理状态（DataShopPhysicalStatusEnum）")
        private DataShopPhysicalStatusEnum physicalStatus;
    }

}
