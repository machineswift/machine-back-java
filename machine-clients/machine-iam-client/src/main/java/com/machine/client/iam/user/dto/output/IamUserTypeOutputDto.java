package com.machine.client.iam.user.dto.output;

import com.machine.sdk.common.envm.iam.user.IamUserTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IamUserTypeOutputDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型
     */
    private IamUserTypeEnum userType;

}
