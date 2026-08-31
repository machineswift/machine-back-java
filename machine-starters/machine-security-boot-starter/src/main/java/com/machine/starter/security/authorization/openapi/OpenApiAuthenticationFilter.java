package com.machine.starter.security.authorization.openapi;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.dto.biam.identity.BIamOAuth2RegisteredClientDto;
import com.machine.starter.redis.caffeine.CaffeineCacheRegisteredClient;
import com.machine.starter.security.config.SecurityConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static com.machine.sdk.base.constant.ContextConstant.SYSTEM_USER_ID;

@Slf4j
public class OpenApiAuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final CaffeineCacheRegisteredClient registeredClient;

    public OpenApiAuthenticationFilter(JwtDecoder jwtDecoder,
                                       CaffeineCacheRegisteredClient registeredClient) {
        this.jwtDecoder = jwtDecoder;
        this.registeredClient = registeredClient;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        AppContextHolder.getContext().setUserId(SYSTEM_USER_ID);

        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(jwt) || !jwt.startsWith(SecurityConstant.BEARER_TYPE_WITH_SPACE)) {
            throw new AuthenticationCredentialsNotFoundException("客户端凭证为空");
        }

        Jwt decode;
        try {
            decode = jwtDecoder.decode(jwt.substring(SecurityConstant.BEARER_TYPE_WITH_SPACE.length()));
        } catch (JwtException e) {
            log.warn("客户端凭证解析失败，error={}", e.getMessage());
            throw new BadCredentialsException("客户端凭证解析失败");
        }
        Map<String, Object> claims = decode.getClaims();

        // 仅允许客户端凭证模式签发的 token 访问开放接口，避免用户 token 冒用
        String grantType = (String) claims.get("grantType");
        if (!AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(grantType)) {
            throw new BadCredentialsException("凭证不是客户端凭证模式");
        }

        String clientId = (String) claims.get("sub");
        BIamOAuth2RegisteredClientDto clientDto = registeredClient.getByClientId(clientId);
        if (null == clientDto || StatusEnum.DISABLE == clientDto.getStatus()) {
            throw new BadCredentialsException("客户端验证失败，clientId=" + clientId);
        }

        //校验ip白名单
        Set<String> allowedIps = clientDto.getAllowedIps();
        if (CollectionUtil.isNotEmpty(allowedIps) && !allowedIps.contains(request.getRemoteAddr())) {
            throw new BadCredentialsException("客户端IP验证失败，IP=" + request.getRemoteAddr());
        }

        AppContextHolder.getContext().setClientId(clientId);
        OpenApiUserDetails userDetails = new OpenApiUserDetails();
        userDetails.setClientId(clientId);

        List<GrantedAuthority> resultList = new ArrayList<>();
        for (String authority : clientDto.getScopes()) {
            resultList.add(new SimpleGrantedAuthority(authority));
        }
        OpenApiAuthenticationToken authentication = new OpenApiAuthenticationToken(resultList);
        authentication.setClientId(clientId);
        authentication.setUserDetails(userDetails);
        authentication.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
