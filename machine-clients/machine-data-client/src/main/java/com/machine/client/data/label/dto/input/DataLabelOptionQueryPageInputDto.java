package com.machine.client.data.label.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.request.PageRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataLabelOptionQueryPageInputDto extends PageRequest {

    /**
     * 人工标签ID
     */
    private String labelId;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private StatusEnum status;

}
