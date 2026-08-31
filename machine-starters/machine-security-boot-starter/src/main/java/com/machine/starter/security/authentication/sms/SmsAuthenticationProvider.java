package com.machine.starter.security.authentication.sms;

import cn.hutool.core.util.StrUtil;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.biam.auth.BIamAuthMethodEnum;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.service.MachineUserDetailsService;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import static com.machine.sdk.base.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_SUBMIT;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth.BIAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN;

@Component
public class SmsAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private MachineUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户提交的手机号 + 验证码
        String phone = (String) authentication.getPrincipal();
        String captcha = (String) authentication.getCredentials();

        // 校验验证码
        validate(phone, captcha);

        // 查数据库，匹配用户信息
        BIamUserDto iamUserDto = userDetailsService.loadUserByPhone(phone);
        if (null == iamUserDto) {
            throw new BadCredentialsException("您的手机号当前无权限登录，请检查账号是否正确或联系客服");
        }

        AppContextHolder appContextHolder = AppContextHolder.getContext();
        appContextHolder.setUserId(iamUserDto.getUserId());
        MDC.put(USER_ID_KEY, AppContextHolder.getContext().getUserId());
        appContextHolder.setAuthMethod(BIamAuthMethodEnum.PHONE_CAPTCHA);

        if (!iamUserDto.isEnabled()) {
            throw new BadCredentialsException("您的账号已被禁用，请联系客服了解详情");
        }

        SmsAuthenticationToken token = new SmsAuthenticationToken();
        token.setUsername(iamUserDto.getUsername());
        token.setAuthenticated(true);
        return token;
    }

    @SneakyThrows
    private void validate(String phone,
                          String captcha) {
        if (StrUtil.isBlank(phone)) {
            throw new BadCredentialsException("您输入的手机号有误，请检查后再试");
        }

        if (StrUtil.isBlank(captcha)) {
            throw new BadCredentialsException("您输入的验证码有误，请检查后再试");
        }

        RLock lock = redissonClient.getLock(LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_SUBMIT + phone);
        try {
            lock.lock();

            //从redis获取验证码
            String userKey = BIAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN + phone;
            String redisCaptcha = customerRedisCommands.get(userKey);
            if (!captcha.equals(redisCaptcha)) {
                //防止暴力破解验证码
                Thread.sleep(200L);
                throw new BadCredentialsException("您输入的验证码有误，请检查后再试");
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