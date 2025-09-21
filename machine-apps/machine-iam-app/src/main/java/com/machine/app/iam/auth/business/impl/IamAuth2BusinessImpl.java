package com.machine.app.iam.auth.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.auth.business.IIamAuth2Business;
import com.machine.client.iam.user.IIamThirdPartyUserClient;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserBindInputDto;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.login.IamAuthLoginResponse;
import com.machine.starter.security.util.LoginLogUtil;
import com.machine.starter.security.util.MachineJwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.security.SecurityConstant.*;
import static com.machine.starter.security.SecurityConstant.AUTH_TOKEN_REFRESH_TOKEN_KEY;

@Slf4j
@Component
@RefreshScope
public class IamAuth2BusinessImpl implements IIamAuth2Business {

    /**
     * 授权携带数据效期为5分钟
     */
    public static final long STATE_EXPIRATION_TIME = 300;

    @Value("${auth2.redirectFrontUri:XXX}")
    private String redirectFrontUri;

    @Value("${auth2.gitee.clientId:XXX}")
    private String giteeClientId;
    @Value("${auth2.gitee.clientSecret:XXX}")
    private String giteeClientSecret;
    @Value("${auth2.gitee.redirectUri:XXX}")
    private String giteeRedirectUri;

    @Value("${auth2.feiShu.clientId:XXX}")
    private String feishuClientId;
    @Value("${auth2.feiShu.clientSecret:XXX}")
    private String feishuClientSecret;
    @Value("${auth2.feiShu.redirectUri:XXX}")
    private String feishuRedirectUri;


    @Autowired
    private MachineJwtUtil machineJwtUtils;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Autowired
    private IIamThirdPartyUserClient thirdPartyUserClient;

    @Override
    public void renderGitee(HttpServletResponse response) {
        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
                .clientId(giteeClientId)
                .clientSecret(giteeClientSecret)
                .redirectUri(giteeRedirectUri)
                .build());

        String state = AuthStateUtils.createState();
        String redirectUrl = authRequest.authorize(state);
        if (StrUtil.isNotBlank(AppContext.getContext().getUserId())) {
            customerRedisCommands.set(state, AppContext.getContext().getUserId(), STATE_EXPIRATION_TIME);
        }
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("auth2.0 码云重定向报错", e);
            throw new IamBusinessException("iam.auth2.business.renderGitee.exception", e.getMessage(), e);
        }
    }

    @Override
    public void callbackGitee(HttpServletRequest request,
                              HttpServletResponse response) {
        AuthCallback callback = new AuthCallback();
        callback.setCode(request.getParameter("code"));
        callback.setState(request.getParameter("state"));

        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
                .clientId(giteeClientId)
                .clientSecret(giteeClientSecret)
                .redirectUri(giteeRedirectUri)
                .build());
        AuthUser authUser = authRequest.login(callback).getData();
        log.info("Auth2.0 码云授权登录返回数据:{}", JSONUtil.toJsonStr(authUser));

        IamUserDto userDto = userClient.getByThirdPartyUuid(IamAuth2SourceEnum.GITEE, authUser.getUuid());
        if (null != userDto) {
            // 处理登录逻辑
            AppContext.getContext().setUserId(userDto.getUserId());
            IamAuthLoginResponse loginResponse = processLogin(IamAuthMethodEnum.AUTH2_GITEE, userDto.getUserId(), userDto.getUsername(), request);
            redirectUrl(loginResponse, null, response);
            return;
        }

        String userId = customerRedisCommands.get(callback.getState());
        if (StrUtil.isBlank(userId)) {
            IamBusinessException businessException = new IamBusinessException("iam.auth2.business.callbackGitee.notBindUser", "该第三方平台账号尚未绑定系统账号，请先绑定");
            redirectUrl(null, businessException, response);
        } else {
            customerRedisCommands.del(callback.getState());
        }

        AppContext.getContext().setUserId(userId);
        userDto = userClient.getByUserId(userId);
        if (null == userDto) {
            IamBusinessException businessException = new IamBusinessException("iam.auth2.business.callbackGitee.userNotExists", "用户不存在，请联系客服");
            redirectUrl(null, businessException, response);
        }

        //创建三方用户并绑定账号
        createAndBingUser(IamAuth2SourceEnum.GITEE, userId, authUser);
    }

    @Override
    public void renderFeiShu(HttpServletResponse response) {
        AuthRequest authRequest = new AuthFeishuRequest(AuthConfig.builder()
                .clientId(feishuClientId)
                .clientSecret(feishuClientSecret)
                .redirectUri(feishuRedirectUri)
                .build());

        String state = AuthStateUtils.createState();
        String redirectUrl = authRequest.authorize(state);
        if (StrUtil.isNotBlank(AppContext.getContext().getUserId())) {
            customerRedisCommands.set(state, AppContext.getContext().getUserId(), STATE_EXPIRATION_TIME);
        }
        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            log.error("auth2.0 飞书重定向报错", e);
            throw new IamBusinessException("iam.auth2.business.renderFeiShu.exception", e.getMessage(), e);
        }
    }

    @Override
    public void callbackFeiShu(HttpServletRequest request, HttpServletResponse response) {
        AuthCallback callback = new AuthCallback();
        callback.setCode(request.getParameter("code"));
        callback.setState(request.getParameter("state"));

        AuthRequest authRequest = new AuthGiteeRequest(AuthConfig.builder()
                .clientId(feishuClientId)
                .clientSecret(feishuClientSecret)
                .redirectUri(feishuRedirectUri)
                .build());
        AuthUser authUser = authRequest.login(callback).getData();
        log.info("Auth2.0 飞书授权登录返回数据:{}", JSONUtil.toJsonStr(authUser));

        IamUserDto userDto = userClient.getByThirdPartyUuid(IamAuth2SourceEnum.FEISHU, authUser.getUuid());
        if (null != userDto) {
            // 处理登录逻辑
            AppContext.getContext().setUserId(userDto.getUserId());
            IamAuthLoginResponse loginResponse = processLogin(IamAuthMethodEnum.AUTH2_FEI_SHU, userDto.getUserId(), userDto.getUsername(), request);
            redirectUrl(loginResponse, null, response);
            return;
        }

        String userId = customerRedisCommands.get(callback.getState());
        if (StrUtil.isBlank(userId)) {
            IamBusinessException businessException = new IamBusinessException("iam.auth2.business.callbackFeiShu.notBindUser", "该第三方平台账号尚未绑定系统账号，请先绑定");
            redirectUrl(null, businessException, response);
        } else {
            customerRedisCommands.del(callback.getState());
        }

        AppContext.getContext().setUserId(userId);
        userDto = userClient.getByUserId(userId);
        if (null == userDto) {
            IamBusinessException businessException = new IamBusinessException("iam.auth2.business.callbackFeiShu.userNotExists", "用户不存在，请联系客服");
            redirectUrl(null, businessException, response);
        }

        //创建三方用户并绑定账号
        createAndBingUser(IamAuth2SourceEnum.FEISHU, userId, authUser);
    }


    private void createAndBingUser(IamAuth2SourceEnum source,
                                   String userId,
                                   AuthUser authUser) {
        IamThirdPartyUserCreateInputDto createInputDto = new IamThirdPartyUserCreateInputDto();
        createInputDto.setSource(source);
        createInputDto.setUuid(authUser.getUuid());
        createInputDto.setUserName(authUser.getUsername());
        createInputDto.setDisplayName(authUser.getNickname());
        createInputDto.setHeadPictureUrl(authUser.getAvatar());
        createInputDto.setContent(JSONUtil.toJsonStr(authUser));
        String thirdPartyUserId = thirdPartyUserClient.create(createInputDto);

        IamThirdPartyUserBindInputDto bindInputDto = new IamThirdPartyUserBindInputDto();
        bindInputDto.setUserId(userId);
        bindInputDto.setThirdPartyUserId(thirdPartyUserId);
        thirdPartyUserClient.bind(bindInputDto);
    }

    @SneakyThrows
    private void redirectUrl(IamAuthLoginResponse loginResponse,
                             IamBusinessException businessException,
                             HttpServletResponse response) {
        String redirectUrl = redirectFrontUri;
        if (null != loginResponse) {
            redirectUrl = redirectUrl + "?access_token=" + URLEncoder.encode(loginResponse.getAccessToken(), StandardCharsets.UTF_8) +
                    "&expires_in=" + loginResponse.getExpiresIn() +
                    "&refresh_token=" + URLEncoder.encode(loginResponse.getRefreshToken(), StandardCharsets.UTF_8) +
                    "&token_type=" + URLEncoder.encode(loginResponse.getTokenType(), StandardCharsets.UTF_8);
        } else {
            redirectUrl = redirectUrl + "?code=" + URLEncoder.encode(businessException.getCode(), StandardCharsets.UTF_8) +
                    "&message=" + URLEncoder.encode(businessException.getMessage(), StandardCharsets.UTF_8);
        }
        response.sendRedirect(redirectUrl);
    }

    private IamAuthLoginResponse processLogin(IamAuthMethodEnum authMethod,
                                              String userId,
                                              String userName,
                                              HttpServletRequest request) {
        // 生成JWT accessToken
        String accessTokenId = UUID.randomUUID().toString().replaceAll("-", "");
        long accessTokenExpire = System.currentTimeMillis() + AUTH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4AuthToken = new HashMap<>();
        claimMap4AuthToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, accessTokenId);
        claimMap4AuthToken.put(USER_ID_KEY, userId);
        String accessToken = machineJwtUtils.generateToken(
                userName,
                claimMap4AuthToken,
                accessTokenExpire);

        //生成JWT refreshToken
        String refreshTokenId = UUID.randomUUID().toString().replaceAll("-", "");
        long refreshTokenExpire = System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4RefreshToken = new HashMap<>();
        claimMap4RefreshToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, refreshTokenId);
        claimMap4RefreshToken.put(USER_ID_KEY, userId);
        claimMap4RefreshToken.put(AUTH_TOKEN_REFRESH_TOKEN_KEY, AUTH_TOKEN_REFRESH_TOKEN_KEY);
        String refreshToken = machineJwtUtils.generateToken(
                userName,
                claimMap4RefreshToken,
                refreshTokenExpire);

        //新增登录成功日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(userId));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.LOGIN);
        inputDto.setAuthMethod(authMethod);
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setRefreshTokenId(refreshTokenId);
        inputDto.setAccessTokenExpire(accessTokenExpire);
        inputDto.setRefreshTokenExpire(refreshTokenExpire);
        inputDto.setAccessToken(accessToken);
        inputDto.setRefreshToken(refreshToken);
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);
        return new IamAuthLoginResponse(accessToken, accessTokenExpire, refreshToken);
    }
}
