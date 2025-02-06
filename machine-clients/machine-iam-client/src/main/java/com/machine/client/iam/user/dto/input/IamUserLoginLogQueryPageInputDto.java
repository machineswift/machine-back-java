
package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IamUserLoginLogQueryPageInputDto extends PageRequest {

    /**
     * 用户id集合
     */
    private Set<String> userIdSet;

    /**
     * 创建开始时间
     */
    private Long createStartTime;


    /**
     * 创建结束时间
     */
    private String createEndTime;


}
