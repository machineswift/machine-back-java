package com.machine.app.manage.data.tag.business;

import com.machine.app.manage.data.tag.controller.vo.request.*;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagOptionDetailResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagOptionExpandListResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagOptionSimpleListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IDataTagOptionBusiness {

    String create(DataTagOptionCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataTagOptionUpdateRequestVo request);

    void updateCode(DataTagOptionUpdateCodeRequestVo request);

    void updateStatus(DataTagOptionUpdateStatusRequestVo request);

    void updateSort(DataTagOptionUpdateSortRequestVo request);

    DataTagOptionDetailResponseVo detail(IdRequest request);

    List<DataTagOptionSimpleListResponseVo> listSimple(DataTagOptionQueryListRequestVo request);

    List<DataTagOptionExpandListResponseVo> listExpand(DataTagOptionQueryListRequestVo request);
}
