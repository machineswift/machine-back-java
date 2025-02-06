package com.machine.app.openapi.data.shop.controller.vo.request;

import com.machine.sdk.common.envm.data.shop.ShopStatusEnum;
import com.machine.sdk.common.envm.data.shop.ShopTypeEnum;
import com.machine.sdk.common.envm.iam.BusinessDistrictTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema
@NoArgsConstructor
public class OpenApiShopCreateRequestVo {

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

    @NotNull(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    @NotNull(message = "编码不能为空")
    @Schema(description = "编码")
    private String code;

    @NotNull(message = "类型不能为空")
    @Schema(description = "类型(ShopTypeEnum)")
    private ShopTypeEnum type;

    @Schema(description = "面积")
    private BigDecimal squareMeters;

    @Schema(description = "初始投资额")
    private BigDecimal initialInvestment;

    @Schema(description = "开店日期")
    private Long openingDate;

    @Schema(description = "经度")
    private BigDecimal latitude;

    @Schema(description = "纬度")
    private BigDecimal longitude;

    @NotNull(message = "商圈类型不能为空")
    @Schema(description = "商圈类型(BusinessDistrictTypeEnum)")
    private BusinessDistrictTypeEnum businessDistrictType;

    @Schema(description = "描述")
    private String description;

}
