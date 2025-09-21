package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserUpdateStatusInputDto {

    @NotNull(message = "ID不能为空")
    @Schema(description = "ID")
    private String id;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    public IamUserUpdateStatusInputDto(String id,
                                       StatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
