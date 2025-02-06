package com.machine.app.manage.data.shop.controller.vo.request;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ShopQueryPageSimpleRequestVo extends PageRequest {

    @Schema(description = "名称")
    private String name;

    @Schema(description = "类型集合(ShopTypeEnum)")
    private Set<ShopTypeEnum> typeSet;

    @Schema(description = "状态集合（ShopStatusEnum）")
    private Set<ShopStatusEnum> statusSet;

    @Schema(description = "省编码")
    public Set<String> provinceCodeSet;

    @Schema(description = "市编码")
    public Set<String> cityCodeSet;

    @Schema(description = "区编码集合")
    public Set<String> areaCodeSet;

    @Schema(description = "编码集合")
    private Set<String> codeSet;

    @Schema(description = "组织ID集合")
    private Set<String> organizationIdSet;

    @Schema(description = "标签选项ID集合")
    private Set<String> labelOptionIdSet;

}
