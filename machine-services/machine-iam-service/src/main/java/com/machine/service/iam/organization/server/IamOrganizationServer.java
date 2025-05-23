package com.machine.service.iam.organization.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.organization.IIamOrganizationClient;
import com.machine.client.iam.organization.dto.input.IamOrganizationCreateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationDetailByNameInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateParentInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationListOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.iam.organization.service.IOrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/iam/organization")
public class IamOrganizationServer implements IIamOrganizationClient {

    @Autowired
    private IOrganizationService organizationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated IamOrganizationCreateInputDto inputDto) {
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
    public int update(@RequestBody @Validated IamOrganizationUpdateInputDto inputDto) {
        log.info("修改组织，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return organizationService.update(inputDto);
    }

    @Override
    @PostMapping("updateParent")
    public int updateParent(@RequestBody @Validated IamOrganizationUpdateParentInputDto inputDto) {
        log.info("修改父组织，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return organizationService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public IamOrganizationDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return organizationService.detail(request);
    }

    @Override
    @PostMapping("detail_by_name")
    public IamOrganizationDetailOutputDto detailByName(@RequestBody @Validated IamOrganizationDetailByNameInputDto inputDto) {
        return organizationService.detailByName(inputDto);
    }

    @Override
    @GetMapping("list_all_by_type")
    public List<IamOrganizationListOutputDto> listAllByType(@RequestParam("organizationType") OrganizationTypeEnum organizationType) {
        return organizationService.listAllByType(organizationType);
    }

    @Override
    @GetMapping("tree_all_simple")
    public IamOrganizationTreeSimpleOutputDto treeAllSimple(@RequestParam("organizationType") OrganizationTypeEnum organizationType) {
        return organizationService.treeAllSimple(organizationType);
    }
}
