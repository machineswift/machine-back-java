package com.machine.app.openapi.iam.user.controller.vo.request;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiUserListSimpleRequestVo {

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "类型（UserTypeEnum）")
    private IamUserTypeEnum userType;

    @Schema(description = "偏移量，不传的话取默认值。下一次请求取当前结果集最后一条数据的ID。")
    private String offset;

    @Min(10)
    @Max(100)
    @NotNull(message = "分页大小不能为空")
    @Schema(description = "支持分页查询，与offset参数同时设置时才生效，此参数代表分页大小，最大100。")
    private Integer size;

}
