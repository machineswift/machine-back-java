package com.machine.client.iam.biam.user.dto.input;

import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamUserConfigGetInputDto {

    /**
     * 用户Id
     */
    @NotBlank(message = "用户id不能为空")
    private String userId;

    /**
     * 配置键
     * {@link BIamUserConfigKeyEnum}
     */
    @NotNull(message = "配置键不能为空")
    private BIamUserConfigKeyEnum configKey;

}
