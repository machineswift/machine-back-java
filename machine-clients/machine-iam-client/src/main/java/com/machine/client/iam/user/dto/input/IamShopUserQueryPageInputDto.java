package com.machine.client.iam.user.dto.input;

import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IamShopUserQueryPageInputDto extends PageRequest {

    /**
     * 用户ID集合
     */
    private Set<String> userIdSet;

    /**
     * 状态
     */
    private StatusEnum status;

    /**
     * 系统账号(用户名)
     */
    private String userName;

    /**
     * 编码
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 更新开始时间
     */
    private Long updateStartTime;

    /**
     * 更新结束时间
     */
    private String updateEndTime;

    /**
     * 创建开始时间
     */
    private Long createStartTime;

    /**
     * 创建结束时间
     */
    private String createEndTime;

}
