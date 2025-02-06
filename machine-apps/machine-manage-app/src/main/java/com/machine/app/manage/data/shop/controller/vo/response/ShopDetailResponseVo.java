package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.iam.BusinessDistrictTypeEnum;
import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema
public class ShopDetailResponseVo {

    @Schema(description = "门店ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "类型(ShopTypeEnum)")
    private ShopTypeEnum type;

    @Schema(description = "状态（ShopStatusEnum）")
    private ShopStatusEnum status;

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

    @Schema(description = "加盟商ID")
    private String franchiseeId;

    @Schema(description = "商圈类型(BusinessDistrictTypeEnum)")
    private BusinessDistrictTypeEnum businessDistrictType;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "地址信息")
    private AddressInfoDto addressInfo;

    @Schema(description = " 门店标签选项集合")
    private List<LabelOption> labelOptionList;

    @Schema(description = "门店关联组织集合")
    private List<Organization> organizationList;

    @Schema(description = "门店营业执照")
    private ShopBusinessLicenseResponseVo businessLicense;

    @Schema(description = "食品经营许可证")
    private ShopFoodBusinessLicenseResponseVo foodBusinessLicense;

    @Schema(description = "消杀合同")
    private ShopDisinfectingContractResponseVo disinfectingContract;

    @Schema(description = "门头照")
    private ShopFrontPhotoResponseVo frontPhoto;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;


    @Data
    @Schema
    public static class Organization {

        @Schema(description = "组织ID")
        private String id;

        @Schema(description = " 组织类型(OrganizationTypeEnum)")
        private OrganizationTypeEnum type;

        @Schema(description = "排序")
        private Long sort;
    }

    @Data
    @Schema
    public static class LabelOption {

        @Schema(description = "id")
        private String id;

        @Schema(description = "人工标签ID")
        private String labelId;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "名称")
        private String name;
    }

}
