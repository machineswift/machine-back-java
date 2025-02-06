package com.machine.app.iam.auth.business;

import com.machine.app.iam.auth.controller.vo.request.IamAuthCreateClientRequestVo;
import jakarta.servlet.http.HttpServletResponse;

public interface IIamAuth2Business {
    void gitee(HttpServletResponse response);

    String createClient(IamAuthCreateClientRequestVo request);

    void feishu(HttpServletResponse response);
}
