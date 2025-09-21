package com.machine.app.manage.data.shop.controller.vo.response;

import com.machine.sdk.common.envm.data.shop.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopSimpleListResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "经营状态（DataShopBusinessStatusEnum）")
    private DataShopBusinessStatusEnum businessStatus;

    @Schema(description = "运营状态（DataShopOperationStatusEnum）")
    private DataShopOperationStatusEnum operationStatus;

    @Schema(description = "物理状态（DataShopPhysicalStatusEnum）")
    private DataShopPhysicalStatusEnum physicalStatus;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "更新时间")
    private Long updateTime;
}
