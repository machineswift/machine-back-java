package com.machine.app.iam.role.controller.vo.response;

import com.machine.sdk.beisen.envm.BeiSenJobPostStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IamJobPostListResponseVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态")
    private BeiSenJobPostStatusEnum status;

}
