package com.machine.service.scm.attribute.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.IScmAttributeValueRelationClient;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueRelationCreateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.service.IScmAttributeValueRelationService;
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
@RequestMapping("server/scm/attribute/attribute_value_relation")
public class ScmAttributeValueRelationServer implements IScmAttributeValueRelationClient {

    @Autowired
    private IScmAttributeValueRelationService attributeValueRelationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmAttributeValueRelationCreateInputDto inputDto) {
        log.info("创建属性值关联，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeValueRelationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性值关联，id={}", request.getId());
        return attributeValueRelationService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmAttributeValueRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性值关联详情，id={}", request.getId());
        return attributeValueRelationService.getById(request);
    }

    @Override
    @PostMapping("list_child_attribute_by_parent_value")
    public List<String> listChildAttributeIdByParentValueId(@RequestBody @Validated IdRequest request) {
        log.info("根据父属性值ID获取子属性ID列表，parentValueId={}", request.getId());
        return attributeValueRelationService.listChildAttributeIdByParentValueId(request);
    }
}