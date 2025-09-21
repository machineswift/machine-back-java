package com.machine.starter.security.resource.open;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.auth.IIamOauth2RegisteredClientClient;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.system.SystemEnvironmentEnum;
import com.machine.sdk.common.exception.iam.access.OpenApiResourceBlackException;
import com.machine.sdk.common.exception.iam.access.OpenApiResourceClientException;
import com.machine.starter.redis.cache.LocalCacheRegisteredClient;
import com.machine.starter.redis.cache.LocalCacheSystemConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static com.machine.sdk.common.constant.ContextConstant.CLIENT_ID_KEY;
import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

@Slf4j
public class OpenApiAuthenticationFilter extends OncePerRequestFilter {

    private final String activeProfile;
    private final JwtDecoder jwtDecoder;
    private final LocalCacheSystemConfig localCacheSystemConfig;
    private final LocalCacheRegisteredClient localCacheRegisteredClient;
    private final IIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    public OpenApiAuthenticationFilter(String activeProfile,
                                       JwtDecoder jwtDecoder,
                                       LocalCacheSystemConfig localCacheSystemConfig,
                                       LocalCacheRegisteredClient localCacheRegisteredClient,
                                       IIamOauth2RegisteredClientClient oauth2RegisteredClientClient) {
        this.activeProfile = activeProfile;
        this.jwtDecoder = jwtDecoder;
        this.localCacheSystemConfig = localCacheSystemConfig;
        this.localCacheRegisteredClient = localCacheRegisteredClient;
        this.oauth2RegisteredClientClient = oauth2RegisteredClientClient;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);

        if (SystemEnvironmentEnum.PROD.getName().equals(activeProfile)) {
            //校验ip白名单
            Set<String> blankIpSet = localCacheSystemConfig.openApiBlankIp();
            if (!blankIpSet.contains(request.getRemoteAddr())) {
                throw new OpenApiResourceBlackException("IP验证失败，IP=" + request.getRemoteAddr());
            }
        }

        String clientId = request.getHeader(CLIENT_ID_KEY);
        if (StrUtil.isBlank(clientId) ||
                null == localCacheRegisteredClient.getByClientId(clientId, oauth2RegisteredClientClient)) {
            throw new OpenApiResourceClientException("客户端验证失败，clientId=" + clientId);
        } else {
            AppContext.getContext().setClientId(clientId);
        }


//        String jwt = request.getHeader(SecurityConstant.AUTH_TOKEN_HEADER_KEY);
//        if (StrUtil.isBlank(jwt)) {
//            throw new AuthenticationCredentialsNotFoundException("token 为空");
//        }
//
//        Jwt decode = jwtDecoder.decode(jwt.substring(SecurityConstant.BEARER_TYPE.length()));
//        Map<String, Object> claims = decode.getClaims();
//        String clientId = (String) claims.get("sub");
//        List<String> scopes = (List<String>) decode.getClaims().get("scope");
//
//        OpenApiUserDetails userDetails = new OpenApiUserDetails();
//        userDetails.setClientId(clientId);
//
//        List<GrantedAuthority> resultList = new ArrayList<>();
//        for (String permissionCode : scopes) {
//            resultList.add(new SimpleGrantedAuthority(permissionCode));
//        }
//        OpenApiAuthenticationToken authentication = new OpenApiAuthenticationToken(resultList);
//
//        authentication.setAuthenticated(true);
//        authentication.setUserDetails(userDetails);
//        SecurityContextHolder.getContext().setAuthentication(authentication);


        List<GrantedAuthority> resultList = new ArrayList<>();
        for (String permissionCode : List.of("iam", "data", "hrm")) {
            resultList.add(new SimpleGrantedAuthority(permissionCode));
        }
        OpenApiAuthenticationToken authentication = new OpenApiAuthenticationToken(resultList);
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
