package com.machine.client.hrm.jobpost.dto.input;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HrmJobPostListSimpleInputDto extends PageRequest {

    /**
     * 状态（StatusEnum）
     */
    private StatusEnum status;

    /**
     * 名称不能为空
     */
    private String name;
    
}
