package com.machine.service.scm.category.server;

import com.machine.client.scm.category.IScmBackCategoryFrontRelationClient;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.category.service.IScmBackCategoryFrontRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/scm/category/back_category_front_relation")
public class ScmBackCategoryFrontRelationServer implements IScmBackCategoryFrontRelationClient {

    @Autowired
    private IScmBackCategoryFrontRelationService backCategoryFrontRelationService;

    @Override
    @PostMapping("detail")
    public ScmBackCategoryFrontRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return backCategoryFrontRelationService.getById(request);
    }

    @Override
    @GetMapping("listAll")
    public List<ScmBackCategoryFrontRelationListOutputDto> listAll() {
        return backCategoryFrontRelationService.listAll();
    }

    @Override
    @PostMapping("list_by_frontCategoryId")
    public List<ScmBackCategoryFrontRelationListOutputDto> listByFrontCategoryId(@RequestBody @Validated IdRequest request) {
        return backCategoryFrontRelationService.listByFrontCategoryId(request);
    }

    @Override
    @PostMapping("list_by_backCategoryId")
    public List<ScmBackCategoryFrontRelationListOutputDto> listByBackCategoryId(@RequestBody @Validated IdRequest request) {
        return backCategoryFrontRelationService.listByBackCategoryId(request);
    }

}