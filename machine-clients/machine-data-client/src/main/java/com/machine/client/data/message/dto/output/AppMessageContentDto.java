package com.machine.client.data.message.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppMessageContentDto {

    /**
     * 通知按钮名称
     */
    private String informButtonName;

    /**
     * 后台跳转链接
     */
    private String manageJumpLink;

    /**
     * app跳转链接
     */
    private String appJumpLink;

    /**
     * 业务id
     */
    private String businessId;

}