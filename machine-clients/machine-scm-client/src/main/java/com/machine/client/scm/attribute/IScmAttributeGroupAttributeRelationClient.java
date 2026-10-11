package com.machine.client.scm.attribute;

import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/attribute/attribute_group_attribute_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmAttributeGroupAttributeRelationClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmAttributeGroupAttributeRelationCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("delete_by_group_id")
    int deleteByGroupId(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmAttributeGroupAttributeRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_group")
    List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByGroupId(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_attribute")
    List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByAttributeId(@RequestBody @Valid IdRequest request);

    @PostMapping("list_attribute_by_group")
    List<String> listAttributeIdByGroupId(@RequestBody @Valid IdRequest request);

}