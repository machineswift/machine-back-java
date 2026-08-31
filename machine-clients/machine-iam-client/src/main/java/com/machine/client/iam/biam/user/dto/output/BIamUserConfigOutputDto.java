package com.machine.client.iam.biam.user.dto.output;

import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamUserConfigOutputDto {

    /**
     * 配置键
     * {@link BIamUserConfigKeyEnum}
     */
    private BIamUserConfigKeyEnum configKey;

    /**
     * 配置值（JSON字符串）
     */
    private String configValue;

}
