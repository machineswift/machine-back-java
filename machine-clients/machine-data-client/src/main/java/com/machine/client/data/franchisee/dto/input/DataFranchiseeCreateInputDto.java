package com.machine.client.data.franchisee.dto.input;

import com.machine.sdk.common.envm.DataCertificateTypeEnum;
import com.machine.sdk.common.envm.data.DataEducationalQualificationEnum;
import com.machine.sdk.common.envm.data.DataFranchiseeEntityTypeEnum;
import com.machine.sdk.common.envm.data.DataWorkExperienceTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema
@NoArgsConstructor
public class DataFranchiseeCreateInputDto {

    @NotBlank(message = "加盟商手机号不能为空")
    @Schema(description = "加盟商手机号")
    private String phone;

    @NotBlank(message = "加盟商名称不能为空")
    @Schema(description = "加盟商名称")
    private String name;

    @NotBlank(message = "加盟商编码不能为空")
    @Schema(description = "加盟商编码")
    private String code;

    @NotNull(message = "主体类型不能为空")
    @Schema(description = "主体类型（FranchiseeEntityTypeEnum）")
    private DataFranchiseeEntityTypeEnum entityType;

    @NotNull(message = "证件类型不能为空")
    @Schema(description = "证件类型（CertificateTypeEnum）")
    private DataCertificateTypeEnum certificateType;

    @NotBlank(message = "证件号码不能为空")
    @Schema(description = "证件号码")
    private String certificateNumber;

    @Schema(description = "身份证信息")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证信息")
    private HealthCertificateDto healthCertificate;

    @NotNull(message = "学历不能为空")
    @Schema(description = "学历（EducationalQualificationEnum）")
    private DataEducationalQualificationEnum educationalQualification;

    @NotNull(message = "工作经验类型不能为空")
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
