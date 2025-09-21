package com.machine.sdk.beisen.client.jobpost.dto.input;

import lombok.Data;

import java.util.List;

@Data
public class BeiSenJobPostWindowInputDto {

    /**
     * 是否包含停用的记录，默认不包含。
     */
    private Boolean withDisabled = Boolean.FALSE;

    /**
     * 时间范围开始时间 格式：2021-01-01T00:00:00
     */
    private String startTime;

    /**
     * 时间范围结束时间 格式：2021-09-01T00:00:00
     */
    private String stopTime;

    /**
     * 时间窗查询类型，1修改时间、2业务修改时间
     * ModifiedTime:修改时间，系统修改同步更新该时间。示例："1"
     * BusinessModifiedTime:业务修改时间，系统修改不同步更新该时间
     */
    private String timeWindowQueryType = "1";

    /**
     * 本批次的ScrollId，第一次查询为空，后续为上次结果返回的ScrollId。
     */
    private String scrollId;

    /**
     * 每批次查询个数，默认100个
     */
    private Integer capacity = 300;


    /**
     * 是否包括已删除数据，默认否，示例：true/false
     */
    private Boolean isWithDeleted = Boolean.FALSE;


}
