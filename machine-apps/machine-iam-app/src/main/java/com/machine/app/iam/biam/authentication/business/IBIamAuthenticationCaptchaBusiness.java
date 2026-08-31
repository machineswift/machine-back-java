package com.machine.app.iam.biam.authentication.business;

import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationAccessTokenRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationSmsCaptchaRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCaptchaResponseVo;
import com.machine.starter.security.service.model.MachineAuthenticationResult;

public interface IBIamAuthenticationCaptchaBusiness {

    BIamAuthenticationCaptchaResponseVo getCaptcha();

    void smsCaptchaPhoneLogin(BIamAuthenticationSmsCaptchaRequestVo request);

    void smsCaptchaForgetPassword(BIamAuthenticationSmsCaptchaRequestVo request);

    MachineAuthenticationResult accessToken(BIamAuthenticationAccessTokenRequestVo request);
}
