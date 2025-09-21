package com.machine.app.manage.data.area.business;

import com.machine.app.manage.data.area.controller.vo.request.DataAreaCreateRequestVo;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaTreeRequestVo;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaUpdateParentRequestVo;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaUpdateRequestVo;
import com.machine.app.manage.data.area.controller.vo.response.DataAreaTreeExpandResponseVo;
import com.machine.app.manage.data.area.controller.vo.response.DataAreaTreeSimpleResponseVo;
import com.machine.app.manage.data.area.controller.vo.response.DataAreaDetailResponseVo;
import com.machine.sdk.common.model.request.IdRequest;

public interface IDataAreaBusiness {

    String create(DataAreaCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataAreaUpdateRequestVo request);

    void updateParent(DataAreaUpdateParentRequestVo request);

    DataAreaDetailResponseVo detail(IdRequest request);

    DataAreaTreeSimpleResponseVo treeSimple(DataAreaTreeRequestVo request);

    DataAreaTreeExpandResponseVo treeExpand(DataAreaTreeRequestVo request);
}
