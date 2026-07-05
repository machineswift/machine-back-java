package com.machine.service.scm.property.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.IScmPropertyValueClient;
import com.machine.client.scm.property.dto.input.ScmPropertyValueCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.service.IScmPropertyValueService;
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
@RequestMapping("server/scm/property/property_value")
public class ScmPropertyValueServer implements IScmPropertyValueClient {

    @Autowired
    private IScmPropertyValueService propertyValueService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmPropertyValueCreateInputDto inputDto) {
        log.info("创建属性值，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyValueService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmPropertyValueUpdateInputDto inputDto) {
        log.info("修改属性值，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyValueService.update(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性值，id={}", request.getId());
        return propertyValueService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmPropertyValueDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性值详情，id={}", request.getId());
        return propertyValueService.getById(request);
    }

    @Override
    @PostMapping("list_by_property_id")
    public List<ScmPropertyValueListOutputDto> listByPropertyId(@RequestBody @Validated ScmPropertyValueQueryInputDto inputDto) {
        log.info("根据属性ID获取属性值列表，propertyId={}", inputDto.getPropertyId());
        return propertyValueService.listByPropertyId(inputDto);
    }
}