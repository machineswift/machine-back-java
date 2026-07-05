package com.machine.service.scm.property.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.IScmPropertyGroupClient;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.property.service.IScmPropertyGroupService;
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
@RequestMapping("server/scm/property/property_group")
public class ScmPropertyGroupServer implements IScmPropertyGroupClient {

    @Autowired
    private IScmPropertyGroupService propertyGroupService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmPropertyGroupCreateInputDto inputDto) {
        log.info("创建属性分组，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyGroupService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmPropertyGroupUpdateInputDto inputDto) {
        log.info("修改属性分组，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyGroupService.update(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性分组，id={}", request.getId());
        return propertyGroupService.deleteById(request);
    }

    @Override
    @PostMapping("detail")
    public ScmPropertyGroupDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性分组详情，id={}", request.getId());
        return propertyGroupService.getById(request);
    }

    @Override
    @PostMapping("list_by_back_category_id")
    public List<ScmPropertyGroupListOutputDto> listByBackCategoryId(@RequestBody @Validated ScmPropertyGroupQueryInputDto inputDto) {
        log.info("根据后台分类ID获取属性分组列表，backCategoryId={}", inputDto.getBackCategoryId());
        return propertyGroupService.listByBackCategoryId(inputDto);
    }

}