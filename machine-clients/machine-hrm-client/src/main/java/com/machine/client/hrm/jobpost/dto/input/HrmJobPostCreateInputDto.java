package com.machine.client.hrm.jobpost.dto.input;

import com.machine.sdk.beisen.envm.BeiSenJobPostStatusEnum;
import lombok.Data;

@Data
public class HrmJobPostCreateInputDto {

    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 状态
     */
    private BeiSenJobPostStatusEnum status;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Long sort;

}
