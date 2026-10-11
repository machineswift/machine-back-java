package com.machine.client.scm.attribute;

import com.machine.client.scm.attribute.dto.input.ScmBackCategoryAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/attribute/back_category_attribute_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmBackCategoryAttributeRelationClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmBackCategoryAttributeRelationCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmBackCategoryAttributeRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_back_category")
    List<ScmBackCategoryAttributeRelationDetailOutputDto> listByBackCategoryId(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_attribute")
    List<ScmBackCategoryAttributeRelationDetailOutputDto> listByAttributeId(@RequestBody @Valid IdRequest request);

    @PostMapping("map_attribute_count")
    Map<String, Integer> mapAttributeCountByBackCategoryIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("map_back_category_count")
    Map<String, Integer> mapBackCategoryCountByAttributeIdSet(@RequestBody @Valid IdSetRequest request);
}