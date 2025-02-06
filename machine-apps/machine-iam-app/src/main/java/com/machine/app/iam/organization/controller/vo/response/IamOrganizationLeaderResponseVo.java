
package com.machine.app.iam.organization.controller.vo.response;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class IamOrganizationLeaderResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "系统账号(用户名)")
    private String userName;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "名称")
    private String name;

    @Schema(description = " 手机号")
    private String phone;
}
