package com.machine.app.iam.auth.business;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IIamAuth2Business {
    void renderGitee(HttpServletResponse response);

    void callbackGitee(HttpServletRequest request,
                       HttpServletResponse response);

    void renderFeiShu(HttpServletResponse response);

    void callbackFeiShu(HttpServletRequest request,
                        HttpServletResponse response);

}
