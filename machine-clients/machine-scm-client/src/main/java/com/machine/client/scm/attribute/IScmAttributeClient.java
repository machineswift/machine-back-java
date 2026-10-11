package com.machine.client.scm.attribute;

import com.machine.client.scm.attribute.dto.input.ScmAttributeCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateAttributeGroupInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/attribute/attribute",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmAttributeClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmAttributeCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmAttributeUpdateInputDto inputDto);

    @PostMapping("update_code")
    int updateCode(@RequestBody @Valid ScmAttributeUpdateCodeInputDto inputDto);

    @PostMapping("update_attribute_group")
    int updateAttributeGroup(@RequestBody @Valid ScmAttributeUpdateAttributeGroupInputDto inputDto);

    @PostMapping("update_back_category")
    int updateBackCategory(@RequestBody @Valid ScmAttributeUpdateBackCategoryInputDto inputDto);

    @PostMapping("detail")
    ScmAttributeDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_all")
    List<ScmAttributeListOutputDto> listAll();

    @PostMapping("list_by_id_set")
    List<ScmAttributeListOutputDto> listByIdSet(@RequestBody @Valid IdSetRequest request);

    @PostMapping("select_page")
    PageResponse<ScmAttributeListOutputDto> selectPage(@RequestBody @Valid ScmAttributeQueryPageInputDto inputDto);
}