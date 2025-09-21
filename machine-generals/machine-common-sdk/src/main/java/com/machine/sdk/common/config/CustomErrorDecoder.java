package com.machine.sdk.common.config;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.common.exception.BusinessException;
import com.machine.sdk.common.model.AppResult;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        // 首先处理可重试异常
        FeignException exception = (FeignException) defaultDecoder.decode(methodKey, response);
        if (exception instanceof RetryableException) {
            return exception;
        }

        // 处理4xx客户端错误
        if (exception instanceof FeignException.FeignClientException) {
            String responseBody = exception.contentUTF8();
            AppResult<?> result = JSONUtil.toBean(responseBody, AppResult.class);
            if (result != null && result.getCode() != null) {
                return new BusinessException(result.getCode(), result.getMessage());
            }
        }

        //  默认情况
        return exception;
    }
}