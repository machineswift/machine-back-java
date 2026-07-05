package com.machine.client.scm.category;

import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/category/front_back_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmFrontBackCategoryRelationClient {

    @PostMapping("detail")
    ScmFrontBackCategoryRelationDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @GetMapping("listAll")
    List<ScmFrontBackCategoryRelationListOutputDto> listAll();

    @PostMapping("list_by_frontCategoryId")
    List<ScmFrontBackCategoryRelationListOutputDto> listByFrontCategoryId(@RequestBody @Valid IdRequest request);

}