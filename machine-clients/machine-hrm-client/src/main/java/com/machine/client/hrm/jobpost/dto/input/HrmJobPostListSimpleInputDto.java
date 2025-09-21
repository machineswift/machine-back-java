package com.machine.client.hrm.jobpost.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.request.PageRequest;
import jakarta.validation.constraints.NotBlank;
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
