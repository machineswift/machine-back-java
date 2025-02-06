package com.machine.sdk.common.config;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.common.exception.BusinessException;
import com.machine.sdk.common.model.AppResult;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;

public class ErrorDecoder extends feign.codec.ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey,
                            Response response) {
        FeignException exception = (FeignException) super.decode(methodKey, response);

        if (exception instanceof RetryableException retryableException) {
            return retryableException;
        }

        if (exception instanceof FeignException.FeignClientException clientException) {
            return clientException;
        }

        if (exception instanceof FeignException.FeignServerException serverException) {
            if (serverException instanceof FeignException.InternalServerError internalServerError) {
                AppResult result = JSONUtil.toBean(internalServerError.contentUTF8(), AppResult.class);
                return new BusinessException(result.getCode(), result.getMessage());
            }
            return serverException;
        }

        return exception;
    }
}
