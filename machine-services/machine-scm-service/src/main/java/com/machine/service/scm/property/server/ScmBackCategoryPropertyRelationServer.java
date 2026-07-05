package com.machine.service.scm.property.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.IScmBackCategoryPropertyRelationClient;
import com.machine.client.scm.property.dto.input.ScmBackCategoryPropertyRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmBackCategoryPropertyRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.service.IScmBackCategoryPropertyRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/scm/property/back_category_property_relation")
public class ScmBackCategoryPropertyRelationServer implements IScmBackCategoryPropertyRelationClient {

    @Autowired
    private IScmBackCategoryPropertyRelationService backCategoryPropertyRelationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmBackCategoryPropertyRelationCreateInputDto inputDto) {
        log.info("创建后台分类属性关联，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return backCategoryPropertyRelationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除后台分类属性关联，id={}", request.getId());
        return backCategoryPropertyRelationService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmBackCategoryPropertyRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return backCategoryPropertyRelationService.getById(request);
    }
}