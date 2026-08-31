package com.machine.client.iam.biam.user.dto.output;

import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BIamUserTypeOutputDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户类型
     */
    private BIamUserTypeEnum userType;

}
