package com.machine.app.suprr.data.area.business;

import com.machine.app.suprr.data.area.controller.vo.request.SuperAreaTreeRequestVo;
import com.machine.app.suprr.data.area.controller.vo.response.SuperAreaTreeExpandSelfResponseVo;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;

import java.util.Set;

public interface ISuperAreaBusiness {

    Set<String> recursionListSubIds(Set<String> areaIdSet);

    AreaTreeOutputDto treeSelfSimple(SuperAreaTreeRequestVo request);

    SuperAreaTreeExpandSelfResponseVo treeSelfExpand(SuperAreaTreeRequestVo request);

    AreaTreeOutputDto treeAllSimple(SuperAreaTreeRequestVo request);

}
