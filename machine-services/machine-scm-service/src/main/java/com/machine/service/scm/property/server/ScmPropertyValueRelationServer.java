package com.machine.service.scm.property.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.IScmPropertyValueRelationClient;
import com.machine.client.scm.property.dto.input.ScmPropertyValueRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.service.IScmPropertyValueRelationService;
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
@RequestMapping("server/scm/property/property_value_relation")
public class ScmPropertyValueRelationServer implements IScmPropertyValueRelationClient {

    @Autowired
    private IScmPropertyValueRelationService propertyValueRelationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmPropertyValueRelationCreateInputDto inputDto) {
        log.info("创建属性值关联，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyValueRelationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性值关联，id={}", request.getId());
        return propertyValueRelationService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmPropertyValueRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性值关联详情，id={}", request.getId());
        return propertyValueRelationService.getById(request);
    }

    @Override
    @PostMapping("list_child_property_by_parent_value")
    public List<String> listChildPropertyIdByParentValueId(@RequestBody @Validated IdRequest request) {
        log.info("根据父属性值ID获取子属性ID列表，parentValueId={}", request.getId());
        return propertyValueRelationService.listChildPropertyIdByParentValueId(request);
    }
}