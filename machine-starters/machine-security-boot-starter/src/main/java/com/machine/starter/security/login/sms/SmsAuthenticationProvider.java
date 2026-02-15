package com.machine.starter.security.login.sms;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.exception.iam.authentication.UserStatusDisableException;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.CustomerUserDetailsService;
import com.machine.starter.security.exception.CaptchaWrongAuthenticationException;
import com.machine.starter.security.exception.PhoneNotFoundException;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_SUBMIT;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN;

@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户提交的手机号 + 验证码
        String phone = (String) authentication.getPrincipal();
        String captcha = (String) authentication.getCredentials();

        // 校验验证码
        validate(phone, captcha);

        // 查数据库，匹配用户信息
        IamUserDto iamUserDto = userDetailsService.loadUserByPhone(phone);
        if (null == iamUserDto) {
            throw new PhoneNotFoundException(phone);
        }

        AppContext appContext = AppContext.getContext();
        appContext.setUserId(iamUserDto.getUserId());
        appContext.setAuthMethod(IamAuthMethodEnum.PHONE_CAPTCHA);

        if (!iamUserDto.isEnabled()) {
            throw new UserStatusDisableException("您的账号已被禁用，请联系客服了解详情");
        }

        SmsAuthenticationToken token = new SmsAuthenticationToken();
        token.setAuthenticated(true);
        token.setUsername(iamUserDto.getUsername());
        return token;
    }

    @SneakyThrows
    private void validate(String phone,
                          String captcha) {
        if (StrUtil.isBlank(phone)) {
            throw new PhoneNotFoundException(phone);
        }

        if (StrUtil.isBlank(captcha)) {
            throw new CaptchaWrongAuthenticationException("您输入的验证码有误，请检查后再试");
        }

        RLock lock = redissonClient.getLock(LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_SUBMIT + phone);
        try {
            lock.lock();

            //从redis获取验证码
            String userKey = IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN + phone;
            String redisCaptcha = customerRedisCommands.get(userKey);
            if (!captcha.equals(redisCaptcha)) {
                //防止暴力破解验证码
                Thread.sleep(200L);
                throw new CaptchaWrongAuthenticationException("您输入的验证码有误，请检查后再试");
            } else {
                customerRedisCommands.del(userKey);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

}