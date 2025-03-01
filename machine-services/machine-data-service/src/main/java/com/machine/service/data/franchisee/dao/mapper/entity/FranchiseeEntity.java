package com.machine.service.data.franchisee.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.CertificateTypeEnum;
import com.machine.sdk.common.envm.data.EducationalQualificationEnum;
import com.machine.sdk.common.envm.data.FranchiseeEntityTypeEnum;
import com.machine.sdk.common.envm.data.WorkExperienceTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_franchisee")
public class FranchiseeEntity extends BaseEntity {
    /**
     * 用户id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 加盟商名称
     */
    @TableField("name")
    private String name;

    /**
     * 加盟商编码
     */
    @TableField("code")
    private String code;

    /**
     * 主体类型
     */
    @TableField("entity_type")
    private FranchiseeEntityTypeEnum entityType;

    /**
     * 证件类型（CertificateTypeEnum）
     */
    @TableField("certificate_type")
    private CertificateTypeEnum certificateType;

    /**
     * 证件号码
     */
    @TableField("certificate_number")
    private String certificateNumber;

    /**
     * 学历
     */
    @TableField("educational_qualification")
    private EducationalQualificationEnum educationalQualification;

    /**
     * 工作经验类型
     */
    @TableField("work_experience_type")
    private WorkExperienceTypeEnum workExperienceType;

    /**
     * 工作经验值
     */
    @TableField("work_experience_value")
    private String workExperienceValue;

    /**
     * 个人资产
     */
    @TableField("personal_assets")
    private BigDecimal personalAssets;

    /**
     * 加盟日期
     */
    @TableField("franchise_date")
    private Long franchiseDate;

    /**
     * 一级进线渠道
     */
    @TableField("incoming_channel_first")
    private String incomingChannelFirst;

    /**
     * 二级进线渠道
     */
    @TableField("incoming_channel_second")
    private String incomingChannelSecond;

    /**
     * 三级进线渠道
     */
    @TableField("incoming_channel_third")
    private String incomingChannelThird;

    /**
     * 描述
     */
    @TableField("description")
    private String description;
}