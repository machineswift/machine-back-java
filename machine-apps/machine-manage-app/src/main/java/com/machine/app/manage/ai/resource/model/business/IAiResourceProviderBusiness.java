package com.machine.app.manage.ai.resource.model.business;

import com.machine.app.manage.ai.resource.model.controller.vo.request.AiResourceProviderCreateRequestVo;
import com.machine.app.manage.ai.resource.model.controller.vo.request.AiResourceProviderListRequestVo;
import com.machine.app.manage.ai.resource.model.controller.vo.request.AiResourceProviderUpdateRequestVo;
import com.machine.app.manage.ai.resource.model.controller.vo.request.AiResourceProviderUpdateStatusRequestVo;
import com.machine.app.manage.ai.resource.model.controller.vo.response.AiResourceProviderDetailResponseVo;
import com.machine.app.manage.ai.resource.model.controller.vo.response.AiResourceProviderExpandListResponseVo;
import com.machine.app.manage.ai.resource.model.controller.vo.response.AiResourceProviderSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IAiResourceProviderBusiness {

    String create(AiResourceProviderCreateRequestVo request);

    void delete(IdRequest request);

    void update(AiResourceProviderUpdateRequestVo request);

    void updateStatus(AiResourceProviderUpdateStatusRequestVo request);

    AiResourceProviderDetailResponseVo detail(IdRequest request);

    List<AiResourceProviderSimpleListResponseVo> listSimple(AiResourceProviderListRequestVo request);
    
    List<AiResourceProviderExpandListResponseVo> listExpanded(AiResourceProviderListRequestVo request);

}
