package com.machine.sdk.beisen.client.organization.dto.output;

import lombok.Data;

@Data
public class BeiSenOrganizationInfoOutputDto {
    /**
     * 部门机构名称，示例：总裁办
     */
    private String name;

    /**
     * 组织单元编码，示例：BP123
     */
    private String code;

    /**
     * 组织单元OId，示例：123
     */
    private Integer oId;

    /**
     * 状态，示例：1（启用），0停用、1启用
     */
    private Integer status;

    /**
     * 设立日期，示例：2021-01-01T00:00:00
     */
    private String establishDate;

    /**
     * 生效日期，示例：2021-01-01T00:00:00
     */
    private String startDate;

    /**
     * 失效日期，示例：9999-12-31T23:59:59
     */
    private String stopDate;

    /**
     * 变更日期，示例：2021-01-01T00:00:00
     */
    private String changeDate;

    /**
     * 行政维度上级组织OId，示例：123
     */
    private Integer pOIdOrgAdmin;

    /**
     * 行政维度顺序号，示例：1
     */
    private Integer orderAdmin;

    /**
     * 行政维度_层级，示例：2
     */
    private Integer pOIdOrgAdmin_TreeLevel;

    /**
     * 行政维度_路径，示例：900127666/4622894/4622489/4622492/4743877
     */
    private String pOIdOrgAdmin_TreePath;

    /**
     * 创建人员工UserID，示例：115515434
     */
    private Integer createdBy;

    /**
     * 创建时间，示例：2021-01-01T14:00:00
     */
    private String createdTime;

    /**
     * 修改人员工UserID，示例：115515434。系统更新数据时，该字段值会修改。
     */
    private Integer modifiedBy;

    /**
     * 修改时间，示例：2021-01-01T14:00:00。系统更新数据时，该字段值会修改。
     */
    private String modifiedTime;

    /**
     * 是否删除，示例：false
     */
    private Boolean stdIsDeleted;
}
