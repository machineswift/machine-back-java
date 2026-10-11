package com.machine.app.admin.scm.attribute.business;

import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeQueryPageRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateAttributeGroupRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeDetailResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

public interface IScmAttributeBusiness {

    String create(ScmAttributeCreateRequestVo request);

    void deleteById(IdRequest request);

    void update(ScmAttributeUpdateRequestVo request);

    void updateCode(ScmAttributeUpdateCodeRequestVo request);

    void updateAttributeGroup(ScmAttributeUpdateAttributeGroupRequestVo request);

    void updateBackCategory(ScmAttributeUpdateBackCategoryRequestVo request);

    ScmAttributeDetailResponseVo getById(IdRequest request);

    PageResponse<ScmAttributeSimpleListResponseVo> pageSimple(ScmAttributeQueryPageRequestVo request);

    PageResponse<ScmAttributeListResponseVo> pageExpand(ScmAttributeQueryPageRequestVo request);

}
