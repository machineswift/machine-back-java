package com.machine.app.admin.scm.attribute.business;

import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupQueryListRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupQueryPageRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateAttributeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeGroupUpdateStatusRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupDetailResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupRelationListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

import java.util.List;

public interface IScmAttributeGroupBusiness {

    String create(ScmAttributeGroupCreateRequestVo request);

    void deleteById(IdRequest request);

    void update(ScmAttributeGroupUpdateRequestVo request);

    void updateStatus(ScmAttributeGroupUpdateStatusRequestVo request);

    void updateCode(ScmAttributeGroupUpdateCodeRequestVo request);

    void updateBackCategory(ScmAttributeGroupUpdateBackCategoryRequestVo request);

    void updateAttribute(ScmAttributeGroupUpdateAttributeRequestVo request);

    ScmAttributeGroupDetailResponseVo getById(IdRequest request);

    List<ScmAttributeGroupRelationListResponseVo> listSimple(ScmAttributeGroupQueryListRequestVo request);

    PageResponse<ScmAttributeGroupSimpleListResponseVo> pageSimple(ScmAttributeGroupQueryPageRequestVo request);

    PageResponse<ScmAttributeGroupListResponseVo> pageExpand(ScmAttributeGroupQueryPageRequestVo request);

}
