package com.machine.sdk.beisen.client.organization.dto.input;

import lombok.Data;

@Data
public class BeiSenOrganizationGetSubInputDto {

    /**
     * 查询指定组织单元OId，元素值必须大于等于0，示例：3879。注意：默认组织OId为0，根组织OId为900+租户ID，如：900100000
     */
    private Integer oId;

    /**
     * 是否包含停用组织，默认false，不包含。
     */
    private Boolean isWithDisable = Boolean.FALSE;

    /**
     * 是否包含指定组织自身，默认false，不包含。
     */
    private Boolean isWithSelf = Boolean.TRUE;

    /**
     * 是否包括已删除数据，默认否，示例：true/false
     */
    private Boolean isWithDeleted = Boolean.FALSE;


    /**
     * 查询层级。注意：默认组织查询20层，示例：1
     */
    private Integer level = 20;

}
