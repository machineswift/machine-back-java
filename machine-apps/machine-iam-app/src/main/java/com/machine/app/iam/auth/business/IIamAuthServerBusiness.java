package com.machine.app.iam.auth.business;

import com.machine.app.iam.auth.controller.vo.request.IamAuthCreateClientRequestVo;

public interface IIamAuthServerBusiness {
    String createClient(IamAuthCreateClientRequestVo request);
}
