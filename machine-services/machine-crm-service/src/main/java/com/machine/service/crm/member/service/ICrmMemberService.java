package com.machine.service.crm.member.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.member.dto.input.CrmMemberCreateInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberUpdateInputDto;
import com.machine.client.crm.member.dto.output.CrmMemberDetailOutputDto;
import com.machine.client.crm.member.dto.output.CrmMemberListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface ICrmMemberService {

    String create(CrmMemberCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(CrmMemberUpdateInputDto inputDto);

    CrmMemberDetailOutputDto detail(IdRequest request);

    Page<CrmMemberListOutputDto> selectPage(CrmMemberQueryPageInputDto inputDto);
}
