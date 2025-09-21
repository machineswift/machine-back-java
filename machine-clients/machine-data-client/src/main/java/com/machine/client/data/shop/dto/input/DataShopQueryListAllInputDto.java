package com.machine.client.data.shop.dto.input;

import com.machine.sdk.common.envm.data.shop.DataShopBusinessStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopOperationStatusEnum;
import com.machine.sdk.common.envm.data.shop.DataShopPhysicalStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class DataShopQueryListAllInputDto {

    @Schema(description = "经营状态（DataShopBusinessStatusEnum）")
    private DataShopBusinessStatusEnum businessStatus;

    @Schema(description = "运营状态（DataShopOperationStatusEnum）")
    private DataShopOperationStatusEnum operationStatus;

    @Schema(description = "物理状态（DataShopPhysicalStatusEnum）")
    private DataShopPhysicalStatusEnum physicalStatus;

}
