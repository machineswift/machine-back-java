package com.machine.app.iam.biam.user.business;

import com.machine.app.iam.biam.user.controller.vo.request.BIamUserConfigGetRequestVo;
import com.machine.app.iam.biam.user.controller.vo.request.BIamUserConfigSaveRequestVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserConfigResponseVo;

public interface IBIamUserConfigBusiness {

    void save(BIamUserConfigSaveRequestVo request);

    BIamUserConfigResponseVo getByKey(BIamUserConfigGetRequestVo request);

}
