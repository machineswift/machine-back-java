package com.machine.client.iam.user.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserRoleInfoFranchiseeBindShopInputDto {

    @NotBlank(message = "用户ID不能为空")
    @Schema(description = "用户ID")
    private String userId;

    @NotBlank(message = "门店ID不能为空")
    @Schema(description = "门店ID")
    private String shopId;

    public IamUserRoleInfoFranchiseeBindShopInputDto(String userId,
                                                     String shopId) {
        this.userId = userId;
        this.shopId = shopId;
    }
}
