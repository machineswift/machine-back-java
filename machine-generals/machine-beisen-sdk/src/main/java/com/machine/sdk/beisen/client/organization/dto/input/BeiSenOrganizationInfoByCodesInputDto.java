package com.machine.sdk.beisen.client.organization.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class BeiSenOrganizationInfoByCodesInputDto {

    /**
     * 组织Code集合 示例 ：["abc123","dc33"]
     */
    private List<String> codes;

    /**
     * 组织生效时间点 为空时按当前日期查询， 必须大于等于1900-01-01T00:00:00，示例：2020-1-12T00:00:00
     */
    private  String activeDate;

    /**
     * 查询字段列表 默认null表示查询所有字段。注意：该参数仅表示是否查询该列的值，不控制响应模型的字段。示例：["Name","OId","Code"]
     */
    private  List<String> columns;

    /**
     * 是否包括已删除数据，默认否，示例：true/false
     */
    private Boolean isWithDeleted = Boolean.FALSE;

}
