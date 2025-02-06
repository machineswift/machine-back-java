package com.machine.client.data.informaion.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AppMessageContentDto implements Serializable {

    private static final long serialVersionUID = 12423409423L;

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