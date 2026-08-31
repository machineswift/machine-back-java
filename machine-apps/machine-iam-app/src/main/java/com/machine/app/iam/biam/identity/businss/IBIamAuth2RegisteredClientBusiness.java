package com.machine.app.iam.biam.identity.businss;

import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientCreateRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientPageQueryRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientUpdateRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientUpdateStatusRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.response.BIamAuth2RegisteredClientDetailResponseVo;
import com.machine.app.iam.biam.identity.controller.vo.response.BIamAuth2RegisteredClientListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IBIamAuth2RegisteredClientBusiness {

    void cleanCache();

    String create(BIamAuth2RegisteredClientCreateRequestVo request);

    void update(BIamAuth2RegisteredClientUpdateRequestVo request);

    void updateStatus(BIamAuth2RegisteredClientUpdateStatusRequestVo request);

    void delete(IdRequest request);

    BIamAuth2RegisteredClientDetailResponseVo detail(IdRequest request);

    PageResponse<BIamAuth2RegisteredClientListResponseVo> pageExpand(BIamAuth2RegisteredClientPageQueryRequestVo query);

}
