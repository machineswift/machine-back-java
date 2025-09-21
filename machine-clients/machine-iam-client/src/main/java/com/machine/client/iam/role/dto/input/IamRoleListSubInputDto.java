package com.machine.client.iam.role.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamRoleListSubInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 状态不能为空
     */
    private StatusEnum status;


    public IamRoleListSubInputDto(String id) {
        this.id = id;
    }

    public IamRoleListSubInputDto(String id,
                                  StatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
