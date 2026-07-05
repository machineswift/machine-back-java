package com.machine.app.manage.scm.category.business;

import com.machine.app.manage.scm.category.controller.vo.request.ScmFrontCategoryCreateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmFrontCategoryUpdateParentRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmFrontCategoryUpdateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.response.ScmFrontCategoryDetailResponseVo;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeExpandOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IScmFrontCategoryBusiness {

    String create(ScmFrontCategoryCreateRequestVo request);

    void update(ScmFrontCategoryUpdateRequestVo request);

    void updateParent(ScmFrontCategoryUpdateParentRequestVo request);

    void deleteById(IdRequest request);

    ScmFrontCategoryDetailResponseVo getById(IdRequest request);

    ScmFrontCategoryTreeOutputDto treeSimple();

    ScmFrontCategoryTreeExpandOutputDto treeExpand();

}