package com.machine.client.iam.identity.dto.output;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamOAuth2RegisteredClientListOutputDto {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Schema(description = "更新时间")
    private Long updateTime;
}
