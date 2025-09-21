package com.machine.app.iam.auth.business;

import com.machine.app.iam.auth.controller.vo.request.IamAuthSmsCaptchaRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCaptchaResponseVo;
import com.machine.starter.security.login.IamAuthLoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IIamAuthBusiness {

    IamAuthCaptchaResponseVo getCaptcha();

    void smsCaptchaPhoneLogin(IamAuthSmsCaptchaRequestVo request);

    void smsCaptchaForgetPassword(IamAuthSmsCaptchaRequestVo request);

    IamAuthLoginResponse accessToken(HttpServletRequest request);
}
