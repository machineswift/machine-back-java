package com.machine.client.iam.biam.log.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamUserAccessLogDeleteInputDto {

    @NotNull(message = "删除时间节点不能为空")
    @Schema(description = "删除该时间（Unix时间戳）之前的访问日志，用于日志保留与清理")
    private Long beforeCreateTime;

}
