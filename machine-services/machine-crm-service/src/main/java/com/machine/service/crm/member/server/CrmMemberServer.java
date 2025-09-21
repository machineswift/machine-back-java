package com.machine.service.crm.member.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.member.dto.input.CrmMemberCreateInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberUpdateInputDto;
import com.machine.client.crm.member.dto.output.CrmMemberDetailOutputDto;
import com.machine.client.crm.member.dto.output.CrmMemberListOutputDto;
import com.machine.client.crm.member.ICrmMemberClient;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.crm.member.service.ICrmMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/crm/member")
public class CrmMemberServer implements ICrmMemberClient {

    @Autowired
    private ICrmMemberService memberService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated CrmMemberCreateInputDto inputDto) {
        log.info("创建客户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return memberService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除客户，request={}", JSONUtil.toJsonStr(request));
        return memberService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated CrmMemberUpdateInputDto inputDto) {
        log.info("修改客户，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return memberService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public CrmMemberDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return memberService.detail(request);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<CrmMemberListOutputDto> selectPage(@RequestBody @Validated CrmMemberQueryPageInputDto inputDto) {
        Page<CrmMemberListOutputDto> pageResult = memberService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
