package com.machine.starter.security.service;

import com.machine.client.iam.biam.user.dto.BIamUserDto;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 授权服务器表单登录用户加载器
 */
@Slf4j
@Service
@ConditionalOnProperty(name = "machine.iam.identity.auth2.authorization-server-enabled", havingValue = "true")
public class AuthServerUserDetailsService implements UserDetailsService {

    @Autowired
    private MachineUserDetailsService machineUserDetailsService;

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        BIamUserDto user = machineUserDetailsService.loadUserByUsername(username);
        if (user == null) {
            user = machineUserDetailsService.loadUserByPhone(username);
        }
        if (user == null) {
            log.warn("授权服务器登录，用户不存在，username={}", username);
            throw new UsernameNotFoundException(username);
        }
        if (!user.isEnabled()) {
            log.warn("授权服务器登录，账号已被禁用，username={}", username);
            throw new DisabledException("您的账号已被禁用，无法登录");
        }
        return User.withUsername(username)
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
