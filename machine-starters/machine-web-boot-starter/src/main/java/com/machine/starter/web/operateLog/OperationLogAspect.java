package com.machine.starter.web.operateLog;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.base.audit.ActionStatusEnum;
import com.machine.sdk.base.exception.BusinessException;
import com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo;
import com.machine.sdk.base.tool.ClientEnvironmentUtil;
import com.machine.starter.web.WebProperties;
import com.machine.starter.web.operateLog.annotation.WebOperationLog;
import com.machine.starter.web.operateLog.diff.OperationLogDiffHelper;
import com.machine.starter.web.operateLog.loader.OperationLogEntityLoader;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 业务操作日志切面（只在 controller 层加注解）。
 */
@Slf4j
@Aspect
public class OperationLogAspect {

    private final WebProperties properties;
    private final ApplicationEventPublisher eventPublisher;
    private final OperationLogLoaderRegistry loaderRegistry;

    private final SpelExpressionParser spelParser = new SpelExpressionParser();
    private final Map<String, Expression> expressionCache = new ConcurrentHashMap<>();
    private final DefaultParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    public OperationLogAspect(WebProperties properties,
                              ApplicationEventPublisher eventPublisher,
                              OperationLogLoaderRegistry loaderRegistry) {
        this.properties = properties;
        this.eventPublisher = eventPublisher;
        this.loaderRegistry = loaderRegistry;
    }

    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint joinPoint,
                         WebOperationLog operationLog) throws Throwable {
        if (!properties.getOperationLog().isEnabled() || !operationLog.enable()) {
            return joinPoint.proceed();
        }

        long startMillis = System.currentTimeMillis();
        OperationLogContext context;
        Object before;
        try {
            context = buildRequestContext(joinPoint, operationLog);
            before = loadBefore(joinPoint, operationLog, context.getModuleEntityId());
        } catch (Throwable error) {
            log.warn("操作日志上下文采集失败，method={}，原因: {}",
                    joinPoint.getSignature().toShortString(), error.getMessage());
            return joinPoint.proceed();
        }

        try {
            Object result = joinPoint.proceed();
            fillSuccess(context, result, operationLog, before, joinPoint);
            return result;
        } catch (Throwable error) {
            fillException(context, error);
            throw error;
        } finally {
            context.setCostTime(System.currentTimeMillis() - startMillis);
            eventPublisher.publishEvent(new OperationLogEvent(context));
        }
    }

    /**
     * 采集请求侧信息（注解语义、用户、链路、客户端环境、请求体快照）。
     */
    private OperationLogContext buildRequestContext(ProceedingJoinPoint joinPoint,
                                                    WebOperationLog operationLog) {
        HttpServletRequest request = getRequest();

        OperationLogContext context = new OperationLogContext();
        context.setOperateSource(operationLog.operateSource());
        context.setModule(operationLog.module());
        context.setModuleEntity(operationLog.moduleEntity());
        context.setOperateType(operationLog.operateType());
        context.setOperateName(operationLog.operateName());
        context.setModuleEntityId(asString(evaluateSpel(operationLog.moduleEntityId(), joinPoint, null)));

        context.setUserId(AppContextHolder.getContext().getUserId());
        context.setTraceId(resolveTraceId());

        if (request != null) {
            ClientEnvironmentInfo info = ClientEnvironmentUtil.buildInfo(request);
            context.setClientIp(info.getIpAddress());
            context.setPlatform(info.getPlatform());
            context.setUserAgent(info.getUserAgent());
            context.setDeviceId(resolveHeaderOrParam(request, properties.getOperationLog().getDeviceIdHeader()));

            context.setHttpMethod(request.getMethod());
            context.setRequestPath(request.getRequestURI());
            context.setQueryString(request.getQueryString());
        }

        context.setRequestBody(resolveRequestBody(request, joinPoint, operationLog));
        context.setExtendInfo(buildExtendInfo(request, joinPoint));
        return context;
    }

    /**
     * 请求体快照：优先 ContentCachingRequestWrapper 缓存内容，否则序列化方法参数；按注解脱敏。
     */
    private String resolveRequestBody(HttpServletRequest request,
                                      ProceedingJoinPoint joinPoint,
                                      WebOperationLog operationLog) {
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
        return sanitize(body, operationLog.sanitizeKeys());
    }

    private String serializeArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        List<Object> list = new ArrayList<>(args.length);
        for (Object arg : args) {
            if (arg == null || arg instanceof HttpServletRequest
                    || arg instanceof HttpServletResponse
                    || arg instanceof MultipartFile) {
                continue;
            }
            list.add(arg);
        }
        return list.isEmpty() ? null : JSONUtil.toJsonStr(list);
    }

    /**
     * 敏感字段脱敏：对 JSON 中指定 key 的值进行掩码。
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

    /**
     * 扩展信息：定位方法信息（类#方法）、contentType、及配置的扩展请求头。
     */
    private Map<String, Object> buildExtendInfo(HttpServletRequest request,
                                                ProceedingJoinPoint joinPoint) {
        Map<String, Object> extendInfo = new LinkedHashMap<>();

        if (joinPoint.getSignature() instanceof MethodSignature signature) {
            Method method = signature.getMethod();
            extendInfo.put("classMethod", method.getDeclaringClass().getName() + "#" + method.getName());
        }

        if (request != null) {
            extendInfo.put("contentType", request.getContentType());
            String[] extendHeaders = properties.getOperationLog().getExtendHeaders();
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

    /**
     * 变更前快照：从快照加载器加载。
     */
    private Object loadBefore(ProceedingJoinPoint joinPoint,
                              WebOperationLog operationLog,
                              String entityId) {
        if (!operationLog.diff()) {
            return null;
        }
        return safeLoad(resolveLoader(joinPoint, operationLog), entityId);
    }

    /**
     * 变更后快照：优先方法返回值，其次 loader 再查。
     */
    private Object resolveAfter(ProceedingJoinPoint joinPoint,
                                WebOperationLog operationLog,
                                Object result,
                                String entityId) {
        if (result != null) {
            return result;
        }
        return safeLoad(resolveLoader(joinPoint, operationLog), entityId);
    }

    /**
     * 按「实体 + 方法名」定位快照加载器。
     */
    private OperationLogEntityLoader resolveLoader(ProceedingJoinPoint joinPoint,
                                                   WebOperationLog operationLog) {
        return loaderRegistry.get(operationLog.moduleEntity(), resolveMethodName(joinPoint));
    }

    /**
     * 当前拦截的控制器方法名（用于定位加载器）。
     */
    private String resolveMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private Object safeLoad(OperationLogEntityLoader loader,
                            Object bizId) {
        try {
            return loader == null ? null : loader.load(bizId);
        } catch (Throwable error) {
            log.warn("操作日志快照加载失败，bizId={}，原因: {}", bizId, error.getMessage());
            return null;
        }
    }


    private void fillSuccess(OperationLogContext context,
                             Object result,
                             WebOperationLog operationLog,
                             Object before,
                             ProceedingJoinPoint joinPoint) {
        context.setActionStatus(ActionStatusEnum.SUCCESS);
        context.setHttpStatus(resolveHttpStatus(getResponse(), null));

        Object after = null;
        if (operationLog.diff()) {
            after = resolveAfter(joinPoint, operationLog, result, context.getModuleEntityId());
            Map<String, Object> diffMap = OperationLogDiffHelper.diff(before, after, operationLog.ignoreFields());
            context.setDiff(diffMap == null ? null : JSONUtil.toJsonStr(diffMap));
        }
        if (operationLog.responseEnable()) {
            context.setResponseBody(resolveResponseBody(result, operationLog));
        }
        context.setContent(resolveContent(joinPoint, operationLog, context, before, after, result));
    }

    private void fillException(OperationLogContext context,
                               Throwable error) {
        context.setActionStatus(ActionStatusEnum.FAIL);
        context.setErrorMessage(error.getMessage());
        context.setExceptionStack(stackTraceToString(error));
        context.setErrorCode(resolveErrorCode(error));
        context.setHttpStatus(resolveHttpStatus(getResponse(), error));
    }

    /**
     * 解析内容模板；失败/为空时兜底为"操作名 + bizId"。
     */
    private String resolveContent(ProceedingJoinPoint joinPoint,
                                  WebOperationLog operationLog,
                                  OperationLogContext context,
                                  Object before,
                                  Object after,
                                  Object result) {
        if (StrUtil.isNotBlank(operationLog.content())) {
            // 用 HashMap 而非 Map.of：Map.of 不允许 null 值，而 result/before/after 可能为 null
            Map<String, Object> variables = new HashMap<>(4);
            variables.put("moduleEntityId", context.getModuleEntityId());
            variables.put("result", result);
            variables.put("before", before);
            variables.put("after", after);
            Object value = evaluateSpel(operationLog.content(), joinPoint, variables);
            if (value != null) {
                return asString(value);
            }
        }
        return StrUtil.isBlank(context.getModuleEntityId())
                ? operationLog.operateName()
                : operationLog.operateName() + "，实体ID=" + context.getModuleEntityId();
    }

    /**
     * 响应体快照：序列化控制器方法返回值，并对敏感字段脱敏。
     */
    private String resolveResponseBody(Object result,
                                       WebOperationLog operationLog) {
        if (result == null) {
            return null;
        }
        // 二进制/流式响应不记录
        if (result instanceof byte[] || result instanceof MultipartFile) {
            return "[BINARY]";
        }
        return sanitize(JSONUtil.toJsonStr(result), operationLog.sanitizeKeys());
    }

    private int resolveHttpStatus(HttpServletResponse response,
                                  Throwable error) {
        if (error != null) {
            if (error instanceof BindException
                    || error instanceof MissingServletRequestParameterException
                    || error instanceof ConstraintViolationException) {
                return HttpStatus.BAD_REQUEST.value();
            }
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        return response != null ? response.getStatus() : HttpStatus.OK.value();
    }

    private String resolveErrorCode(Throwable error) {
        if (error instanceof BusinessException businessException
                && StrUtil.isNotBlank(businessException.getCode())) {
            return businessException.getCode();
        }
        return "";
    }

    /**
     * 将异常堆栈转为字符串。
     */
    private String stackTraceToString(Throwable error) {
        if (error == null) {
            return null;
        }
        StringWriter stringWriter = new StringWriter();
        error.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * SpEL 求值（采用 Spring 原生 {@link MethodBasedEvaluationContext} 方式）
     */
    private Object evaluateSpel(String expression,
                                ProceedingJoinPoint joinPoint,
                                Map<String, Object> extra) {
        if (StrUtil.isBlank(expression)) {
            return null;
        }

        if (!(joinPoint.getSignature() instanceof MethodSignature signature)) {
            return null;
        }
        MethodBasedEvaluationContext ctx = new MethodBasedEvaluationContext(
                joinPoint.getTarget(), signature.getMethod(), joinPoint.getArgs(), parameterNameDiscoverer);
        if (extra != null) {
            extra.forEach(ctx::setVariable);
        }
        return parseExpression(expression).getValue(ctx);
}

/**
 * 解析并缓存 SpEL 表达式（同 Spring {@code CachedExpressionEvaluator} 思路）。
 */
private Expression parseExpression(String expression) {
    return expressionCache.computeIfAbsent(expression, spelParser::parseExpression);
}

private String resolveTraceId() {
    String traceId = TraceContext.traceId();
    return StrUtil.isBlank(traceId) ? "" : traceId;
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

private String asString(Object value) {
    return value == null ? null : String.valueOf(value);
}
}
