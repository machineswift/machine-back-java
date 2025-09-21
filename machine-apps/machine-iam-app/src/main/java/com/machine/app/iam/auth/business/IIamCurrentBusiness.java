package com.machine.app.iam.auth.business;

import com.machine.app.iam.auth.controller.vo.request.IamAuthChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.request.IamAuthSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserFunctionPermissionResponseVo;
import com.machine.app.iam.auth.controller.vo.response.IamAuthCurrentUserResponseVo;

public interface IIamCurrentBusiness {

    void changePassword(IamAuthChangePasswordRequestVo request);

    void changePasswordSmsCaptcha(IamAuthSmsCaptchaChangePasswordRequestVo request);

    IamAuthCurrentUserResponseVo userInfo();

    IamAuthCurrentUserFunctionPermissionResponseVo functionPermission();
}
