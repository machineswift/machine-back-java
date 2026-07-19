package com.machine.app.admin.ai.resource.model.business;

import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelCreateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelQueryPageRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelUpdateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelUpdateStatusRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelDetailResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelExpandListResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IAiResourceModelBusiness {

    String create(AiResourceModelCreateRequestVo request);

    void delete(IdRequest request);

    void update(AiResourceModelUpdateRequestVo request);

    void updateStatus(AiResourceModelUpdateStatusRequestVo request);

    AiResourceModelDetailResponseVo detail(IdRequest request);

    PageResponse<AiResourceModelSimpleListResponseVo> pageSimple(AiResourceModelQueryPageRequestVo request);

    PageResponse<AiResourceModelExpandListResponseVo> pageExpand(AiResourceModelQueryPageRequestVo request);
}
