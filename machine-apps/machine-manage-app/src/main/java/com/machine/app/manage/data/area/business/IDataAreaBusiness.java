package com.machine.app.manage.data.area.business;

import com.machine.app.manage.data.area.controller.vo.request.DataAreaTreeRequestVo;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;

public interface IDataAreaBusiness {

    void clearCache();

    AreaTreeOutputDto treeSimple(DataAreaTreeRequestVo request);

}
