package com.machine.client.iam.biam.role.dto.input;

import com.machine.sdk.base.envm.StatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamRoleListSubInputDto {

    @NotBlank(message = "id不能为空")
    private String id;

    /**
     * 状态不能为空
     */
    private StatusEnum status;


    public BIamRoleListSubInputDto(String id) {
        this.id = id;
    }

    public BIamRoleListSubInputDto(String id,
                                   StatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
