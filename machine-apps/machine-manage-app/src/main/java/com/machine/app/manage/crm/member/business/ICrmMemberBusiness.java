package com.machine.app.manage.crm.member.business;

import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberDetailResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberExpandListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberCreateRequestVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberQueryPageRequestVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface ICrmMemberBusiness {
    String create(CrmMemberCreateRequestVo request);

    void delete(IdRequest request);

    void update(CrmMemberUpdateRequestVo request);

    CrmMemberDetailResponseVo detail(IdRequest request);

    PageResponse<CrmMemberListResponseVo> pageSimple(CrmMemberQueryPageRequestVo request);

    PageResponse<CrmMemberExpandListResponseVo> pageExpand(CrmMemberQueryPageRequestVo request);
}
