package com.machine.app.iam.biam.authentication.business;

import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthenticationChangePasswordRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.request.BIamAuthSmsCaptchaChangePasswordRequestVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCurrentUserFunctionPermissionResponseVo;
import com.machine.app.iam.biam.authentication.controller.vo.response.BIamAuthenticationCurrentUserResponseVo;

public interface IBIamAuthenticationCurrentBusiness {

    void changePassword(BIamAuthenticationChangePasswordRequestVo request);

    void changePasswordSmsCaptcha(BIamAuthSmsCaptchaChangePasswordRequestVo request);

    BIamAuthenticationCurrentUserResponseVo userInfo();

    BIamAuthenticationCurrentUserFunctionPermissionResponseVo functionPermission();
}
