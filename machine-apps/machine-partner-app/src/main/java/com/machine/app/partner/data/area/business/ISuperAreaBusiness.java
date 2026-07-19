package com.machine.app.partner.data.area.business;

import com.machine.app.partner.data.area.controller.vo.request.SuperAreaTreeRequestVo;
import com.machine.app.partner.data.area.controller.vo.response.SuperAreaTreeExpandSelfResponseVo;
import com.machine.app.partner.data.area.controller.vo.response.SupperAreaTreeSimpleResponseVo;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;

public interface ISuperAreaBusiness {

    DataAreaTreeOutputDto treeSelfSimple(SuperAreaTreeRequestVo request);

    SuperAreaTreeExpandSelfResponseVo treeSelfExpand(SuperAreaTreeRequestVo request);

    SupperAreaTreeSimpleResponseVo treeAllSimple(SuperAreaTreeRequestVo request);

}
