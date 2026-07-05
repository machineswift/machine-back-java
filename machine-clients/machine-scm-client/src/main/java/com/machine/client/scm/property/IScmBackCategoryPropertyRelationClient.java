package com.machine.client.scm.property;

import com.machine.client.scm.property.dto.input.ScmBackCategoryPropertyRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmBackCategoryPropertyRelationDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/property/back_category_property_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmBackCategoryPropertyRelationClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmBackCategoryPropertyRelationCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmBackCategoryPropertyRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);
}