package com.machine.app.openapi.data.franchisee.controller.vo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OpenApiFranchiseeBindShopRequestVo {

    @NotNull(message = "加盟商ID不能为空")
    @Schema(description = "加盟商ID")
    private String id;

    @NotNull(message = "门店编码不能为空")
    @Schema(description = "门店编码")
    private String shopCode;

}
