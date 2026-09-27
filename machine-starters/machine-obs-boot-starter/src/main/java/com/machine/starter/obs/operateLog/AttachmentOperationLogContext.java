package com.machine.starter.obs.operateLog;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import lombok.Data;

@Data
public class AttachmentOperationLogContext {

    // 操作主体
    private String userId;

    // 附件信息
    private String attachmentId;

    private String versionId;

    private String attachmentGroup;

    // 操作信息
    private OperateSourceEnum operateSource;

    private ModuleEnum module;

    private ModuleEntityEnum moduleEntity;

    private String moduleEntityId;

    private DataAttachmentOperationTypeEnum operationType;

    // 请求链路
    private String traceId;

    private String clientIp;

    private String platform;

    private String userAgent;

    // 操作结果
    private DataAttachmentOperationResultEnum operationResult;

    private String errorMessage;
}
