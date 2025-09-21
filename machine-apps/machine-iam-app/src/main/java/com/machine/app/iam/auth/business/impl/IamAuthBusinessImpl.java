package com.machine.app.iam.auth.business.impl;

import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import com.machine.app.iam.auth.business.IIamAuthBusiness;
import com.machine.app.iam.auth.controller.vo.request.IamAuthSmsCaptchaRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCaptchaResponseVo;
import com.machine.client.data.config.IDataIamConfigClient;
import com.machine.client.data.sms.IDataSmsClient;
import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsForgetPasswordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsPhoneLoginInputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogCreateInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.sms.DataSmsCategoryEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsIamAuthActionEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsSendResultEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthActionEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthMethodEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuthResultEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.exception.iam.authentication.AuthTokenUseException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.StringUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import com.machine.starter.security.SecurityConstant;
import com.machine.starter.security.login.IamAuthLoginResponse;
import com.machine.starter.security.util.MachineJwtUtil;
import com.machine.starter.security.util.LoginLogUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.machine.sdk.common.constant.ContextConstant.USER_ID_KEY;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_GET_CAPTCHA;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_GET_CAPTCHA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Auth.*;
import static com.machine.starter.security.SecurityConstant.*;

@Slf4j
@Component
public class IamAuthBusinessImpl implements IIamAuthBusiness {

    /**
     * 验证码有效期为5分钟
     */
    public static final long CAPTCHA_EXPIRATION_TIME = 300;
    public static final long CAPTCHA_LOCK_TIME = 60;

    @Autowired
    private Producer producer;

    @Autowired
    private MachineJwtUtil machineJwtUtil;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataSmsClient dataSmsClient;

    @Autowired
    private IIamUserLoginLogClient loginLogClient;

    @Autowired
    private IDataIamConfigClient dataIamConfigClient;

    @Override
    @SneakyThrows
    public IamAuthCaptchaResponseVo getCaptcha() {
        //获取验证码
        String captchaCode = producer.createText();
        BufferedImage image = producer.createImage(captchaCode);

        //转化为字节数组
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        byte[] imageBytes = bos.toByteArray();

        String userKey = IAM_AUTH_PIC_CAPTCHA + UUID.randomUUID().toString().replace("-", "");
        customerRedisCommands.set(userKey, captchaCode, CAPTCHA_EXPIRATION_TIME);
        log.info("获取验证码,userKey:{} captcha:{}", userKey, captchaCode);
        return new IamAuthCaptchaResponseVo(userKey, "data:image/png;base64," + Base64Encoder.encode(imageBytes));
    }

    @Override
    public void smsCaptchaPhoneLogin(IamAuthSmsCaptchaRequestVo request) {
        //验证手机号是否存在
        IamUserDto iamUserDto = userClient.getByPhone(request.getPhone());
        if (null == iamUserDto) {
            throw new IamBusinessException("iam.auth.business.smsCaptchaPhoneLogin.phoneNotFound",
                    "您的手机号当前无权限登录，请检查账号是否正确或联系客服");
        }

        //验证用户状态
        if (!iamUserDto.isEnabled()) {
            throw new IamBusinessException("iam.auth.business.smsCaptchaPhoneLogin.userStatusDisable",
                    "您的账号已被禁用，请联系客服了解详情");
        }

        String userKey = IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN + request.getPhone();
        RLock lock = redissonClient.getLock(LOCK_IAM_AUTH_SMS_CAPTCHA_PHONE_LOGIN_GET_CAPTCHA + request.getPhone());
        try {
            lock.lock();

            if (StrUtil.isNotBlank(customerRedisCommands.get(userKey))) {
                //验证时间是否过了一分钟
                Long ttl = customerRedisCommands.ttl(userKey);
                if (null != ttl && (ttl.compareTo(CAPTCHA_EXPIRATION_TIME - CAPTCHA_LOCK_TIME) > 0)) {
                    throw new IamBusinessException("iam.auth.business.smsCaptchaPhoneLogin.captchaAlreadyExistsOneMinute",
                            "验证码请求过于频繁，请稍候再试");
                }
            }
        } finally {
            lock.unlock();
        }

        AppContext.getContext().setUserId(iamUserDto.getUserId());

        //验证发送次数
        DataSmsCountRecordInputDto captchaRecordInputDto = new DataSmsCountRecordInputDto();
        captchaRecordInputDto.setCategory(DataSmsCategoryEnum.IAM_AUTH);
        captchaRecordInputDto.setCode(DataSmsIamAuthActionEnum.PHONE_CAPTCHA_LOGIN.getName());
        captchaRecordInputDto.setResult(DataSmsSendResultEnum.SUCCESS);
        captchaRecordInputDto.setPhone(request.getPhone());
        // 获取当天开始时间，格式为yyyy-MM-dd 00:00:00
        captchaRecordInputDto.setStartTime(DateUtil.beginOfDay(DateUtil.date()).getTime());
        int count = dataSmsClient.countByCondition(captchaRecordInputDto);
        if (count >= dataIamConfigClient.smsCaptchaPhoneLoginLimit()) {
            throw new IamBusinessException("iam.auth.business.smsCaptchaPhoneLogin.userStatusDisable",
                    "您今天的手机号验证次数已用完。如需继续，请尝试其他验证方式或明日再试。如有疑问，请联系客服");
        }

        //获取验证码
        String captchaCode = StringUtil.verificationCode();

        //发送短信
        DataSmsPhoneLoginInputDto phoneLoginInputDto = new DataSmsPhoneLoginInputDto();
        phoneLoginInputDto.setTo(request.getPhone());
        phoneLoginInputDto.setCaptcha(captchaCode);
        dataSmsClient.send4PhoneLogin(phoneLoginInputDto);

        //存储到redis
        customerRedisCommands.del(userKey);
        customerRedisCommands.set(userKey, captchaCode, CAPTCHA_EXPIRATION_TIME);
    }

    @Override
    public void smsCaptchaForgetPassword(IamAuthSmsCaptchaRequestVo request) {
        //验证手机号是否存在
        IamUserDto iamUserDto = userClient.getByPhone(request.getPhone());
        if (null == iamUserDto) {
            throw new IamBusinessException("iam.auth.business.smsCaptchaForgetPassword.phoneNotFound",
                    "您的手机号当前无权限登录，请检查账号是否正确或联系客服");
        }

        //验证用户状态
        if (!iamUserDto.isEnabled()) {
            throw new IamBusinessException("iam.auth.business.smsCaptchaForgetPassword.userStatusDisable",
                    "您的账号已被禁用，请联系客服了解详情");
        }

        String userKey = IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD + request.getPhone();
        RLock lock = redissonClient.getLock(LOCK_IAM_AUTH_SMS_CAPTCHA_FORGET_PASSWORD_GET_CAPTCHA + request.getPhone());
        try {
            lock.lock();

            if (StrUtil.isNotBlank(customerRedisCommands.get(userKey))) {
                //验证时间是否过了一分钟
                Long ttl = customerRedisCommands.ttl(userKey);
                if (null != ttl && (ttl.compareTo(CAPTCHA_EXPIRATION_TIME - CAPTCHA_LOCK_TIME) > 0)) {
                    throw new IamBusinessException("iam.auth.business.smsCaptchaForgetPassword.captchaAlreadyExistsOneMinute",
                            "验证码请求过于频繁，请稍候再试");
                }
            }
        } finally {
            lock.unlock();
        }

        AppContext.getContext().setUserId(iamUserDto.getUserId());

        //验证发送次数
        DataSmsCountRecordInputDto captchaRecordInputDto = new DataSmsCountRecordInputDto();
        captchaRecordInputDto.setCategory(DataSmsCategoryEnum.IAM_AUTH);
        captchaRecordInputDto.setCode(DataSmsIamAuthActionEnum.USER_FORGET_PASSWORD.getName());
        captchaRecordInputDto.setResult(DataSmsSendResultEnum.SUCCESS);
        captchaRecordInputDto.setPhone(request.getPhone());
        // 获取当天开始时间，格式为yyyy-MM-dd 00:00:00
        captchaRecordInputDto.setStartTime(DateUtil.beginOfDay(DateUtil.date()).getTime());
        int count = dataSmsClient.countByCondition(captchaRecordInputDto);
        if (count >= dataIamConfigClient.smsCaptchaForgetPasswordLimit()) {
            throw new IamBusinessException("iam.auth.business.smsCaptchaForgetPassword.userStatusDisable",
                    "您今天的手机号验证次数已用完。如需继续，请尝试其他验证方式或明日再试。如有疑问，请联系客服");
        }

        //获取验证码
        String captchaCode = StringUtil.verificationCode();

        //发送短信
        DataSmsForgetPasswordInputDto forgetPasswordInputDto = new DataSmsForgetPasswordInputDto();
        forgetPasswordInputDto.setTo(request.getPhone());
        forgetPasswordInputDto.setCaptcha(captchaCode);
        dataSmsClient.send4ForgetPassword(forgetPasswordInputDto);

        //存储到redis
        customerRedisCommands.del(userKey);
        customerRedisCommands.set(userKey, captchaCode, CAPTCHA_EXPIRATION_TIME);
    }

    @Override
    public IamAuthLoginResponse accessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(AUTH_TOKEN_HEADER_KEY).substring(SecurityConstant.BEARER_TYPE.length() + 1);
        Claims claimFromToken = machineJwtUtil.getClaimsByToken(refreshToken);
        if (null == claimFromToken.get(AUTH_TOKEN_REFRESH_TOKEN_KEY)) {
            throw new AuthTokenUseException("AccessToken不能用于换取Token");
        }

        String accessTokenId = UUID.randomUUID().toString().replaceAll("-", "");
        long accessTokenExpire = System.currentTimeMillis() + AUTH_TOKEN_EXPIRE_TIMESTAMP;
        Map<String, Object> claimMap4AuthToken = new HashMap<>();
        claimMap4AuthToken.put(AUTH_TOKEN_ACCESS_TOKEN_ID_KEY, accessTokenId);
        claimMap4AuthToken.put(USER_ID_KEY, claimFromToken.get(USER_ID_KEY, String.class));

        //生成 jwt token
        String accessToken = machineJwtUtil.generateToken(
                claimFromToken.getSubject(),
                claimMap4AuthToken,
                accessTokenExpire);

        //新增登录成功日志
        IamUserDetailOutputDto userSimple = userClient.detail(new IdRequest(AppContext.getContext().getUserId()));
        IamUserLoginLogCreateInputDto inputDto = LoginLogUtil.getUserLoginLogCreateInputDto(userSimple);
        inputDto.setAuthAction(IamAuthActionEnum.REFRESH_TOKEN);
        inputDto.setAuthMethod(IamAuthMethodEnum.REFRESH_TOKEN);
        inputDto.setAuthResult(IamAuthResultEnum.SUCCESS);
        inputDto.setAccessTokenId(accessTokenId);
        inputDto.setRefreshTokenId(claimFromToken.getId());
        inputDto.setAccessTokenExpire(accessTokenExpire);
        inputDto.setRefreshTokenExpire(claimFromToken.getExpiration().getTime());
        inputDto.setAccessToken(accessToken);
        inputDto.setRefreshToken(refreshToken);
        LoginLogUtil.setUserAgentInfo(request, inputDto);
        loginLogClient.create(inputDto);

        return new IamAuthLoginResponse(accessToken, accessTokenExpire);
    }

    /**
     * 线段干扰的验证码
     */
    public void getCaptchaImage(HttpServletResponse response) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100);
        System.out.println("验证码：" + captcha.getCode());

        // 设置响应类型为图片
        response.setContentType("image/png");

        // 将验证码图片写入响应
        captcha.write(response.getOutputStream());
    }

    /**
     * 圆圈干扰验证码OException
     */
    public void getCaptchaImage2(HttpServletResponse response) throws IOException {
        // 创建验证码对象
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
        System.out.println("验证码：" + captcha.getCode());

        // 设置响应类型为图片
        response.setContentType("image/png");

        // 将验证码图片写入响应
        captcha.write(response.getOutputStream());
    }

    /**
     * 扭曲干扰验证码
     */
    public void getCaptchaImage3(HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        System.out.println("验证码：" + captcha.getCode());

        // 设置响应类型为图片
        response.setContentType("image/png");

        // 将验证码图片写入响应
        captcha.write(response.getOutputStream());
    }


    /**
     * 自定义纯数字的验证码
     */
    public void getCaptchaImage4(HttpServletResponse response) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰线宽度
        // 自定义纯数字的验证码（随机4位数字，可重复）
        RandomGenerator randomGenerator = new RandomGenerator("0123456789", 4);
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        lineCaptcha.setGenerator(randomGenerator);
        // 重新生成code
        lineCaptcha.createCode();
        // 设置响应类型为图片
        response.setContentType("image/png");
        // 将验证码图片写入响应
        lineCaptcha.write(response.getOutputStream());
    }

    /**
     * 加减乘除的验证码
     */
    public void getCaptchaImage5(HttpServletResponse response) throws IOException {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 45, 4, 4);
        // 自定义验证码内容为四则运算方式
        captcha.setGenerator(new MathGenerator(1));
        // 重新生成code
        captcha.createCode();
        MathGenerator mathGenerator = new MathGenerator();

        //用户输入校验
        System.out.println("验证结果：" + mathGenerator.verify(captcha.getCode(), "1"));

        // 设置响应类型为图片
        response.setContentType("image/png");
        // 将验证码图片写入响应
        captcha.write(response.getOutputStream());
    }

}
