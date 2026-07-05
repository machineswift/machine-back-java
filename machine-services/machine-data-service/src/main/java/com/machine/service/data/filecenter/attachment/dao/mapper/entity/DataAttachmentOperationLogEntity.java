package com.machine.service.data.filecenter.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
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
     * 版本ID
     */
    @TableField("version_id")
    private String versionId;

    /**
     * 操作类型
     */
    @TableField("operation_type")
    private DataAttachmentOperationTypeEnum operationType;

    /**
     * 操作结果
     */
    @TableField("operation_result")
    private DataAttachmentOperationResultEnum operationResult;

    /**
     * 客户端IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 客户端平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 用户代理（浏览器/客户端标识）
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 错误信息
     */
    @TableField("error_msg")
    private String errorMsg;
}