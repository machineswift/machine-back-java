package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.iam.BusinessDistrictTypeEnum;
import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class ShopExpandListResponseVo {

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

    @Schema(description = "绑定组织集合")
    private List<OrganizationDto> bindOrganizationList;

    @Schema(description = " 门店标签选项集合")
    private List<ShopDetailResponseVo.LabelOption> labelOptionList;

    @Schema(description = "地址信息")
    private AddressInfoDto addressInfo;

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

    @Data
    @Schema
    @NoArgsConstructor
    public static class OrganizationDto {

        @Schema(description = "ID")
        private String id;

        @Schema(description = "父ID")
        private String parentId;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "组织类型（OrganizationTypeEnum）")
        private OrganizationTypeEnum type;

    }
}
