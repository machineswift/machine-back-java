package com.machine.starter.security.handler;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.base.model.AppResult;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MachineAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(@NonNull HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");

        ServletOutputStream outputStream = response.getOutputStream();
        AppResult<String> appResult = AppResult.fail("iam.auth.authentication.exception", authException.getMessage());
        outputStream.write(JSONUtil.toJsonStr(appResult).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}