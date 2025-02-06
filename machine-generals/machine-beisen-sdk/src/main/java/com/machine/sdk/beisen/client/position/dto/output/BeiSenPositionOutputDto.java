package com.machine.sdk.beisen.client.position.dto.output;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 职位
 */
@Data
public class BeiSenPositionOutputDto {
    /**
     * 名称
     */
    private String name;

    /**
     * 名称_繁体
     */
    private String name_zh_TW;

    /**
     * 名称_英文
     */
    private String name_en_US;

    /**
     * 编码
     */
    private String code;

    /**
     * 业务OId（非主键）
     */
    private String oId;

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
     * 对应职务实体对象（实体编码：TenantBase.JobPost）业务OId（非主键）
     */
    private String oIdJobPost;

    /**
     * 职等（实体编码：TenantBase.JobGrade）OId字段的值，必须为空或GUID
     */
    private String oIdJobGrade;

    /**
     * 职级类别实体对象（实体编码：TenantBase.JobLevelType）业务OId（非主键）
     */
    private String oIdJobLevelType;

    /**
     * 专业条线实体对象（实体编码：TenantBase.ProfessionalLine）业务OId（非主键）
     */
    private String oIdProfessionalLine;

    /**
     * 职务序列实体对象（实体编码：TenantBase.JobSequence）业务OId（非主键）
     */
    private String oIdJobSequence;

    /**
     * 所属组织实体对象（实体编码：TenantBase.Organization）业务OId（非主键）
     */
    private Integer oIdOrganization;

    /**
     * 最低职级，职级实体对象（实体编码：TenantBase.JobLevel）业务OId（非主键）
     */
    private String oIdJobLevel;

    /**
     * 最高职级，职级实体对象（实体编码：TenantBase.JobLevel）业务OId（非主键）
     */
    private String highestOIdJobLevel;

    /**
     * 业务修改人：只查询。系统更新数据时，该字段值不会修改。
     */
    private Integer businessModifiedBy;

    /**
     * 业务修改时间：只查询。系统更新数据时，该字段值不会修改。
     */
    private Date businessModifiedTime;

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
     * 所在地点字典键，示例："1100"（北京市），查看可用选项
     */
    private String place;

    /**
     * 关键职位，示例："1"（是），查看可用选项
     */
    private String positionKey;

    /**
     * 涉密职位，示例："1"（是），查看可用选项
     */
    private String positionSecret;

    /**
     * 排序编码
     */
    private Integer order;

    /**
     * 评估总分
     */
    private BigDecimal score;

    /**
     * 直线维度顺序号
     */
    private Integer orderAdmin;

    /**
     * 虚线维度顺序号
     */
    private Integer orderReserve2;

    /**
     * 直线上级，职位实体对象（实体编码：TenantBase.Position）业务OId（非主键）
     */
    private String pOIdPositionAdmin;

    /**
     * 虚线上级，职位实体对象（实体编码：TenantBase.Position）业务OId（非主键）
     */
    private String pOIdPositionReserve2;

    /**
     * 任职要求
     */
    private String jobRequirements;

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
