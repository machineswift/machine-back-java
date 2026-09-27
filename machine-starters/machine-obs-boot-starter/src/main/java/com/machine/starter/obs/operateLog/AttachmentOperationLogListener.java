package com.machine.starter.obs.operateLog;

import cn.hutool.core.util.StrUtil;
import com.machine.client.data.filecenter.attachment.IDataAttachmentOperationLogClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.constant.ContextConstant;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public class AttachmentOperationLogListener {

    private final ObjectProvider<IDataAttachmentOperationLogClient> operationLogClientProvider;
    private final ObjectProvider<IBIamUserClient> userProvider;

    public AttachmentOperationLogListener(ObjectProvider<IDataAttachmentOperationLogClient> operationLogClientProvider,
                                          ObjectProvider<IBIamUserClient> userProvider) {
        this.operationLogClientProvider = operationLogClientProvider;
        this.userProvider = userProvider;
    }

    /**
     * 异步监听附件操作日志事件并落库。
     */
    @EventListener
    @Async("attachmentOperationLogExecutor")
    public void onAttachmentOperationLog(AttachmentOperationLogEvent event) {
        AttachmentOperationLogContext context = event.getContext();
        if (context == null || StrUtil.isBlank(context.getAttachmentId())) {
            return;
        }
        try {
            IBIamUserClient userClient = userProvider.getIfAvailable();
            IDataAttachmentOperationLogClient operationLogClient = operationLogClientProvider.getIfAvailable();
            if (userClient == null) {
                log.debug("未配置 IBIamUserClient，跳过附件操作日志落库");
                return;
            }
            if (operationLogClient == null) {
                log.debug("未配置 IDataAttachmentOperationLogClient，跳过附件操作日志落库");
                return;
            }

            AppContextHolder.getContext().setUserId(resolveAuditUserId(context.getUserId()));
            operationLogClient.create(toInputDto(userClient,context));
        } catch (Exception error) {
            log.warn("附件操作日志落库失败，attachmentId={}, operationType={}, 原因: {}",
                    context.getAttachmentId(), context.getOperationType(), error.getMessage());
        } finally {
            AppContextHolder.getContext().clear();
        }
    }

    /**
     * 组装落库入参
     */
    private DataAttachmentOperationLogCreateInputDto toInputDto(IBIamUserClient userClient,
                                                                AttachmentOperationLogContext context) {
        DataAttachmentOperationLogCreateInputDto inputDto = new DataAttachmentOperationLogCreateInputDto();

        String userId = resolveAuditUserId(context.getUserId());
        inputDto.setUserId(userId);
        BIamUserDetailOutputDto userDetail = userClient.detail(new IdRequest(context.getUserId()));
        if (null != userDetail) {
            inputDto.setUsername(userDetail.getUsername());
            inputDto.setRealName(userDetail.getName());
            inputDto.setPhone(userDetail.getPhone());
        }

        inputDto.setAttachmentId(context.getAttachmentId());
        inputDto.setVersionId(context.getVersionId());
        inputDto.setAttachmentGroup(context.getAttachmentGroup());
        inputDto.setOperateSource(context.getOperateSource());
        inputDto.setModule(context.getModule());
        inputDto.setModuleEntity(context.getModuleEntity());
        inputDto.setModuleEntityId(context.getModuleEntityId());
        inputDto.setOperationType(context.getOperationType());
        inputDto.setTraceId(context.getTraceId());
        inputDto.setClientIp(context.getClientIp());
        inputDto.setPlatform(context.getPlatform());
        inputDto.setUserAgent(context.getUserAgent());
        inputDto.setOperationResult(context.getOperationResult());
        inputDto.setErrorMessage(context.getErrorMessage());
        return inputDto;
    }

    /**
     * 审计用户ID：无真实用户时使用系统用户兜底，保证审计字段非空
     */
    private String resolveAuditUserId(String userId) {
        return StrUtil.isBlank(userId) ? ContextConstant.SYSTEM_USER_ID : userId;
    }

}
