package com.machine.sdk.self.domain.data.shop;

import com.machine.sdk.common.envm.data.shop.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.intellij.lang.annotations.JdkConstants;

@Data
@Schema
@NoArgsConstructor
public class DataShopDto {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "经营状态（DataShopBusinessStatusEnum）")
    private DataShopBusinessStatusEnum businessStatus;

    @Schema(description = "运营状态（DataShopOperationStatusEnum）")
    private DataShopOperationStatusEnum operationStatus;

    @Schema(description = "物理状态（DataShopPhysicalStatusEnum）")
    private DataShopPhysicalStatusEnum physicalStatus;

    public DataShopDto(String id, DataShopBusinessStatusEnum businessStatus) {
        this.id = id;
        this.businessStatus = businessStatus;
    }

    public DataShopDto(String id, DataShopOperationStatusEnum operationStatus) {
        this.id = id;
        this.operationStatus = operationStatus;
    }

    public DataShopDto(String id, DataShopPhysicalStatusEnum physicalStatus) {
        this.id = id;
        this.physicalStatus = physicalStatus;
    }
}
