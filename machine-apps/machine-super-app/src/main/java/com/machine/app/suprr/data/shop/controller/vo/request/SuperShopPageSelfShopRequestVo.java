package com.machine.app.suprr.data.shop.controller.vo.request;

import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class SuperShopPageSelfShopRequestVo extends PageRequest {

    @Schema(description = "关键字（名称/编码）")
    private String keyword;

    @Schema(description = "组织类型")
    private IamOrganizationTypeEnum organizationType;

    @Schema(description = "组织Id集合")
    private Set<String> organizationIdSet;

    @Schema(description = "省编码")
    public Set<String> provinceCodeSet;

    @Schema(description = "市编码")
    public Set<String> cityCodeSet;

    @Schema(description = "区编码集合")
    public Set<String> areaCodeSet;

}
