package com.machine.starter.security.login.auth2.feishu;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.AuthMethodEnum;
import com.machine.sdk.common.exception.iam.authentication.UserStatusDisableException;
import com.machine.starter.security.CustomerUserDetailsService;
import com.machine.starter.security.exception.PhoneNotFoundException;
import com.machine.starter.security.login.auth2.Auth2AuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RefreshScope
public class FeiShuAuthenticationProvider implements AuthenticationProvider {

    @Value("${auth2.feiShu.clientId:XXX}")
    private String feiShuClientId;

    @Value("${auth2.feiShu.clientSecret:XXX}")
    private String feiShuClientSecret;

    @Value("${auth2.feiShu.redirectUri:XXX}")
    private String feiShuRedirectUri;

    @Autowired
    private CustomerUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthCallback callback = JSONUtil.toBean(JSONUtil.toJsonStr(authentication), AuthCallback.class);
        AuthRequest authRequest = new AuthFeishuRequest(AuthConfig.builder()
                .clientId(feiShuClientId)
                .clientSecret(feiShuClientSecret)
                .redirectUri(feiShuRedirectUri)
                .build());
        AuthUser authUser = (AuthUser) authRequest.login(callback).getData();
        log.info("飞书授权返回信息，authUser={}", JSONUtil.toJsonStr(authUser));
        JSONObject rawUserInfo = authUser.getRawUserInfo();
        Map data = JSONUtil.toBean(JSONUtil.toJsonStr(rawUserInfo.get("data")), Map.class);
        String phone = data.get("mobile").toString();
        phone = phone.replace("+86", "");
        UserDto userDto = userDetailsService.loadUserByPhone(phone);

        if (null == userDto) {
            throw new PhoneNotFoundException(phone);
        }
        AppContext appContext = AppContext.getContext();
        appContext.setUserId(userDto.getUserId());
        appContext.setAuthMethod(AuthMethodEnum.AUTH2_FEI_SHU);

        if (!userDto.isEnabled()) {
            throw new UserStatusDisableException("您的账号已被禁用，请联系客服了解详情");
        }

        Auth2AuthenticationToken token = new Auth2AuthenticationToken();
        token.setAuthenticated(true);
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return Auth2AuthenticationToken.class.isAssignableFrom(authentication);
    }
}