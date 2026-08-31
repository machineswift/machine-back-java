package com.machine.app.iam.biam.identity.controller.vo.request;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.identity.BIamAuthorizationGrantTypeEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BIamAuth2RegisteredClientPageQueryRequestVo extends PageRequest {

    @Schema(description = "授权方式")
    private BIamAuthorizationGrantTypeEnum authorizationGrantType;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端名称（模糊查询）")
    private String clientName;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;

}
