package com.machine.client.iam.organization;

import com.machine.client.iam.organization.dto.input.*;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationListOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service/machine-iam-service/server/iam/organization",
        configuration = OpenFeignDefaultConfig.class)
public interface IIamOrganizationClient {

    @PostMapping("create")
    String create(@RequestBody @Validated IamOrganizationCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated IamOrganizationUpdateInputDto inputDto);

    @PostMapping("updateParent")
    int updateParent(@RequestBody @Validated IamOrganizationUpdateParentInputDto inputDto);

    @PostMapping("detail")
    IamOrganizationDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("list_all_by_type")
    List<IamOrganizationListOutputDto> listAllByType(@RequestParam("organizationType") IamOrganizationTypeEnum organizationType);

    /**
     * 区域树
     */
    @GetMapping("tree_all_simple")
    IamOrganizationTreeSimpleOutputDto treeAllSimple(@RequestParam("organizationType") IamOrganizationTypeEnum organizationType);

}
