package com.machine.app.openapi.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class OpenApiShopDetailResponseVo {

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

    @Schema(description = "地址信息")
    private AddressInfoDto addressInfo;

    @Schema(description = "经度")
    private Long latitude;

    @Schema(description = "纬度")
    private Long longitude;

    @Schema(description = "描述")
    private String description;

    @Schema(description = " 门店标签选项集合")
    private List<LabelOption> labelOptionList;

    @Schema(description = "门店关联组织集合")
    private List<Organization> organizationList;

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
    public static class Organization {

        @Schema(description = "组织ID")
        private String id;

        @Schema(description = " 组织类型(IamOrganizationTypeEnum)")
        private IamOrganizationTypeEnum type;

        @Schema(description = "排序")
        private Long sort;
    }

    @Data
    @Schema
    public static class LabelOption {

        @Schema(description = "id")
        private String id;

        @Schema(description = "标签ID")
        private String labelId;

        @Schema(description = "编码")
        private String code;

        @Schema(description = "名称")
        private String name;
    }
}
