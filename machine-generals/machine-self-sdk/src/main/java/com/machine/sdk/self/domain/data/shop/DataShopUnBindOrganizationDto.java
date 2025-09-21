package com.machine.sdk.self.domain.data.shop;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataShopUnBindOrganizationDto {

    private String id;

    private String organizationId;

    public DataShopUnBindOrganizationDto(String id,
                                         String organizationId) {
        this.id = id;
        this.organizationId = organizationId;
    }
}
