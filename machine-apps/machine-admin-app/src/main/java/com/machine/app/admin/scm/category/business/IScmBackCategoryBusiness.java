package com.machine.app.admin.scm.category.business;

import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryCreateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateParentRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmBackCategoryDetailResponseVo;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeExprandOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IScmBackCategoryBusiness {

    String create(ScmBackCategoryCreateRequestVo request);

    void update(ScmBackCategoryUpdateRequestVo request);

    void updateParent(ScmBackCategoryUpdateParentRequestVo request);

    void deleteById(IdRequest request);

    ScmBackCategoryDetailResponseVo getById(IdRequest request);

    ScmBackCategoryTreeSimpleOutputDto treeSimple();

    ScmBackCategoryTreeExprandOutputDto treeExpand();
}