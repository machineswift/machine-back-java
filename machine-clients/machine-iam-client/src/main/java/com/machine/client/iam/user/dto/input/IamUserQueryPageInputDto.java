package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.base.GenderEnum;
import com.machine.sdk.common.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IamUserQueryPageInputDto extends PageRequest {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别(GenderEnum)")
    private GenderEnum gender;

    @Schema(description = "用户ID集合")
    private Set<String> userIdSet;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

}
