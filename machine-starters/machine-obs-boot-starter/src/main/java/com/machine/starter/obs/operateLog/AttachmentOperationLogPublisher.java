package com.machine.starter.obs.operateLog;

import cn.hutool.core.util.StrUtil;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.ClientEnvironmentUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class AttachmentOperationLogPublisher {

    private final ApplicationEventPublisher eventPublisher;
    private final  ObjectProvider<IDataAttachmentClient> attachmentProvider;

    public AttachmentOperationLogPublisher(ApplicationEventPublisher eventPublisher,
                                           ObjectProvider<IDataAttachmentClient> attachmentProvider) {
        this.eventPublisher = eventPublisher;
        this.attachmentProvider = attachmentProvider;
    }

    public void publish(String attachmentId,
                        DataAttachmentOperationTypeEnum operationType,
                        OperateSourceEnum operateSource,
                        ModuleEnum module) {
        IDataAttachmentClient attachmentClient = attachmentProvider.getIfAvailable();
        if (attachmentClient == null) {
            log.debug("未配置 IDataAttachmentClient，跳过附件操作日志落库");
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == attributes) {
            log.debug("未查到 ServletRequestAttributes，跳过附件操作日志落库");
            return;
        }

        AttachmentOperationLogContext logContext = new AttachmentOperationLogContext();

        DataAttachmentDetailOutputDto attachmentDetail = attachmentClient.getById(new IdRequest(attachmentId));
        logContext.setAttachmentId(attachmentId);
        logContext.setVersionId(attachmentDetail.getCurrentVersionId());
        logContext.setAttachmentGroup(attachmentDetail.getAttachmentGroup());
        logContext.setOperateSource(operateSource);
        logContext.setModule(module);
        logContext.setModuleEntity(attachmentDetail.getEntity());
        logContext.setModuleEntityId(attachmentDetail.getEntityId());

        logContext.setOperationType(operationType);
        logContext.setOperationResult(DataAttachmentOperationResultEnum.SUCCESS);

        logContext.setUserId(AppContextHolder.getContext().getUserId());

        logContext.setTraceId(resolveTraceId());

        HttpServletRequest servletRequest = attributes.getRequest();
        ClientEnvironmentInfo environmentInfo = ClientEnvironmentUtil.buildInfo(servletRequest);
        logContext.setClientIp(environmentInfo.getIpAddress());
        logContext.setPlatform(environmentInfo.getPlatform());
        logContext.setUserAgent(environmentInfo.getUserAgent());

        eventPublisher.publishEvent(new AttachmentOperationLogEvent(logContext));
    }

    private String resolveTraceId() {
        String traceId = TraceContext.traceId();
        return StrUtil.isBlank(traceId) ? "" : traceId;
    }

}
