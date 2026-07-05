package com.machine.client.scm.category;

import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/category/back_category",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmBackCategoryClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmBackCategoryCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmBackCategoryUpdateInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Valid ScmBackCategoryUpdateParentInputDto inputDto);

    @PostMapping("detail")
    ScmBackCategoryDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @GetMapping("list_all")
    List<ScmBackCategoryListOutputDto> listAll();

    @GetMapping("tree_all_simple")
    ScmBackCategoryTreeSimpleOutputDto treeAllSimple();
}