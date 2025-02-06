package com.machine.starter.security.login.auth2.gitee;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.starter.security.CustomerUserDetails;
import com.machine.starter.security.login.auth2.Auth2AuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope
public class GiteeAuthenticationProvider implements AuthenticationProvider {

    @Value("${auth2.gitee.clientId:XXX}")
    private String giteeClientId;

    @Value("${auth2.gitee.clientSecret:XXX}")
    private String giteeClientSecret;

    @Value("${auth2.gitee.redirectUri:XXX}")
    private String giteeRedirectUri;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthCallback callback = JSONUtil.toBean(JSONUtil.toJsonStr(authentication), AuthCallback.class);
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
                .clientId(giteeClientId)
                .clientSecret(giteeClientSecret)
                .redirectUri(giteeRedirectUri)
                .build());
        AuthUser authUser = (AuthUser) authRequest.login(callback).getData();
        log.info("authUser={}", JSONUtil.toJsonStr(authUser));
        // todo machine auth2 gitee(绑定用户，查询用户明细)


        AppContext appContext= AppContext.getContext();
        //appContext.setUserId(userDetails.getUserId());
        appContext.setAuthMethod(AuthMethodEnum.USERNAME_PASSWORD);


        CustomerUserDetails userDetails = null;
        Auth2AuthenticationToken token = new Auth2AuthenticationToken();
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Auth2AuthenticationToken.class.isAssignableFrom(authentication);
    }

}