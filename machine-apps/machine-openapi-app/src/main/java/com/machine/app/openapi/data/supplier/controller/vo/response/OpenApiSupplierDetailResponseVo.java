package com.machine.app.openapi.data.supplier.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema
public class OpenApiSupplierDetailResponseVo {

    @Schema(description = "供应商ID")
    private String id;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "归属公司Id集合")
    private List<String> companyIdList;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
