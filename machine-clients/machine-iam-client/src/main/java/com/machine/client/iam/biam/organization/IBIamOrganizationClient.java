package com.machine.client.iam.biam.organization;

import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationCreateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateParentInputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationListOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/organization", configuration = OpenFeignMinTimeConfig.class)
public interface IBIamOrganizationClient {

    @PostMapping("create")
    String create(@RequestBody @Validated BIamOrganizationCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated BIamOrganizationUpdateInputDto inputDto);

    @PostMapping("updateParent")
    int updateParent(@RequestBody @Validated BIamOrganizationUpdateParentInputDto inputDto);

    @PostMapping("detail")
    BIamOrganizationDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("list_all_by_type")
    List<BIamOrganizationListOutputDto> listAllByType(@RequestParam("type") BIamOrganizationTypeEnum type);

    @GetMapping("tree_all_simple")
    Tuples.Tuple2<String, BIamOrganizationTreeSimpleOutputDto> treeAllSimple(
            @RequestParam("type") BIamOrganizationTypeEnum type);

}
