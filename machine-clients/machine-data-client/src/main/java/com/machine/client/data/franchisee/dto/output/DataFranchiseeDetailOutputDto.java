package com.machine.client.data.franchisee.dto.output;

import com.machine.sdk.common.envm.data.CertificateTypeEnum;
import com.machine.sdk.common.envm.data.EducationalQualificationEnum;
import com.machine.sdk.common.envm.data.FranchiseeEntityTypeEnum;
import com.machine.sdk.common.envm.data.WorkExperienceTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.HealthCertificateDto;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DataFranchiseeDetailOutputDto {

    @Schema(description = "加盟商ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "加盟商编码")
    private String code;

    @Schema(description = "加盟商绑定门店id集合")
    private List<String> bindShopIdList;

    @Schema(description = "主体类型（FranchiseeEntityTypeEnum）")
    private FranchiseeEntityTypeEnum entityType;

    @Schema(description = "证件类型（CertificateTypeEnum）")
    private CertificateTypeEnum certificateType;

    @Schema(description = "证件号码")
    private String certificateNumber;

    @Schema(description = "身份证信息")
    private IdentityCardDto identityCard;

    @Schema(description = "健康证信息")
    private HealthCertificateDto healthCertificate;

    @Schema(description = "学历（EducationalQualificationEnum）")
    private EducationalQualificationEnum educationalQualification;

    @Schema(description = "工作经验类型（WorkExperienceTypeEnum）")
    private WorkExperienceTypeEnum workExperienceType;

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

    @Schema(description = "创建人 ID")
    private String createBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

    @Schema(description = "修改人 ID")
    private String updateBy;

}
