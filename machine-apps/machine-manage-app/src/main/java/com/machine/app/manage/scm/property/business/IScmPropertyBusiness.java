package com.machine.app.manage.scm.property.business;

import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyCreateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyQueryPageRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyUpdateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyDetailResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyListResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertySimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IScmPropertyBusiness {

    String create(ScmPropertyCreateRequestVo request);

    void update(ScmPropertyUpdateRequestVo request);

    void deleteById(IdRequest request);

    ScmPropertyDetailResponseVo getById(IdRequest request);

    PageResponse<ScmPropertySimpleListResponseVo> pageSimple(ScmPropertyQueryPageRequestVo request);

    PageResponse<ScmPropertyListResponseVo> pageExpand(ScmPropertyQueryPageRequestVo request);

}
