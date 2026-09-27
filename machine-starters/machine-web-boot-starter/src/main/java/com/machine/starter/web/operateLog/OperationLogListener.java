package com.machine.starter.web.operateLog;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.biam.log.IBIamOperationLogClient;
import com.machine.client.iam.biam.log.IBIamUserAccessLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogCreateInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogCreateInputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.constant.ContextConstant;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志异步落库监听器（统一存放 iam）。
 */
@Slf4j
public class OperationLogListener {

    private final ObjectProvider<IBIamUserClient> biamUserProvider;
    private final ObjectProvider<IBIamUserAccessLogClient> accessLogClientProvider;
    private final ObjectProvider<IBIamOperationLogClient> operationLogClientProvider;

    public OperationLogListener(ObjectProvider<IBIamUserClient> biamUserProvider,
                                ObjectProvider<IBIamUserAccessLogClient> accessLogClientProvider,
                                ObjectProvider<IBIamOperationLogClient> operationLogClientProvider) {
        this.biamUserProvider = biamUserProvider;
        this.accessLogClientProvider = accessLogClientProvider;
        this.operationLogClientProvider = operationLogClientProvider;
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

        IBIamUserClient userClient = biamUserProvider.getIfAvailable();
        IBIamUserAccessLogClient accessLogClient = accessLogClientProvider.getIfAvailable();
        IBIamOperationLogClient operationLogClient = operationLogClientProvider.getIfAvailable();
        if (userClient == null) {
            log.debug("未配置 IBIamUserClient，跳过操作日志落库");
            return;
        }
        if (accessLogClient == null) {
            log.debug("未配置 IBIamUserAccessLogClient，跳过操作日志落库");
            return;
        }
        if (operationLogClient == null) {
            log.debug("未配置 IBIamOperationLogClient，跳过操作日志落库");
            return;
        }

        try {
            AppContextHolder.getContext().setUserId(resolveAuditUserId(context.getUserId()));
            BIamOperationLogCreateInputDto operationLogCreateInputDto = buildInputDto(userClient,context);

            operationLogClient.create(operationLogCreateInputDto);
            accessLogClient.create(toAccessLog(operationLogCreateInputDto));
        } catch (Exception error) {
            log.warn("操作日志落库失败，operateName={}, traceId={}, 原因: {}",
                    context.getOperateName(), context.getTraceId(), error.getMessage());
        } finally {
            AppContextHolder.getContext().clear();
        }
    }

    public static BIamUserAccessLogCreateInputDto toAccessLog(BIamOperationLogCreateInputDto operationLog) {
        BIamUserAccessLogCreateInputDto accessLog = new BIamUserAccessLogCreateInputDto();

        // 用户信息
        accessLog.setUserId(operationLog.getUserId());
        accessLog.setUsername(operationLog.getUsername());
        accessLog.setRealName(operationLog.getRealName());
        accessLog.setPhone(operationLog.getPhone());

        // 操作来源与模块
        accessLog.setOperateSource(operationLog.getOperateSource());
        accessLog.setModule(operationLog.getModule());
        accessLog.setModuleEntity(operationLog.getModuleEntity());

        // 操作类型与名称
        accessLog.setOperateType(operationLog.getOperateType());
        accessLog.setOperateName(operationLog.getOperateName());

        // 链路与网络信息
        accessLog.setTraceId(operationLog.getTraceId());
        accessLog.setClientIp(operationLog.getClientIp());
        accessLog.setPlatform(operationLog.getPlatform());
        accessLog.setDeviceId(operationLog.getDeviceId());
        accessLog.setUserAgent(operationLog.getUserAgent());

        // 请求信息
        accessLog.setHttpMethod(operationLog.getHttpMethod());
        accessLog.setRequestPath(operationLog.getRequestPath());
        accessLog.setQueryString(operationLog.getQueryString());
        accessLog.setRequestBody(operationLog.getRequestBody());

        // 响应信息
        accessLog.setHttpStatus(operationLog.getHttpStatus());
        accessLog.setResponseBody(operationLog.getResponseBody());

        // 业务状态与错误信息
        accessLog.setActionStatus(operationLog.getActionStatus());
        accessLog.setErrorCode(operationLog.getErrorCode());
        accessLog.setErrorMessage(operationLog.getErrorMessage());
        accessLog.setExceptionStack(operationLog.getExceptionStack());

        // 性能与扩展信息
        accessLog.setCostTime(operationLog.getCostTime());
        accessLog.setExtendInfo(operationLog.getExtendInfo());
        return accessLog;
    }

    /**
     * 组装落库入参
     */
    private BIamOperationLogCreateInputDto buildInputDto(IBIamUserClient userClient,
                                                         OperationLogContext context) {
        BIamOperationLogCreateInputDto inputDto = new BIamOperationLogCreateInputDto();
        inputDto.setUserId(resolveAuditUserId(context.getUserId()));
        BIamUserDetailOutputDto userDetail = userClient.detail(new IdRequest(context.getUserId()));
        if (null != userDetail) {
            inputDto.setUsername(userDetail.getUsername());
            inputDto.setRealName(userDetail.getName());
            inputDto.setPhone(userDetail.getPhone());
        }

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
