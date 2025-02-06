package com.machine.app.iam.user.controller.vo.request;

import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class IamUserLoginLogQueryPageRequestVo extends PageRequest {

    @Schema(description = "用户id集合")
    private Set<String> userIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private String createEndTime;

}
