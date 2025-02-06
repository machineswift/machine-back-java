package com.machine.client.hrm.jobpost.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import lombok.Data;

@Data
public class HrmJobPostListSimpleOutputDto {

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
    private StatusEnum status;
    
}
