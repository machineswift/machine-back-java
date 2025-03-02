package com.machine.client.iam.role.dto.output;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class IamRoleDetailOutputDto {

    private String id;

    private String parentId;

    /**
     * 类型
     * {@link RoleTypeEnum}
     */
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
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Long sort;

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;

}
