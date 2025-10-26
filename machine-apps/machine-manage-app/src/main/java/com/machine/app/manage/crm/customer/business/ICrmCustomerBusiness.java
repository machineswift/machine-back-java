package com.machine.app.manage.crm.customer.business;

import com.machine.app.manage.crm.customer.controller.vo.response.CrmCustomerDetailResponseVo;
import com.machine.app.manage.crm.customer.controller.vo.response.CrmCustomerExpandListResponseVo;
import com.machine.app.manage.crm.customer.controller.vo.response.CrmCustomerListResponseVo;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerCreateRequestVo;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerQueryPageRequestVo;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface ICrmCustomerBusiness {
    String create(CrmCustomerCreateRequestVo request);

    void delete(IdRequest request);

    void update(CrmCustomerUpdateRequestVo request);

    CrmCustomerDetailResponseVo detail(IdRequest request);

    PageResponse<CrmCustomerListResponseVo> pageSimple(CrmCustomerQueryPageRequestVo request);

    PageResponse<CrmCustomerExpandListResponseVo> pageExpand(CrmCustomerQueryPageRequestVo request);
}
