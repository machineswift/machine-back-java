package com.machine.app.admin.scm.property.business;

import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyValueCreateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyValueListByPropertyRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyValueUpdateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyValueListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

public interface IScmPropertyValueBusiness {

    String create(ScmPropertyValueCreateRequestVo request);

    void update(ScmPropertyValueUpdateRequestVo request);

    void deleteById(IdRequest request);

    List<ScmPropertyValueListResponseVo> listByPropertyId(ScmPropertyValueListByPropertyRequestVo request);
}
