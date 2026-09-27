package com.machine.client.iam.biam.log.dto.input;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.envm.base.audit.ActionTypeEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BIamOperationLogQueryPageInputDto extends PageRequest {

    @Schema(description = "操作人用户ID集合")
    private Set<String> userIdSet;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "姓名（模糊）")
    private String realName;

    @Schema(description = "操作来源")
    private OperateSourceEnum operateSource;

    @Schema(description = "操作模块")
    private ModuleEnum module;

    @Schema(description = "操作模块实体")
    private ModuleEntityEnum moduleEntity;

    @Schema(description = "操作模块实体主键ID")
    private String moduleEntityId;

    @Schema(description = "操作分类")
    private ActionTypeEnum operateType;

    @Schema(description = "操作名称（模糊）")
    private String operateName;

    @Schema(description = "业务状态")
    private ActionStatusEnum actionStatus;

    @Schema(description = "HTTP状态码")
    private Integer httpStatus;

    @Schema(description = "请求路径")
    private String requestPath;

    @Schema(description = "客户端IP")
    private String clientIp;

    @Schema(description = "链路追踪ID")
    private String traceId;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;
}
