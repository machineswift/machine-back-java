package com.machine.app.iam.auth.business;

import com.machine.app.iam.auth.controller.vo.request.IamFunctionPermission4MiniRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamUserChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamUserSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserInfoResponseVo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IIamCurrentBusiness {

    void changePassword(IamUserChangePasswordRequestVo request);

    void changePasswordSmsCaptcha(IamUserSmsCaptchaChangePasswordRequestVo request);

    IamAuthCurrentUserInfoResponseVo userInfo();

    List<String> functionPermission();

    List<String> functionPermission4App(IamFunctionPermission4MiniRequestVo request);

}
