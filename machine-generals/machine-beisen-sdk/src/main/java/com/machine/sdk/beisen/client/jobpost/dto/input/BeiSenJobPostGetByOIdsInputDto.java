package com.machine.sdk.beisen.client.jobpost.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class BeiSenJobPostGetByOIdsInputDto {
    /**
     * 职位数据OId集合。
     */
    private List<String> oIds;

    /**
     * 是否包括已删除数据，默认否，示例：true/false。
     */
    private Boolean withDisabled = Boolean.FALSE;


}
