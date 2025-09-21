package com.machine.app.iam.organization.business.bo;

import com.machine.client.data.shop.dto.output.DataShopListOutputDto;
import com.machine.client.data.shop.dto.output.DataShopListSimpleOutputDto;
import com.machine.sdk.common.model.tree.TreeNode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class IamOrganizationExpandTreeBo extends TreeNode<IamOrganizationExpandTreeBo> {

    @Schema(description = "编码")
    private String code;

    @Schema(description = "组织机构中的组织数")
    private int organizationNumber;

    private Set<String> organizationIdSet = new HashSet<>();

    @Schema(description = "组织机构中的门店数")
    private int shopNumber;

    private Set<String> shopIdSet = new HashSet<>();

    @Schema(description = "绑定的门店集合")
    private List<DataShopListSimpleOutputDto> bindShopList;

    @Schema(description = "组织机构中的人数")
    private int userNumber;

    private Set<String> userIdSet = new HashSet<>();

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
