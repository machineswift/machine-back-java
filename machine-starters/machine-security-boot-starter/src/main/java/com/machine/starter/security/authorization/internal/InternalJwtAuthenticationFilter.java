package com.machine.starter.security.authorization.internal;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.service.MachineUserDetailsService;
import com.machine.starter.security.service.model.MachineUserDetails;
import com.machine.starter.security.config.SecurityConstant;
import com.machine.starter.security.util.MachineJwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.machine.sdk.base.constant.ContextConstant.PERMISSION_CODE;
import static com.machine.sdk.base.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth.BIAM_AUTH_TOKEN_ID;
import static com.machine.starter.security.config.SecurityConstant.*;

public class InternalJwtAuthenticationFilter extends OncePerRequestFilter {

    private final MachineJwtUtil machineJwtUtil;
    private final MachineUserDetailsService userDetailService;
    private final CustomerRedisCommands customerRedisCommands;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public InternalJwtAuthenticationFilter(MachineJwtUtil machineJwtUtil,
                                           MachineUserDetailsService userDetailService,
                                           CustomerRedisCommands customerRedisCommands,
                                           AuthenticationEntryPoint authenticationEntryPoint) {
        this.machineJwtUtil = machineJwtUtil;
        this.userDetailService = userDetailService;
        this.customerRedisCommands = customerRedisCommands;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws IOException, ServletException {
        try {
            String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StrUtil.isBlank(jwt) || jwt.length() < 12) {
                throw new AuthenticationCredentialsNotFoundException("登录凭证为空");
            }

            Jwt claims = machineJwtUtil.getClaimsByToken(jwt.substring(SecurityConstant.BEARER_TYPE.length() + 1));

            AppContextHolder.getContext().setUserId(claims.getClaimAsString(USER_ID_KEY));
            MDC.put(USER_ID_KEY, AppContextHolder.getContext().getUserId());

            //验证是否为黑名单
            if (null != customerRedisCommands.get(BIAM_AUTH_TOKEN_ID + claims.getId())) {
                throw new BadCredentialsException("登录凭证已失效，请重新登录");
            }

            // 获取用户信息
            BIamUserDto iamUserDto = userDetailService.loadUserInCache();
            if (!iamUserDto.isEnabled()) {
                throw new BadCredentialsException("您的账号已被禁用，请联系客服了解详情");
            }

            //权限编码(用户计算数据权限)
            String permissionCode = request.getParameter(PERMISSION_CODE);
            if (StrUtil.isNotBlank(permissionCode)) {
                AppContextHolder.getContext().setPermissionCode(permissionCode);
            }

            if (CURRENT_USER_PATH.equals(request.getRequestURI())) {
                InternalJwtAuthenticationToken authentication = new InternalJwtAuthenticationToken();
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                MachineUserDetails userDetails = (MachineUserDetails) userDetailService.loadUserDetails();
                InternalJwtAuthenticationToken authentication = new InternalJwtAuthenticationToken(userDetails.getAuthorities());
                authentication.setJwtToken(jwt);
                authentication.setUserDetails(userDetails);
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            authenticationEntryPoint.commence(request, response, e);
        }
    }
}
