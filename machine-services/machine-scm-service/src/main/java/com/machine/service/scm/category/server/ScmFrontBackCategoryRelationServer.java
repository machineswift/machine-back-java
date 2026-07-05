package com.machine.service.scm.category.server;

import com.machine.client.scm.category.IScmFrontBackCategoryRelationClient;
import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontBackCategoryRelationListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.category.service.IScmFrontBackCategoryRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/scm/category/front_back_relation")
public class ScmFrontBackCategoryRelationServer implements IScmFrontBackCategoryRelationClient {

    @Autowired
    private IScmFrontBackCategoryRelationService frontBackCategoryRelationService;

    @Override
    @PostMapping("detail")
    public ScmFrontBackCategoryRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return frontBackCategoryRelationService.getById(request);
    }

    @Override
    @GetMapping("listAll")
    public List<ScmFrontBackCategoryRelationListOutputDto> listAll() {
        return frontBackCategoryRelationService.listAll();
    }

    @Override
    @PostMapping("list_by_frontCategoryId")
    public List<ScmFrontBackCategoryRelationListOutputDto> listByFrontCategoryId(@RequestBody @Validated IdRequest request) {
        return frontBackCategoryRelationService.listByFrontCategoryId(request);
    }

}