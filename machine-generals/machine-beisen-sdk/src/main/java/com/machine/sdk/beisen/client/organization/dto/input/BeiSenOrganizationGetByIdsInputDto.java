package com.machine.sdk.beisen.client.organization.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class BeiSenOrganizationGetByIdsInputDto {

    /**
     * 业务OId集合，示例：[116347665,116347666,116347667]。正整数，必填，元素个数小于等于300个
     */
    private List<Integer> oIds;

    /**
     * 是否包括已删除数据，默认否，示例：true/false
     */
    private Boolean isWithDeleted = Boolean.FALSE;
}
