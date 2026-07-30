package com.machine.client.iam.identity.dto.input;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IamOAuth2RegisteredClientPageQueryInputDto extends PageRequest {

    @Schema(description = "客户端ID（模糊查询）")
    private String clientId;

    @Schema(description = "客户端名称（模糊查询）")
    private String clientName;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "创建开始时间")
    private Long updateStartTime;

    @Schema(description = "创建结束时间")
    private Long updateEndTime;

}
