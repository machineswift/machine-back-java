package com.machine.app.admin.scm.property.business;

import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupCreateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupListByBackCategoryRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupUpdateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupUpdateSortRequestVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyGroupDetailResponseVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyGroupListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;

import java.util.List;

/**
 * 后台叶子类目下的属性展示分组编排。
 */
public interface IScmPropertyGroupBusiness {

    String create(ScmPropertyGroupCreateRequestVo request);

    void update(ScmPropertyGroupUpdateRequestVo request);

    void deleteById(IdRequest request);

    ScmPropertyGroupDetailResponseVo getById(IdRequest request);

    void updateSort(ScmPropertyGroupUpdateSortRequestVo request);

    List<ScmPropertyGroupListResponseVo> listByBackCategoryId(ScmPropertyGroupListByBackCategoryRequestVo request);

}
