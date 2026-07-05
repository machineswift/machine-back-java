package com.machine.sdk.base.tool;

import cn.hutool.extra.servlet.JakartaServletUtil;
import cn.hutool.http.useragent.Platform;
import cn.hutool.http.useragent.UserAgentUtil;
import com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo;
import jakarta.servlet.http.HttpServletRequest;

public class ClientEnvironmentUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取客户端真实IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }
        return JakartaServletUtil.getClientIP(request);
    }

    /**
     * 构建客户端环境信息
     */
    public static ClientEnvironmentInfo buildInfo(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        Platform platform = UserAgentUtil.parse(userAgentStr).getPlatform();

        ClientEnvironmentInfo info = new ClientEnvironmentInfo();
        info.setIpAddress(getIpAddress(request));
        info.setPlatform(platform.getName());
        info.setUserAgent(userAgentStr);
        return info;
    }

}
