package com.machine.starter.web.accessLog;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.biam.log.IBIamUserAccessLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogCreateInputDto;
import com.machine.sdk.base.constant.ContextConstant;
import com.machine.sdk.base.context.AppContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 访问日志异步落库监听器
 */
@Slf4j
public class ApiAccessLogListener {

    private final ObjectProvider<IBIamUserAccessLogClient> accessLogClientProvider;

    public ApiAccessLogListener(ObjectProvider<IBIamUserAccessLogClient> accessLogClientProvider) {
        this.accessLogClientProvider = accessLogClientProvider;
    }

    /**
     * 异步监听访问日志事件并落库。
     */
    @EventListener
    @Async("apiAccessLogExecutor")
    public void onApiAccessLog(ApiAccessLogEvent event) {
        ApiAccessLogContext context = event.getContext();
        if (context == null) {
            return;
        }
        IBIamUserAccessLogClient client = accessLogClientProvider.getIfAvailable();
        if (client == null) {
            log.debug("未配置 IBIamUserAccessLogClient，跳过访问日志落库");
            return;
        }
        try {
            AppContextHolder.getContext().setUserId(resolveAuditUserId(context.getUserId()));
            client.create(buildInputDto(context));
        } catch (Exception error) {
            log.warn("访问日志落库失败，operateName={}, traceId={}, 原因: {}",
                    context.getOperateName(), context.getTraceId(), error.getMessage());
        } finally {
            AppContextHolder.getContext().clear();
        }
    }

    /**
     * 组装落库入参
     */
    private BIamUserAccessLogCreateInputDto buildInputDto(ApiAccessLogContext context) {
        BIamUserAccessLogCreateInputDto inputDto = new BIamUserAccessLogCreateInputDto();
        inputDto.setOperateSource(context.getOperateSource());
        inputDto.setModule(context.getModule());
        inputDto.setModuleEntity(context.getModuleEntity());
        inputDto.setOperateType(context.getOperateType());
        inputDto.setOperateName(context.getOperateName());

        inputDto.setUserId(resolveAuditUserId(context.getUserId()));
        inputDto.setUsername(context.getUsername());
        inputDto.setTraceId(context.getTraceId());

        inputDto.setClientIp(context.getClientIp());
        inputDto.setPlatform(context.getPlatform());
        inputDto.setUserAgent(context.getUserAgent());
        inputDto.setDeviceId(context.getDeviceId());

        inputDto.setHttpMethod(context.getHttpMethod());
        inputDto.setRequestPath(context.getRequestPath());
        inputDto.setQueryString(context.getQueryString());
        inputDto.setRequestBody(context.getRequestBody());

        inputDto.setResponseBody(context.getResponseBody());
        inputDto.setHttpStatus(context.getHttpStatus());

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
