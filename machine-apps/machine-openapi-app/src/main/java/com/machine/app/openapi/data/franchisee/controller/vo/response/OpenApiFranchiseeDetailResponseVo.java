package com.machine.app.openapi.data.franchisee.controller.vo.response;

import com.machine.sdk.common.envm.DataCertificateTypeEnum;
import com.machine.sdk.common.envm.data.DataEducationalQualificationEnum;
import com.machine.sdk.common.envm.data.DataFranchiseeEntityTypeEnum;
import com.machine.sdk.common.envm.data.DataWorkExperienceTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OpenApiFranchiseeDetailResponseVo {

    @Schema(description = "加盟商ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "加盟商编码（不是用户编码）")
    private String code;

    @Schema(description = "加盟商绑定门店id集合")
    private List<String> bindShopIdList;

    @Schema(description = "主体类型（FranchiseeEntityTypeEnum）")
    private DataFranchiseeEntityTypeEnum entityType;

    @Schema(description = "证件类型（CertificateTypeEnum）")
    private DataCertificateTypeEnum certificateType;

    @Schema(description = "证件号码")
    private String certificateNumber;

    @Schema(description = "学历（EducationalQualificationEnum）")
    private DataEducationalQualificationEnum educationalQualification;

    @Schema(description = "工作经验类型（WorkExperienceTypeEnum）")
    private DataWorkExperienceTypeEnum workExperienceType;

    @Schema(description = "工作经验值")
    private String workExperienceValue;

    @Schema(description = "个人资产")
    private BigDecimal personalAssets;

    @Schema(description = "加盟日期")
    private Long franchiseDate;

    @Schema(description = "一级进线渠道")
    private String incomingChannelFirst;

    @Schema(description = "二级进线渠道")
    private String incomingChannelSecond;

    @Schema(description = "三级进线渠道")
    private String incomingChannelThird;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
