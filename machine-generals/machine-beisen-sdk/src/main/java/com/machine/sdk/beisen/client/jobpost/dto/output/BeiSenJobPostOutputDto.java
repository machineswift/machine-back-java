package com.machine.sdk.beisen.client.jobpost.dto.output;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 职务
 */
@Data
public class BeiSenJobPostOutputDto {

    /**
     * 名称
     */
    private String name;

    /**
     * 业务OId（非主键）
     */
    private String oId;

    /**
     * 编码
     */
    private String code;

    /**
     * 设立日期
     */
    private Date establishDate;

    /**
     * 生效日期
     */
    private Date startDate;

    /**
     * 失效日期
     */
    private Date stopDate;

    /**
     * 状态，示例：1（启用），查看可用选项
     */
    private Integer status;

    /**
     * 对应所属资源集合实体对象（实体编码：TenantBase.ResourceSet）业务OId（非主键）
     */
    private String oIdResourceSet;

    /**
     * 最低职等，职等实体对象（实体编码：TenantBase.JobGrade）业务OId（非主键）
     */
    private String oIdJobGradeLow;

    /**
     * 最高职等，职等实体对象（实体编码：TenantBase.JobGrade）业务OId（非主键）
     */
    private String oIdJobGradeHigh;

    /**
     * 专业条线实体对象（实体编码：TenantBase.ProfessionalLine）业务OId（非主键）
     */
    private String oIdProfessionalLine;

    /**
     * 职务序列实体对象（实体编码：TenantBase.JobSequence）业务OId（非主键）
     */
    private String oIdJobSequence;

    /**
     * 最低职级，职级实体对象（实体编码：TenantBase.JobLevel）业务OId（非主键）
     */
    private String oIdJobLevel;

    /**
     * 最高职级，职级实体对象（实体编码：TenantBase.JobLevel）业务OId（非主键）
     */
    private String highestOIdJobLevel;

    /**
     * 职级类别实体对象（实体编码：TenantBase.JobLevelType）业务OId（非主键）
     */
    private String oIdJobLevelType;

    /**
     * 名称_繁体
     */
    private String name_zh_TW;

    /**
     * 名称_英文
     */
    private String name_en_US;

    /**
     * 职责描述
     */
    private String description;

    /**
     * 职责描述_繁体
     */
    private String description_zh_TW;

    /**
     * 职责描述_英文
     */
    private String description_en_US;

    /**
     * 胜任力模型
     */
    private String oIdTalentCriterion;

    /**
     * 业务修改人：只查询。系统更新数据时，该字段值不会修改。
     */
    private Integer businessModifiedBy;

    /**
     * 业务修改时间：只查询。系统更新数据时，该字段值不会修改。
     */
    private Date businessModifiedTime;

    /**
     * 排序编码
     */
    private Integer order;

    /**
     * 评估总分
     */
    private BigDecimal score;

    /**
     * 关键职务，示例："1"（是），查看可用选项
     */
    private String jobPostKey;

    /**
     * 涉密职务，示例："1"（是），查看可用选项
     */
    private String jobPostSecret;

    /**
     * 任职要求
     */
    private String jobRequirements;

    /**
     * 所属资源集合（集团化）
     */
    private Integer stdSetID;

    /**
     * 业务对象实体主键GUID
     */
    private String objectId;

    /**
     * 创建人员工UserID
     */
    private Integer createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改人员工UserID。系统更新数据时，该字段值会修改。
     */
    private Integer modifiedBy;

    /**
     * 修改时间。系统更新数据时，该字段值会修改。
     */
    private Date modifiedTime;

    /**
     * 是否删除，示例：false
     */
    private Boolean stdIsDeleted;


}
