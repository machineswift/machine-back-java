package com.machine.app.iam.biam.authentication.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IBIamAuthenticationThirdBusiness {
    void renderGitee(HttpServletResponse response);

    void callbackGitee(HttpServletRequest request,
                       HttpServletResponse response);

    void renderFeiShu(HttpServletResponse response);

    void callbackFeiShu(HttpServletRequest request,
                        HttpServletResponse response);

}
