package com.machine.sdk.beisen.client.organization.dto.output;

import lombok.Data;

@Data
public class BeiSenOrganizationInfoByCodesOutputDto {

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
    private String oId;

    /**
     * 部门负责人员工UserID，示例：101500177
     */
    private String personInCharge;

    /**
     * 负责人（副职）,多员工UserID集合，示例："101500177,101500178"
     */
    private String personInChargeDeputy;
}
