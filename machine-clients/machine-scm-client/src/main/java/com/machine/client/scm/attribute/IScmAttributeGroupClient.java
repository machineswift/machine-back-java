package com.machine.client.scm.attribute;

import com.machine.client.scm.attribute.dto.ScmAttributeGroupBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryListInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateAttributeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/attribute/attribute_group",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmAttributeGroupClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmAttributeGroupCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmAttributeGroupUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Valid ScmAttributeGroupUpdateStatusInputDto inputDto);

    @PostMapping("update_back_category")
    int updateBackCategory(@RequestBody @Valid ScmAttributeGroupUpdateBackCategoryInputDto inputDto);

    @PostMapping("update_attribute")
    int updateAttribute(@RequestBody @Valid ScmAttributeGroupUpdateAttributeInputDto inputDto);

    @PostMapping("update_code")
    int updateCode(@RequestBody @Valid ScmAttributeGroupUpdateCodeInputDto inputDto);

    @PostMapping("map_back_category_count")
    Map<String, Integer> mapBackCategoryCountByGroupIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("map_attribute_count")
    Map<String, Integer> mapAttributeCountByGroupIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("map_attribute_group_count")
    Map<String, Integer> mapAttributeGroupCountByBackCategoryIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("map_attribute_group_count_by_attribute")
    Map<String, Integer> mapAttributeGroupCountByAttributeIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("map_attribute_group")
    Map<String, List<ScmAttributeGroupItemOutputDto>> mapAttributeGroupByBackCategoryIdSet(
            @RequestBody @Valid IdSetRequest request);

    @PostMapping("detail")
    ScmAttributeGroupDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_back_category")
    List<ScmAttributeGroupBackCategoryItemDto> listBackCategoryByGroupId(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_id_set")
    List<ScmAttributeGroupSimpleListOutputDto> listByIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("list_simple")
    List<ScmAttributeGroupSimpleListOutputDto> listSimple(@RequestBody @Valid ScmAttributeGroupQueryListInputDto inputDto);

    @PostMapping("page_expand")
    PageResponse<ScmAttributeGroupListOutputDto> pageExpand(@RequestBody @Valid ScmAttributeGroupQueryPageInputDto inputDto);

}