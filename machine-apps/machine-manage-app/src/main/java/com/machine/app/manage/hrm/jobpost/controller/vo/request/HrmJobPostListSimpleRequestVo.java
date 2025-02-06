package com.machine.app.manage.hrm.jobpost.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class HrmJobPostListSimpleRequestVo extends PageRequest {

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

}
