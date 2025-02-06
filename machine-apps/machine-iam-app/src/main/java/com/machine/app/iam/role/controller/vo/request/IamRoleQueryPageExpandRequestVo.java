package com.machine.app.iam.role.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class IamRoleQueryPageExpandRequestVo extends PageRequest {

    @NotNull(message = "类型不能为空")
    @Schema(description = "类型（RoleTypeEnum）", requiredMode = Schema.RequiredMode.REQUIRED)
    private RoleTypeEnum type;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

}
