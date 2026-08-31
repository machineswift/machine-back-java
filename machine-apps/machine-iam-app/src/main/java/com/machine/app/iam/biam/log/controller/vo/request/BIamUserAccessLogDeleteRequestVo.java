package com.machine.app.iam.biam.log.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamUserAccessLogDeleteRequestVo {

    @NotNull(message = "删除时间节点不能为空")
    @Schema(description = "删除该时间之前的访问日志")
    private Long beforeCreateTime;

}
