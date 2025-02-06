package com.machine.client.data.shop.dto.output;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.iam.BusinessDistrictTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DataShopListOutputDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "类型")
    private ShopTypeEnum type;

    @Schema(description = "状态")
    private ShopStatusEnum status;

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

    @Schema(description = "商圈类型")
    private BusinessDistrictTypeEnum businessDistrictType;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "更新时间")
    private Long updateTime;
}
