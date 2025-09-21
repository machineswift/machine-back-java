package com.machine.starter.security.login.username;

import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.IamUserUpdatePasswordInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.exception.iam.authentication.UserStatusDisableException;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.CustomerUserDetailsService;
import com.machine.starter.security.exception.CaptchaWrongAuthenticationException;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 帐号密码登录认证
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsService userDetailsService;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IIamUserClient userClient;


    public UsernameAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernameAuthenticationToken authenticationToken = (UsernameAuthenticationToken) authentication;

        // 用户提交的用户名 + 密码
        String username = authenticationToken.getUsername();
        String password = authenticationToken.getPassword();
        String captcha = authenticationToken.getCaptcha();
        String userKey = authenticationToken.getUserKey();

        // 校验验证码
        validateCaptcha(captcha, userKey);

        // 获取信息
        IamUserDto iamUserDto = userDetailsService.loadUserByUsername(username);
        if (iamUserDto == null) {
            iamUserDto = userDetailsService.loadUserByPhone(username);
        }
        if (iamUserDto == null) {
            throw new UsernameNotFoundException(username);
        }

        AppContext appContext = AppContext.getContext();
        appContext.setUserId(iamUserDto.getUserId());
        appContext.setAuthMethod(IamAuthMethodEnum.USERNAME_PASSWORD);

        if (!iamUserDto.isEnabled()) {
            throw new UserStatusDisableException("您的账号已被禁用，请联系客服了解详情");
        }

        if (!passwordEncoder.matches(password, iamUserDto.getPassword())) {
            throw new BadCredentialsException("用户名或密码不正确");
        }

        //修改密码的加密方式
        processPasswordEncode(password, iamUserDto.getPassword());

        UsernameAuthenticationToken token = new UsernameAuthenticationToken();
        token.setAuthenticated(true);
        return token;
    }

    private void processPasswordEncode(String password,
                                       String passwordDb) {
        boolean upgradeEncoding = passwordEncoder.upgradeEncoding(passwordDb);
        if (!upgradeEncoding) {
            return;
        }

        String encodePassword = passwordEncoder.encode(password);
        IamUserUpdatePasswordInputDto inputDto = new IamUserUpdatePasswordInputDto();
        inputDto.setUserId(AppContext.getContext().getUserId());
        inputDto.setPassword(encodePassword);
        userClient.updatePassword(inputDto);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernameAuthenticationToken.class);
    }

    /**
     * 校验验证码逻辑
     */
    private void validateCaptcha(String captcha,
                                 String userKey) {
        if (StringUtils.isBlank(captcha) || StringUtils.isBlank(userKey)) {
            customerRedisCommands.del(userKey);
            throw new CaptchaWrongAuthenticationException("您输入的验证码有误，请检查后再试");
        }
        if (!captcha.equals(customerRedisCommands.get(userKey))) {
            customerRedisCommands.del(userKey);
            throw new CaptchaWrongAuthenticationException("您输入的验证码有误，请检查后再试");
        }
        customerRedisCommands.del(userKey);
    }
}

