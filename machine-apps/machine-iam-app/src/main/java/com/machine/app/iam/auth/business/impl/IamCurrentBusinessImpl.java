package com.machine.app.iam.auth.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.machine.app.iam.auth.business.IIamCurrentBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamAuthChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamAuthSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserFunctionPermissionResponseVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserResponseVo;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserUpdatePasswordInputDto;
import com.machine.client.iam.user.dto.output.IamUserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.redis.cache.RedisCacheIamFunctionPermission;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.util.LoginLogUtil;
import com.machine.starter.security.util.MachineJwtUtil;
import io.jsonwebtoken.Claims;
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
import java.util.List;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_UPDATE_PASSWORD;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.IAM_AUTH_TOKEN_ID;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.User.IAM_USER_BASE_KEY;
import static com.machine.starter.security.SecurityConstant.AUTH_TOKEN_HEADER_KEY;
import static com.machine.starter.security.SecurityConstant.BEARER_TYPE;
import static com.machine.starter.security.util.LoginLogUtil.blackAllAvailableToken;

@Slf4j
@Component
public class IamCurrentBusinessImpl implements IIamCurrentBusiness {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheIamFunctionPermission redisCacheIamFunctionPermission;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Override
    public void changePassword(IamAuthChangePasswordRequestVo request) {
        IamUserDto iamUserDto = userClient.getByUserId(AppContext.getContext().getUserId());
        // 验证旧密码
        if (!passwordEncoder.matches(request.getOldPassword(), iamUserDto.getPassword())) {
            throw new InvalidParameterException("旧密码不正确");
        }
        userClient.updatePassword(new IamUserUpdatePasswordInputDto(iamUserDto.getUserId(),
                passwordEncoder.encode(request.getNewPassword())));

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4SelfUpdatePassword(servletRequest);
    }

    @Override
    @SneakyThrows
    public void changePasswordSmsCaptcha(IamAuthSmsCaptchaChangePasswordRequestVo request) {
        //验证手机号是否存在
        IamUserDto iamUserDto = userClient.getByPhone(request.getPhone());
        if (null == iamUserDto) {
            throw new IamBusinessException("iam.auth.business.changePasswordSmsCaptcha.phoneNotFound",
                    "您的手机号当前无权限登录，请检查账号是否正确或联系客服");
        }

        //验证用户状态
        if (!iamUserDto.isEnabled()) {
            throw new IamBusinessException("iam.auth.business.changePasswordSmsCaptcha.userStatusDisable",
                    "您的账号已被禁用，请联系客服了解详情");
        }

        RLock lock = redissonClient.getLock(LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_UPDATE_PASSWORD + request.getPhone());
        try {
            lock.lock();

            //从redis获取验证码
            String userKey = IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD + request.getPhone();
            String redisCaptcha = customerRedisCommands.get(userKey);
            if (!request.getCaptcha().equals(redisCaptcha)) {
                //防止暴力破解验证码
                Thread.sleep(200L);
                throw new IamBusinessException("iam.auth.business.changePasswordSmsCaptcha.wrongCaptcha",
                        "您输入的验证码有误，请检查后再试");
            } else {
                customerRedisCommands.del(userKey);
            }
        } finally {
            lock.unlock();
        }

        AppContext.getContext().setUserId(iamUserDto.getUserId());
        userClient.updatePassword(new IamUserUpdatePasswordInputDto(iamUserDto.getUserId(),
                passwordEncoder.encode(request.getNewPassword())));

        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        blackAuthToken4SelfUpdatePasswordPhoneCaptcha(servletRequest);
    }

    @Override
    public IamAuthCurrentUserResponseVo userInfo() {
        String userId = AppContext.getContext().getUserId();

        String value = customerRedisCommands.get(IAM_USER_BASE_KEY + userId);
        if (StrUtil.isNotEmpty(value)) {
            if (EMPTY_OBJECT.equals(value)) {
                return null;
            }
            return JSONUtil.toBean(value, IamAuthCurrentUserResponseVo.class);
        }

        IamUserDetailOutputDto outputDto = userClient.detail(new IdRequest(userId));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamAuthCurrentUserResponseVo.class);
    }

    @Override
    public IamAuthCurrentUserFunctionPermissionResponseVo functionPermission() {
        IamUserAuthDetailOutputDto outputDto = redisCacheIamFunctionPermission.functionPermission();
        return new IamAuthCurrentUserFunctionPermissionResponseVo(outputDto.getRoleCodeList(), outputDto.getPermissionCodeList());
    }

    /**
     * 用户修改自己密码记录日志，并失效所有token
     */
    private void blackAuthToken4SelfUpdatePassword(HttpServletRequest request) {
        String userId = AppContext.getContext().getUserId();
        String accessToken = request.getHeader(AUTH_TOKEN_HEADER_KEY);
        Claims claimHeader = machineJwtUtil.getClaimsByToken(accessToken.substring(BEARER_TYPE.length() + 1));
        String accessTokenId = claimHeader.getId();

        customerRedisCommands.set(IAM_AUTH_TOKEN_ID + accessTokenId,
                claimHeader.get(USER_ID_KEY).toString(),
                (claimHeader.getExpiration().getTime() - System.currentTimeMillis()) / 1000);

        IamUserLoginLogDetailOutputDto detailOutputDto = loginLogClient.getLoginSuccessByAccessTokenId(accessTokenId);
        List<String> hasProcessLoginLogList = blackAllAvailableToken(machineJwtUtil, userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.USER_CHANGE_PASSWORD);
        inputDto.setAuthMethod(detailOutputDto.getAuthMethod());
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setAccessTokenExpire(detailOutputDto.getAccessTokenExpire());
        inputDto.setAccessToken(detailOutputDto.getAccessToken());

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }

    /**
     * 用户修改自己密码记录日志，并失效所有token
     */
    private void blackAuthToken4SelfUpdatePasswordPhoneCaptcha(HttpServletRequest request) {
        String userId = AppContext.getContext().getUserId();

        List<String> hasProcessLoginLogList = blackAllAvailableToken(machineJwtUtil, userId, loginLogClient, customerRedisCommands);

        //新增修改密码日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.PHONE_CAPTCHA_CHANGE_PASSWORD);
        inputDto.setAuthMethod(IamAuthMethodEnum.NULL);
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);

        //记录被联动处理的日志ID
        inputDto.setDescription(JSON.toJSONString(hasProcessLoginLogList));
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
    }
}
