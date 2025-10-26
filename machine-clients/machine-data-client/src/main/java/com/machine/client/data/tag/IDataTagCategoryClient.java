package com.machine.client.data.tag;

import com.machine.client.data.tag.dto.input.DataTagCategoryCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateParentInputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryListOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/tag/category",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataTagCategoryClient {

    @PostMapping("create")
    String create(@RequestBody @Valid DataTagCategoryCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid DataTagCategoryUpdateInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Valid DataTagCategoryUpdateParentInputDto inputDto);

    @PostMapping("detail")
    DataTagCategoryDetailOutputDto detail(@RequestBody @Valid IdRequest request);

    @GetMapping("list_all_by_type")
    List<DataTagCategoryListOutputDto> listAllByType(@RequestParam("type") ProfileSubjectTypeEnum type);

    @GetMapping("tree_all_simple")
    DataTagCategoryTreeSimpleOutputDto treeAllSimple(@RequestParam("type") ProfileSubjectTypeEnum type);
}
