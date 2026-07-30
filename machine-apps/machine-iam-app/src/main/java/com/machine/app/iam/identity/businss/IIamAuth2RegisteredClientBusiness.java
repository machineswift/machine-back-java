package com.machine.app.iam.identity.businss;

import com.machine.app.iam.identity.controller.vo.request.*;
import com.machine.app.iam.identity.controller.vo.response.IamAuth2RegisteredClientDetailResponseVo;
import com.machine.app.iam.identity.controller.vo.response.IamAuth2RegisteredClientListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IIamAuth2RegisteredClientBusiness {

    String create(IamAuth2RegisteredClientCreateRequestVo request);

    void update(IamAuth2RegisteredClientUpdateRequestVo request);

    void updateStatus(IamAuth2RegisteredClientUpdateStatusRequestVo request);

    void delete(IdRequest request);

    IamAuth2RegisteredClientDetailResponseVo detail(IdRequest request);

    PageResponse<IamAuth2RegisteredClientListResponseVo> pageExpand(IamAuth2RegisteredClientPageQueryRequestVo query);

}
