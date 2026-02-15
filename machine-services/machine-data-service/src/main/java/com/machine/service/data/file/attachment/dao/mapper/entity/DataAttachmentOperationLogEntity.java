package com.machine.service.data.file.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.common.envm.data.file.DataAttachmentOperationTypeEnum;
import com.machine.starter.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("t_data_attachment_operation_log")
@EqualsAndHashCode(callSuper = true)
public class DataAttachmentOperationLogEntity extends BaseEntity {
    /**
     * 附件ID
     */
    @TableField("attachment_id")
    private String attachmentId;

    /**
     * 操作类型
     */
    @TableField("operation_type")
    private DataAttachmentOperationTypeEnum operationType;

    /**
     * IP 地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 登录平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 浏览器和操作系统等信息
     */
    @TableField("user_agent")
    private String userAgent;
}