package com.machine.service.data.filecenter.attachment.dao.mapper.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
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
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 用户名（系统账号）
     */
    @TableField("username")
    private String username;

    /**
     * 姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

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
     * 附件分组（同一分组所有版本共享）
     */
    @TableField("attachment_group")
    private String attachmentGroup;

    /**
     * 操作来源
     */
    @TableField("operate_source")
    private OperateSourceEnum operateSource;

    /**
     * 操作模块
     */
    @TableField("module")
    private ModuleEnum module;

    /**
     * 操作模块实体
     */
    @TableField("module_entity")
    private ModuleEntityEnum moduleEntity;

    /**
     * 操作模块实体Id
     */
    @TableField("module_entity_id")
    private String moduleEntityId;

    /**
     * 操作类型
     */
    @TableField("operation_type")
    private DataAttachmentOperationTypeEnum operationType;

    /**
     * 分布式链路追踪ID
     */
    @TableField("trace_id")
    private String traceId;

    /**
     * 客户端IP
     */
    @TableField("client_ip")
    private String clientIp;

    /**
     * 客户端平台
     */
    @TableField("platform")
    private String platform;

    /**
     * User-Agent（浏览器/客户端标识）
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 操作结果
     */
    @TableField("operation_result")
    private DataAttachmentOperationResultEnum operationResult;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;
}