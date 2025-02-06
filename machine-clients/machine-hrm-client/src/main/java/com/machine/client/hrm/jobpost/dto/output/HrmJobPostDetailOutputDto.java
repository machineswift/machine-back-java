package com.machine.client.hrm.jobpost.dto.output;

import com.machine.sdk.beisen.envm.BeiSenJobPostStatusEnum;
import lombok.Data;

@Data
public class HrmJobPostDetailOutputDto {

    /**
     * ID
     */
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
     * 排序，数值越大越排在前面
     */
    private long sort;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private long updateTime;

}
