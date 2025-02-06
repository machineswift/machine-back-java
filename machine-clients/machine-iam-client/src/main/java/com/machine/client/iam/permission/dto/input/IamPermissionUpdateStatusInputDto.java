package com.machine.client.iam.permission.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IamPermissionUpdateStatusInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    @NotNull(message = "状态不能为空")
    private StatusEnum status;
}
