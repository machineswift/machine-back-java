package com.machine.client.scm.attribute;

import com.machine.client.scm.attribute.dto.input.ScmAttributeValueRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueRelationDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/attribute/attribute_value_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmAttributeValueRelationClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmAttributeValueRelationCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmAttributeValueRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_child_attribute_by_parent_value")
    List<String> listChildAttributeIdByParentValueId(@RequestBody @Valid IdRequest request);
}