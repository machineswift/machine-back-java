package com.machine.sdk.self.domain.data.franchisee;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DataFranchiseeUnbindShopDto {
    private String id;

    private String shopId;

    public DataFranchiseeUnbindShopDto(String id,
                                       String shopId) {
        this.id = id;
        this.shopId = shopId;
    }
}
