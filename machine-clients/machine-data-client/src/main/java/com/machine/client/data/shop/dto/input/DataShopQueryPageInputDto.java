package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
public class DataShopQueryPageInputDto extends PageRequest {

    @Schema(description = "关键字（名称/编码）")
    private String keyword;

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

    @Schema(description = "门店ID集合")
    private Set<String> shopIdSet;
}
