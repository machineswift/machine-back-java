package com.machine.service.scm.attribute.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.IScmBackCategoryAttributeRelationClient;
import com.machine.client.scm.attribute.dto.input.ScmBackCategoryAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.scm.attribute.service.IScmBackCategoryAttributeRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/scm/attribute/back_category_attribute_relation")
public class ScmBackCategoryAttributeRelationServer implements IScmBackCategoryAttributeRelationClient {

    @Autowired
    private IScmBackCategoryAttributeRelationService backCategoryAttributeRelationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmBackCategoryAttributeRelationCreateInputDto inputDto) {
        log.info("创建后台分类属性关联，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return backCategoryAttributeRelationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除后台分类属性关联，id={}", request.getId());
        return backCategoryAttributeRelationService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmBackCategoryAttributeRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return backCategoryAttributeRelationService.getById(request);
    }

    @Override
    @PostMapping("list_by_back_category")
    public List<ScmBackCategoryAttributeRelationDetailOutputDto> listByBackCategoryId(@RequestBody @Validated IdRequest request) {
        return backCategoryAttributeRelationService.listByBackCategoryId(request);
    }

    @Override
    @PostMapping("list_by_attribute")
    public List<ScmBackCategoryAttributeRelationDetailOutputDto> listByAttributeId(@RequestBody @Validated IdRequest request) {
        return backCategoryAttributeRelationService.listByAttributeId(request);
    }

    @Override
    @PostMapping("map_attribute_count")
    public Map<String, Integer> mapAttributeCountByBackCategoryIdSet(@RequestBody @Validated IdSetRequest request) {
        return backCategoryAttributeRelationService.mapAttributeCountByBackCategoryIdSet(request);
    }

    @Override
    @PostMapping("map_back_category_count")
    public Map<String, Integer> mapBackCategoryCountByAttributeIdSet(@RequestBody @Validated IdSetRequest request) {
        return backCategoryAttributeRelationService.mapBackCategoryCountByAttributeIdSet(request);
    }
}