package com.machine.client.crm.member;

import com.machine.client.crm.member.dto.input.CrmMemberCreateInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberUpdateInputDto;
import com.machine.client.crm.member.dto.output.CrmMemberDetailOutputDto;
import com.machine.client.crm.member.dto.output.CrmMemberListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-crm-service", path = "machine-crm-service/server/crm/member",
        configuration = OpenFeignMinTimeConfig.class)
public interface ICrmMemberClient {

    @PostMapping("create")
    String create(@RequestBody @Validated CrmMemberCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated CrmMemberUpdateInputDto inputDto);

    @PostMapping("detail")
    CrmMemberDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("select_page")
    PageResponse<CrmMemberListOutputDto> selectPage(@RequestBody @Validated CrmMemberQueryPageInputDto inputDto);
}



