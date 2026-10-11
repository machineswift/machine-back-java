package com.machine.app.admin.scm.attribute.business;

import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueListByAttributeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateStatusRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeValueListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeValueSimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmAttributeValueBusiness {

    String create(ScmAttributeValueCreateRequestVo request);

    void deleteById(IdRequest request);

    void update(ScmAttributeValueUpdateRequestVo request);

    void updateStatus(ScmAttributeValueUpdateStatusRequestVo request);

    void updateCode(ScmAttributeValueUpdateCodeRequestVo request);

    List<ScmAttributeValueSimpleListResponseVo> listSimple(ScmAttributeValueListByAttributeRequestVo request);

    List<ScmAttributeValueListResponseVo> listExpand(ScmAttributeValueListByAttributeRequestVo request);
}
