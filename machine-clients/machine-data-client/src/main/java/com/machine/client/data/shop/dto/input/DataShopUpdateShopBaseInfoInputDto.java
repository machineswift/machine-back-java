package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.data.shop.ShopTypeEnum;
import com.machine.sdk.common.envm.iam.BusinessDistrictTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema
public class DataShopUpdateShopBaseInfoInputDto {

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "类型(ShopTypeEnum)")
    private ShopTypeEnum type;

    @Schema(description = "面积")
    private BigDecimal squareMeters;

    @Schema(description = "初始投资额")
    private BigDecimal initialInvestment;

    @Schema(description = "开店日期")
    private Long openingDate;

    @Schema(description = "经度")
    private Long latitude;

    @Schema(description = "纬度")
    private Long longitude;

    @Schema(description = "商圈类型(BusinessDistrictTypeEnum)")
    private BusinessDistrictTypeEnum businessDistrictType;

    @Schema(description = "描述")
    private String description;
}
