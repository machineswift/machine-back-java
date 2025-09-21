package com.machine.client.data.franchisee.dto.input;

import com.machine.sdk.common.envm.data.DataEducationalQualificationEnum;
import com.machine.sdk.common.envm.data.DataWorkExperienceTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema
@NoArgsConstructor
public class DataFranchiseeUpdateInputDto {

    @NotNull(message = "加盟商ID不能为空")
    @Schema(description = "加盟商ID")
    private String id;

    @Schema(description = "加盟商名称")
    private String name;

    @Schema(description = "身份证信息")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证信息")
    private HealthCertificateDto healthCertificate;

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
}
