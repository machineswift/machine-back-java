package com.machine.client.scm.property;

import com.machine.client.scm.property.dto.input.ScmPropertyGroupRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupRelationDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/property/property_group_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmPropertyGroupRelationClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmPropertyGroupRelationCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmPropertyGroupRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_property_by_group")
    List<String> listPropertyIdByGroupId(@RequestBody @Valid IdRequest request);

    @PostMapping("delete_by_group_id")
    int deleteByGroupId(@RequestBody @Valid IdRequest request);

}