package com.machine.app.admin.scm.category.business;

import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryCreateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateParentRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmFrontCategoryDetailResponseVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmFrontCategoryTreeExpandResponseVo;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IScmFrontCategoryBusiness {

    String create(ScmFrontCategoryCreateRequestVo request);

    void deleteById(IdRequest request);

    void update(ScmFrontCategoryUpdateRequestVo request);

    void updateParent(ScmFrontCategoryUpdateParentRequestVo request);

    void updateBackCategory(ScmFrontCategoryUpdateBackCategoryRequestVo request);

    ScmFrontCategoryDetailResponseVo getById(IdRequest request);

    ScmFrontCategoryTreeOutputDto treeSimple();

    ScmFrontCategoryTreeExpandResponseVo treeExpand();

}