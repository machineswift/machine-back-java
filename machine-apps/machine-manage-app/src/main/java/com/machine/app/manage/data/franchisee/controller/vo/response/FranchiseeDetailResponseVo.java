package com.machine.app.manage.data.franchisee.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.CrmGenderEnum;
import com.machine.sdk.common.envm.data.DataEducationalQualificationEnum;
import com.machine.sdk.common.envm.data.DataFranchiseeEntityTypeEnum;
import com.machine.sdk.common.envm.data.DataWorkExperienceTypeEnum;
import com.machine.sdk.common.model.dto.data.certificate.IdentityCardDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Schema
@NoArgsConstructor
public class FranchiseeDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "系统账号(用户名)")
    private String username;

    @Schema(description = "编码（工号）")
    private String code;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "性别（GenderEnum）")
    private CrmGenderEnum gender;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "主体类型（FranchiseeEntityTypeEnum）")
    private DataFranchiseeEntityTypeEnum entityType;

    @Schema(description = "学历（EducationalQualificationEnum）")
    private DataEducationalQualificationEnum educationalQualification;

    @Schema(description = "工作经验类型（WorkExperienceTypeEnum）")
    private DataWorkExperienceTypeEnum workExperienceType;

    @Schema(description = "工作经验值")
    private String workExperienceValue;

    @Schema(description = "个人资产")
    private BigDecimal personalAssets;

    @Schema(description = "身份证信息")
    private IdentityCardDto identityCard;

    @Schema(description = "加盟日期（首次加盟合同生效日期）")
    private Long franchiseDate;

    @Schema(description = "一级进线渠道")
    private String incomingChannelFirst;

    @Schema(description = "二级进线渠道")
    private String incomingChannelSecond;

    @Schema(description = "三级进线渠道")
    private String incomingChannelThird;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}
