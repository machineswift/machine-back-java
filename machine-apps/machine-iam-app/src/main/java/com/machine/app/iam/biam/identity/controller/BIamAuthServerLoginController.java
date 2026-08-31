package com.machine.app.iam.biam.identity.controller;

import com.machine.starter.security.config.SecurityConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 授权服务器登录页
 * <p>
 * GET 渲染登录表单，POST 由授权服务器登录过滤器链（AuthServerSecurityConfig）处理表单登录。
 */
@Controller
public class BIamAuthServerLoginController {

    @GetMapping(SecurityConstant.AUTH_SERVER_LOGIN_PAGE)
    public String login() {
        return "iam/oauth2/login";
    }
}
