package com.machine.client.scm.property;

import com.machine.client.scm.property.dto.input.ScmPropertyValueRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueRelationDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/property/property_value_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmPropertyValueRelationClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmPropertyValueRelationCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmPropertyValueRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_child_property_by_parent_value")
    List<String> listChildPropertyIdByParentValueId(@RequestBody @Valid IdRequest request);
}