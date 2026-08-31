package com.machine.service.iam.biam.organization.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.organization.IBIamOrganizationClient;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationCreateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateParentInputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationListOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.iam.biam.organization.service.IBIamOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/organization")
public class BIamOrganizationServer implements IBIamOrganizationClient {

    @Autowired
    private IBIamOrganizationService organizationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated BIamOrganizationCreateInputDto inputDto) {
        log.info("创建组织，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return organizationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除组织，request={}", JSONUtil.toJsonStr(request));
        return organizationService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated BIamOrganizationUpdateInputDto inputDto) {
        log.info("修改组织，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return organizationService.update(inputDto);
    }

    @Override
    @PostMapping("updateParent")
    public int updateParent(@RequestBody @Validated BIamOrganizationUpdateParentInputDto inputDto) {
        log.info("修改父组织，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return organizationService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public BIamOrganizationDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return organizationService.detail(request);
    }

    @Override
    @GetMapping("list_all_by_type")
    public List<BIamOrganizationListOutputDto> listAllByType(@RequestParam("type") BIamOrganizationTypeEnum type) {
        return organizationService.listAllByType(type);
    }

    @Override
    @GetMapping("tree_all_simple")
    public Tuples.Tuple2<String, BIamOrganizationTreeSimpleOutputDto> treeAllSimple(
            @RequestParam("type") BIamOrganizationTypeEnum type) {
        return organizationService.treeAllSimple(type);
    }
}
