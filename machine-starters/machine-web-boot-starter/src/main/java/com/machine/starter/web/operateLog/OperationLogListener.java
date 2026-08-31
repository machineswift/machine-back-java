package com.machine.starter.web.operateLog;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.biam.log.IBIamOperationLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogCreateInputDto;
import com.machine.sdk.base.constant.ContextConstant;
import com.machine.sdk.base.context.AppContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志异步落库监听器（统一存放 iam）。
 */
@Slf4j
public class OperationLogListener {

    private final ObjectProvider<IBIamOperationLogClient> clientProvider;

    public OperationLogListener(ObjectProvider<IBIamOperationLogClient> clientProvider) {
        this.clientProvider = clientProvider;
    }

    /**
     * 异步监听操作日志事件并落库。
     */
    @EventListener
    @Async("operationLogExecutor")
    public void onOperationLog(OperationLogEvent event) {
        OperationLogContext context = event.getContext();
        if (context == null) {
            return;
        }
        try {
            IBIamOperationLogClient client = clientProvider.getIfAvailable();
            if (client == null) {
                log.debug("未配置 IBIamOperationLogClient，跳过操作日志落库");
                return;
            }
            AppContextHolder.getContext().setUserId(resolveAuditUserId(context.getUserId()));
            client.create(buildInputDto(context));
        } catch (Exception error) {
            log.warn("操作日志落库失败，operateName={}, traceId={}, 原因: {}",
                    context.getOperateName(), context.getTraceId(), error.getMessage());
        } finally {
            AppContextHolder.getContext().clear();
        }
    }

    /**
     * 组装落库入参
     */
    private BIamOperationLogCreateInputDto buildInputDto(OperationLogContext context) {
        BIamOperationLogCreateInputDto inputDto = new BIamOperationLogCreateInputDto();
        inputDto.setUserId(resolveAuditUserId(context.getUserId()));
        inputDto.setUsername(context.getUsername());

        inputDto.setOperateSource(context.getOperateSource());
        inputDto.setModule(context.getModule());
        inputDto.setModuleEntity(context.getModuleEntity());
        inputDto.setModuleEntityId(context.getModuleEntityId());
        inputDto.setOperateType(context.getOperateType());
        inputDto.setOperateName(context.getOperateName());
        inputDto.setContent(context.getContent());
        inputDto.setDiff(context.getDiff());

        inputDto.setTraceId(context.getTraceId());
        inputDto.setClientIp(context.getClientIp());
        inputDto.setPlatform(context.getPlatform());
        inputDto.setDeviceId(context.getDeviceId());
        inputDto.setUserAgent(context.getUserAgent());

        inputDto.setHttpMethod(context.getHttpMethod());
        inputDto.setRequestPath(context.getRequestPath());
        inputDto.setQueryString(context.getQueryString());
        inputDto.setRequestBody(context.getRequestBody());
        inputDto.setHttpStatus(context.getHttpStatus());
        inputDto.setResponseBody(context.getResponseBody());

        inputDto.setActionStatus(context.getActionStatus());
        inputDto.setErrorCode(context.getErrorCode());
        inputDto.setErrorMessage(context.getErrorMessage());
        inputDto.setExceptionStack(context.getExceptionStack());

        inputDto.setCostTime(context.getCostTime());
        inputDto.setExtendInfo(context.getExtendInfo());
        return inputDto;
    }

    /**
     * 审计用户ID：无真实用户时使用系统用户兜底，保证审计字段非空
     */
    private String resolveAuditUserId(String userId) {
        return StrUtil.isBlank(userId) ? ContextConstant.SYSTEM_USER_ID : userId;
    }
}
