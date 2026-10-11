package com.machine.service.scm.attribute.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.IScmAttributeGroupAttributeRelationClient;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupAttributeRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.service.IScmAttributeGroupAttributeRelationService;
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
@RequestMapping("server/scm/attribute/attribute_group_attribute_relation")
public class ScmAttributeGroupAttributeRelationServer implements IScmAttributeGroupAttributeRelationClient {

    @Autowired
    private IScmAttributeGroupAttributeRelationService attributeGroupAttributeRelationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmAttributeGroupAttributeRelationCreateInputDto inputDto) {
        log.info("创建属性分组关联，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupAttributeRelationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性分组关联，id={}", request.getId());
        return attributeGroupAttributeRelationService.deleteById(request);
    }

    @Override
    @PostMapping("delete_by_group_id")
    public int deleteByGroupId(@RequestBody @Validated IdRequest request) {
        log.info("根据分组ID删除组内属性关联，groupId={}", request.getId());
        return attributeGroupAttributeRelationService.deleteByGroupId(request.getId());
    }

    @Override
    @PostMapping("detail")
    public ScmAttributeGroupAttributeRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性分组关联详情，id={}", request.getId());
        return attributeGroupAttributeRelationService.getById(request);
    }

    @Override
    @PostMapping("list_by_group")
    public List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByGroupId(@RequestBody @Validated IdRequest request) {
        log.info("根据分组ID获取属性分组关联列表，groupId={}", request.getId());
        return attributeGroupAttributeRelationService.listByGroupId(request);
    }

    @Override
    @PostMapping("list_by_attribute")
    public List<ScmAttributeGroupAttributeRelationDetailOutputDto> listByAttributeId(@RequestBody @Validated IdRequest request) {
        log.info("根据属性ID获取属性分组关联列表，attributeId={}", request.getId());
        return attributeGroupAttributeRelationService.listByAttributeId(request);
    }

    @Override
    @PostMapping("list_attribute_by_group")
    public List<String> listAttributeIdByGroupId(@RequestBody @Validated IdRequest request) {
        log.info("根据分组ID获取属性ID列表，groupId={}", request.getId());
        return attributeGroupAttributeRelationService.listAttributeIdByGroupId(request);
    }

}