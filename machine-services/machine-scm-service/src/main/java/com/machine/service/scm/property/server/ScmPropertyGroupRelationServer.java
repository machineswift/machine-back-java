package com.machine.service.scm.property.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.IScmPropertyGroupRelationClient;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupRelationCreateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.service.IScmPropertyGroupRelationService;
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
@RequestMapping("server/scm/property/property_group_relation")
public class ScmPropertyGroupRelationServer implements IScmPropertyGroupRelationClient {

    @Autowired
    private IScmPropertyGroupRelationService propertyGroupRelationService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmPropertyGroupRelationCreateInputDto inputDto) {
        log.info("创建属性分组关联，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyGroupRelationService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性分组关联，id={}", request.getId());
        return propertyGroupRelationService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmPropertyGroupRelationDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性分组关联详情，id={}", request.getId());
        return propertyGroupRelationService.getById(request);
    }

    @Override
    @PostMapping("list_property_by_group")
    public List<String> listPropertyIdByGroupId(@RequestBody @Validated IdRequest request) {
        log.info("根据分组ID获取属性ID列表，groupId={}", request.getId());
        return propertyGroupRelationService.listPropertyIdByGroupId(request);
    }

    @Override
    @PostMapping("delete_by_group_id")
    public int deleteByGroupId(@RequestBody @Validated IdRequest request) {
        log.info("根据分组ID删除组内属性关联，groupId={}", request.getId());
        return propertyGroupRelationService.deleteByGroupId(request.getId());
    }

}