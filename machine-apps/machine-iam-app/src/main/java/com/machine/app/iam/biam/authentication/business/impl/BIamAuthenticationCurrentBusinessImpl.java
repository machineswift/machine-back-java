package com.machine.app.iam.biam.authentication.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.app.iam.biam.authentication.business.IBIamAuthenticationCurrentBusiness;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationChangePasswordRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCurrentUserFunctionPermissionResponseVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCurrentUserResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogCreateInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserUpdatePasswordInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserAuthDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.biam.auth.BIamAuthActionEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthMethodEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuthResultEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.redis.cache.biam.RedisBIamFunctionPermissionCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import com.machine.starter.security.util.MachineLoginLogUtil;
import com.machine.starter.security.util.MachineJwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.sdk.base.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_UPDATE_PASSWORD;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth.BIAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth.BIAM_AUTH_TOKEN_ID;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.User.BIAM_USER_BASE_KEY;
import static com.machine.starter.security.config.SecurityConstant.BEARER_TYPE;
import static com.machine.starter.security.util.MachineLoginLogUtil.blackAllAvailableToken;

@Slf4j
@Component
public class BIamAuthenticationCurrentBusinessImpl implements IBIamAuthenticationCurrentBusiness {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisBIamFunctionPermissionCache redisIamFunctionPermissionCache;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IBIamUserLoginLogClient loginLogClient;

    @Override
    public void changePassword(BIamAuthenticationChangePasswordRequestVo request) {
        BIamUserDto iamUserDto = userClient.getByUserId(AppContextHolder.getContext().getUserId());
        // 验证旧密码
        if (!passwordEncoder.matches(request.getOldPassword(), iamUserDto.getPassword())) {
            throw new InvalidParameterException("旧密码不正确");
        }
        userClient.updatePassword(new BIamUserUpdatePasswordInputDto(iamUserDto.getUserId(),
                passwordEncoder.encode(request.getNewPassword())));

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4SelfUpdatePassword(servletRequest);
    }

    @Override
    @SneakyThrows
    public void changePasswordSmsCaptcha(BIamAuthSmsCaptchaChangePasswordRequestVo request) {
        //验证手机号是否存在
        BIamUserDto iamUserDto = userClient.getByPhone(request.getPhone());
        if (null == iamUserDto) {
            throw new BIamBusinessException("iam.auth.business.changePasswordSmsCaptcha.phoneNotFound",
                    "您的手机号当前无权限登录，请检查账号是否正确或联系客服");
        }

        //验证用户状态
        if (!iamUserDto.isEnabled()) {
            throw new BIamBusinessException("iam.auth.business.changePasswordSmsCaptcha.userStatusDisable",
                    "您的账号已被禁用，请联系客服了解详情");
        }

        RLock lock = redissonClient.getLock(LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_UPDATE_PASSWORD + request.getPhone());
        try {
            lock.lock();

            //从redis获取验证码
            String userKey = BIAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD + request.getPhone();
            String redisCaptcha = customerRedisCommands.get(userKey);
            if (!request.getCaptcha().equals(redisCaptcha)) {
                //防止暴力破解验证码
                Thread.sleep(200L);
                throw new BIamBusinessException("iam.auth.business.changePasswordSmsCaptcha.wrongCaptcha",
                        "您输入的验证码有误，请检查后再试");
            } else {
                customerRedisCommands.del(userKey);
            }
        } finally {
            lock.unlock();
        }

        AppContextHolder.getContext().setUserId(iamUserDto.getUserId());
        userClient.updatePassword(new BIamUserUpdatePasswordInputDto(iamUserDto.getUserId(),
                passwordEncoder.encode(request.getNewPassword())));

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4SelfUpdatePasswordPhoneCaptcha(servletRequest);
    }

    @Override
    public BIamAuthenticationCurrentUserResponseVo userInfo() {
        String userId = AppContextHolder.getContext().getUserId();

        String value = customerRedisCommands.get(BIAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (EMPTY_OBJECT.equals(value)) {
                return null;
            }
            return JSONUtil.toBean(value, BIamAuthenticationCurrentUserResponseVo.class);
        }

        BIamUserDetailOutputDto outputDto = userClient.detail(new IdRequest(userId));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamAuthenticationCurrentUserResponseVo.class);
    }

    @Override
    public BIamAuthenticationCurrentUserFunctionPermissionResponseVo functionPermission() {
        BIamUserAuthDetailOutputDto outputDto = redisIamFunctionPermissionCache.functionPermission();
        return new BIamAuthenticationCurrentUserFunctionPermissionResponseVo(outputDto.getRoleCodeList(), outputDto.getPermissionCodeList());
    }

    /**
     * 用户修改自己密码记录日志，并失效所有token
     */
    private void blackAuthToken4SelfUpdatePassword(HttpServletRequest request) {
        String userId = AppContextHolder.getContext().getUserId();
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        Jwt claimHeader = machineJwtUtil.getClaimsByToken(accessToken.substring(BEARER_TYPE.length() + 1));
        String accessTokenId = claimHeader.getId();

        Duration ttl = Duration.between(Instant.now(), claimHeader.getExpiresAt());
        long ttlSeconds = Math.max(1, ttl.getSeconds());
        customerRedisCommands.setex(BIAM_AUTH_TOKEN_ID + accessTokenId,
                claimHeader.getClaim(USER_ID_KEY).toString(),ttlSeconds);

        BIamUserLoginLogDetailOutputDto detailOutputDto = loginLogClient.getLoginSuccessByAccessTokenId(accessTokenId);
        List<String> hasProcessLoginLogList = blackAllAvailableToken(userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        BIamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        BIamUserLoginLogCreateInputDto inputDto = MachineLoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(BIamAuthActionEnum.USER_CHANGE_PASSWORD);
        inputDto.setAuthMethod(detailOutputDto != null ? detailOutputDto.getAuthMethod() : null);
        inputDto.setAuthResult(BIamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setAccessTokenExpire(detailOutputDto != null ? detailOutputDto.getAccessTokenExpire() : null);

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        MachineLoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }

    /**
     * 用户修改自己密码记录日志，并失效所有token
     */
    private void blackAuthToken4SelfUpdatePasswordPhoneCaptcha(HttpServletRequest request) {
        String userId = AppContextHolder.getContext().getUserId();

        List<String> hasProcessLoginLogList = blackAllAvailableToken(userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        BIamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        BIamUserLoginLogCreateInputDto inputDto = MachineLoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(BIamAuthActionEnum.PHONE_CAPTCHA_CHANGE_PASSWORD);
        inputDto.setAuthMethod(BIamAuthMethodEnum.NULL);
        inputDto.setAuthResult(BIamAuthResultEnum.SUCCESS);

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        MachineLoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }
}
