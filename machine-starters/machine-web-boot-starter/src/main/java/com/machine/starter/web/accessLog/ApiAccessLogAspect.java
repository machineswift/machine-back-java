package com.machine.starter.web.accessLog;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.exception.BusinessException;
import com.machine.sdk.base.model.AppResult;
import com.machine.sdk.base.tool.ClientEnvironmentUtil;
import com.machine.starter.web.WebProperties;
import com.machine.starter.web.accessLog.annotation.WebApiAccessLog;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 访问日志切面。
 */
@Slf4j
@Aspect
public class ApiAccessLogAspect {

    private static final String PARAM_ERROR_CODE = "PARAM_ERROR";

    private final WebProperties properties;

    private final ApplicationEventPublisher eventPublisher;

    public ApiAccessLogAspect(WebProperties properties,
                              ApplicationEventPublisher eventPublisher) {
        this.properties = properties;
        this.eventPublisher = eventPublisher;
    }

    @Around("@annotation(apiAccessLog)")
    public Object around(ProceedingJoinPoint joinPoint,
                         WebApiAccessLog apiAccessLog) throws Throwable {
        if (!properties.getAccessLog().isEnabled() || !apiAccessLog.enable()) {
            return joinPoint.proceed();
        }

        long startMillis = System.currentTimeMillis();
        ApiAccessLogContext context;
        try {
            context = buildRequestContext(joinPoint, apiAccessLog);
        } catch (Throwable error) {
            log.warn("访问日志上下文采集失败，method={}，原因: {}",
                    joinPoint.getSignature().toShortString(), error.getMessage());
            return joinPoint.proceed();
        }

        try {
            Object result = joinPoint.proceed();
            fillSuccess(context, result, apiAccessLog);
            return result;
        } catch (Throwable error) {
            fillException(context, error);
            throw error;
        } finally {
            context.setCostTime(System.currentTimeMillis() - startMillis);
            eventPublisher.publishEvent(new ApiAccessLogEvent(context));
        }
    }

    /**
     * 采集请求侧信息（注解元数据、用户、链路、客户端环境、请求体等）
     */
    private ApiAccessLogContext buildRequestContext(ProceedingJoinPoint joinPoint,
                                                    WebApiAccessLog apiAccessLog) {
        HttpServletRequest request = getRequest();

        ApiAccessLogContext context = new ApiAccessLogContext();

        // 注解元数据
        context.setOperateSource(apiAccessLog.operateSource());
        context.setModule(apiAccessLog.module());
        context.setModuleEntity(apiAccessLog.moduleEntity());
        context.setOperateType(apiAccessLog.operateType());
        context.setOperateName(apiAccessLog.operateName());

        // 用户 & 链路
        context.setUserId(AppContextHolder.getContext().getUserId());
        context.setTraceId(resolveTraceId());

        // 客户端环境
        if (request != null) {
            com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo info = ClientEnvironmentUtil.buildInfo(request);
            context.setClientIp(info.getIpAddress());
            context.setPlatform(info.getPlatform());
            context.setUserAgent(info.getUserAgent());
            context.setDeviceId(resolveHeaderOrParam(request, properties.getAccessLog().getDeviceIdHeader()));

            context.setHttpMethod(request.getMethod());
            context.setRequestPath(request.getRequestURI());
            context.setQueryString(request.getQueryString());
        }

        if (apiAccessLog.requestEnable()) {
            context.setRequestBody(resolveRequestBody(request, joinPoint, apiAccessLog));
        }

        // 扩展信息
        context.setExtendInfo(buildExtendInfo(request, joinPoint));

        return context;
    }

    private void fillSuccess(ApiAccessLogContext context,
                             Object result,
                             WebApiAccessLog apiAccessLog) {
        context.setActionStatus(ActionStatusEnum.SUCCESS);
        context.setHttpStatus(resolveHttpStatus(getResponse(), null));

        // 兼容直接返回 AppResult 的接口：按业务码判定成败
        if (result instanceof AppResult<?> appResult) {
            if (!StrUtil.equalsIgnoreCase(ActionStatusEnum.SUCCESS.getCode(), appResult.getCode())) {
                context.setActionStatus(ActionStatusEnum.FAIL);
                context.setErrorCode(appResult.getCode());
                context.setErrorMessage(appResult.getMessage());
            }
        }

        if (apiAccessLog.responseEnable()) {
            context.setResponseBody(resolveResponseBody(result, apiAccessLog));
        }
    }

    private void fillException(ApiAccessLogContext context,
                               Throwable error) {
        context.setActionStatus(ActionStatusEnum.FAIL);
        context.setErrorMessage(error.getMessage());
        context.setExceptionStack(stackTraceToString(error));
        context.setErrorCode(resolveErrorCode(error));
        context.setHttpStatus(resolveHttpStatus(getResponse(), error));
    }

    /**
     * 将异常堆栈转为字符串（截断逻辑在调用处处理）
     */
    private String stackTraceToString(Throwable error) {
        if (error == null) {
            return null;
        }
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        error.printStackTrace(new java.io.PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    private String resolveRequestBody(HttpServletRequest request,
                                      ProceedingJoinPoint joinPoint,
                                      WebApiAccessLog apiAccessLog) {
        String body = null;
        if (request instanceof ContentCachingRequestWrapper wrapper) {
            byte[] content = wrapper.getContentAsByteArray();
            if (content.length > 0) {
                body = new String(content, StandardCharsets.UTF_8);
            }
        }
        if (StrUtil.isBlank(body)) {
            body = serializeArgs(joinPoint.getArgs());
        }
        body = sanitize(body, apiAccessLog.sanitizeKeys());
        return body;
    }

    /**
     * 响应体解析：序列化控制器方法返回值（在业务数据层面做审计），并对敏感字段脱敏
     */
    private String resolveResponseBody(Object result,
                                       WebApiAccessLog apiAccessLog) {
        if (result == null) {
            return null;
        }
        // 二进制/流式响应不记录
        if (result instanceof byte[] || result instanceof MultipartFile) {
            return "[BINARY]";
        }
        String body = JSONUtil.toJsonStr(result);
        return sanitize(body, apiAccessLog.sanitizeKeys());
    }

    private String serializeArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        List<Object> list = new ArrayList<>(args.length);
        for (Object arg : args) {
            if (arg == null) {
                continue;
            }
            if (arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse
                    || arg instanceof MultipartFile
                    || arg instanceof BindingResult) {
                continue;
            }
            list.add(arg);
        }
        if (list.isEmpty()) {
            return null;
        }
        return JSONUtil.toJsonStr(list);
    }

    /**
     * 敏感字段脱敏：对 JSON 中指定 key 的值进行掩码
     */
    private String sanitize(String content, String[] sanitizeKeys) {
        if (StrUtil.isBlank(content) || sanitizeKeys == null || sanitizeKeys.length == 0) {
            return content;
        }
        String result = content;
        for (String key : sanitizeKeys) {
            if (StrUtil.isBlank(key)) {
                continue;
            }
            String pattern = "(\"" + Pattern.quote(key) + "\"\\s*:\\s*)(\"[^\"]*\")";
            result = result.replaceAll(pattern, "$1\"******\"");
        }
        return result;
    }

    private String resolveErrorCode(Throwable error) {
        if (error instanceof BusinessException businessException && StrUtil.isNotBlank(businessException.getCode())) {
            return businessException.getCode();
        }
        if (error instanceof BindException
                || error instanceof MissingServletRequestParameterException) {
            return PARAM_ERROR_CODE;
        }
        return "";
    }

    private int resolveHttpStatus(HttpServletResponse response,
                                  Throwable error) {
        if (error != null) {
            if (error instanceof BindException
                    || error instanceof MissingServletRequestParameterException
                    || error instanceof jakarta.validation.ConstraintViolationException) {
                return HttpStatus.BAD_REQUEST.value();
            }
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        return response != null ? response.getStatus() : HttpStatus.OK.value();
    }

    private String resolveTraceId() {
        try {
            String traceId = TraceContext.traceId();
            return StrUtil.isBlank(traceId) ? "" : traceId;
        } catch (Throwable error) {
            return "";
        }
    }

    private String resolveHeaderOrParam(HttpServletRequest request,
                                        String name) {
        if (StrUtil.isBlank(name)) {
            return null;
        }
        String value = request.getHeader(name);
        if (StrUtil.isBlank(value)) {
            value = request.getParameter(name);
        }
        return value;
    }

    private Map<String, Object> buildExtendInfo(HttpServletRequest request,
                                                ProceedingJoinPoint joinPoint) {
        Map<String, Object> extendInfo = new LinkedHashMap<>();

        // 定位方法信息（类#方法）
        if (joinPoint.getSignature() instanceof MethodSignature signature) {
            Method method = signature.getMethod();
            extendInfo.put("classMethod", method.getDeclaringClass().getName() + "#" + method.getName());
        }

        if (request != null) {
            extendInfo.put("contentType", request.getContentType());
            String[] extendHeaders = properties.getAccessLog().getExtendHeaders();
            if (extendHeaders != null && extendHeaders.length > 0) {
                Map<String, String> headerMap = new HashMap<>(extendHeaders.length);
                for (String header : extendHeaders) {
                    String value = request.getHeader(header);
                    if (StrUtil.isNotBlank(value)) {
                        headerMap.put(header, value);
                    }
                }
                if (!headerMap.isEmpty()) {
                    extendInfo.put("headers", headerMap);
                }
            }
        }
        return extendInfo;
    }


    private HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            return attributes.getRequest();
        }
        return null;
    }

    private HttpServletResponse getResponse() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes) {
            return attributes.getResponse();
        }
        return null;
    }
}
