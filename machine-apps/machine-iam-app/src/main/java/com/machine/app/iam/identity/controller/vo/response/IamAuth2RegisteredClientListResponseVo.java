package com.machine.app.iam.identity.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class IamAuth2RegisteredClientListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
