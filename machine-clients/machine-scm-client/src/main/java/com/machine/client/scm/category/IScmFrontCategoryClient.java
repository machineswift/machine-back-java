package com.machine.client.scm.category;

import com.machine.client.scm.category.dto.input.ScmFrontCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/category/front_category",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmFrontCategoryClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmFrontCategoryCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmFrontCategoryUpdateInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Valid ScmFrontCategoryUpdateParentInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmFrontCategoryDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @GetMapping("tree_all_simple")
    ScmFrontCategoryTreeOutputDto treeAllSimple();

    @GetMapping("list_all")
    List<ScmFrontCategoryListOutputDto> listAll();
}