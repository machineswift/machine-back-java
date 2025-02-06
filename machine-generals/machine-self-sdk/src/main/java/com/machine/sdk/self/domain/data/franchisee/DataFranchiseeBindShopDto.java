package com.machine.sdk.self.domain.data.franchisee;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataFranchiseeBindShopDto {
    private String id;

    private String shopCode;

    public DataFranchiseeBindShopDto(String id,
                                     String shopCode) {
        this.id = id;
        this.shopCode = shopCode;
    }
}
