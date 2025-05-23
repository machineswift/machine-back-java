package com.machine.starter.security.resource.self;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.exception.iam.authentication.UserStatusDisableException;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.CustomerUserDetailsService;
import com.machine.starter.security.SecurityConstant;
import com.machine.sdk.common.exception.iam.authentication.JwtTokenBlackException;
import com.machine.sdk.common.exception.iam.authentication.RefreshTokenUseException;
import com.machine.starter.security.util.MachineJwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.machine.sdk.common.constant.ContextConstant.PERMISSION_CODE;
import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.IAM_AUTH_TOKEN_ID;
import static com.machine.starter.security.SecurityConstant.*;

public class SelfJwtAuthenticationFilter extends OncePerRequestFilter {

    private final MachineJwtUtil machineJwtUtil;
    private final CustomerUserDetailsService userDetailService;
    private final CustomerRedisCommands customerRedisCommands;

    public SelfJwtAuthenticationFilter(MachineJwtUtil machineJwtUtil,
                                       CustomerUserDetailsService userDetailService,
                                       CustomerRedisCommands customerRedisCommands) {
        this.machineJwtUtil = machineJwtUtil;
        this.userDetailService = userDetailService;
        this.customerRedisCommands = customerRedisCommands;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(SecurityConstant.AUTH_TOKEN_HEADER_KEY);
        if (StrUtil.isBlank(jwt) || jwt.length() < 12) {
            throw new AuthenticationCredentialsNotFoundException("token 为空");
        }

        Claims claim = machineJwtUtil.getClaimsByToken(jwt.substring(SecurityConstant.BEARER_TYPE.length() + 1));

        if (null != claim.get(AUTH_TOKEN_REFRESH_TOKEN_KEY)) {
            //验证是否是 RefreshToken
            if (!REFRESH_TOKEN_PATH.equals(request.getRequestURI())) {
                throw new RefreshTokenUseException("RefreshToken 不能用于访问资源");
            }
        }

        AppContext.getContext().setUserId(claim.get(USER_ID_KEY, String.class));
        //验证是否为黑名单
        if (null != customerRedisCommands.get(IAM_AUTH_TOKEN_ID + claim.getId())) {
            throw new JwtTokenBlackException("登录失效，请重新登录");
        }

        // 获取用户信息
        UserDto userDto = userDetailService.loadUserInCache();
        if (!userDto.isEnabled()) {
            throw new UserStatusDisableException("您的账号已被禁用，请联系客服了解详情");
        }

        //权限编码(用户计算数据权限)
        String permissionCode = request.getParameter(PERMISSION_CODE);
        if (StrUtil.isNotBlank(permissionCode)) {
            AppContext.getContext().setPermissionCode(permissionCode);
        }

        if (CURRENT_USER_PATH.equals(request.getRequestURI())) {
            SelfJwtAuthenticationToken authentication = new SelfJwtAuthenticationToken();
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            UserDetails userDetails = userDetailService.loadUserDetails();
            SelfJwtAuthenticationToken authentication = new SelfJwtAuthenticationToken(userDetails.getAuthorities());
            authentication.setJwtToken(jwt);
            authentication.setDetails(userDetails);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
