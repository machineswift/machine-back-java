package com.machine.client.iam.user.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserListOutputDto {

    private String id;

    /**
     * 用户名（系统账号）
     */
    private String userName;

    private StatusEnum status;

    private String code;

    private String name;

    private String phone;

    private GenderEnum gender;

    /**
     * 描述
     */
    private String description;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
