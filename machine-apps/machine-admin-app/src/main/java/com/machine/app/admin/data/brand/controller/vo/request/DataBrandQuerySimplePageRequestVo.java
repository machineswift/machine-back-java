package com.machine.app.admin.data.brand.controller.vo.request;

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
public class DataBrandQuerySimplePageRequestVo extends PageRequest {

    @Schema(description = "父品牌ID")
    private String parentId;

    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;
}


