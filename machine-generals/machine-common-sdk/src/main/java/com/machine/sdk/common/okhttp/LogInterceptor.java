package com.machine.sdk.common.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public abstract class LogInterceptor implements Interceptor {

    protected abstract String getTitle();

    @Override
    public Response intercept(Chain chain) throws IOException {
        long startTime = System.currentTimeMillis();
        Request request = chain.request();
        String url = request.url().toString();
        Response response = chain.proceed(request);
        log.info("{} API-{}-{},executionTime={},url={}",
                getTitle(), request.method(), response.code(), (System.currentTimeMillis() - startTime), url);
        return response;
    }

}
