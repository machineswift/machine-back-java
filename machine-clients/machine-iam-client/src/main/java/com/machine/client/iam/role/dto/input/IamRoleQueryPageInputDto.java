package com.machine.client.iam.role.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.model.request.PageRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class IamRoleQueryPageInputDto extends PageRequest {

    @NotNull(message = "类型不能为空")
    private RoleTypeEnum type;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态（StatusEnum）
     */
    private StatusEnum status;

    /**
     * 创建人ID集合
     */
    private Set<String> createUserIdSet;

    /**
     * 修改人ID集合
     */
    private Set<String> updateUserIdSet;

    /**
     * 创建开始时间
     */
    private Long updateStartTime;

    /**
     * 创建结束时间
     */
    private Long updateEndTime;

}
