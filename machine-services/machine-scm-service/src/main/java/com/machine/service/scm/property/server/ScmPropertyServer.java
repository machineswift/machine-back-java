package com.machine.service.scm.property.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.property.IScmPropertyClient;
import com.machine.client.scm.property.dto.input.ScmPropertyCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.scm.property.service.IScmPropertyService;
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
@RequestMapping("server/scm/property/property")
public class ScmPropertyServer implements IScmPropertyClient {

    @Autowired
    private IScmPropertyService propertyService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmPropertyCreateInputDto inputDto) {
        log.info("创建属性，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        return propertyService.deleteById(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmPropertyUpdateInputDto inputDto) {
        log.info("修改属性，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return propertyService.update(inputDto);
    }

    @Override
    @PostMapping("detail")
    public ScmPropertyDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性详情，id={}", request.getId());
        return propertyService.getById(request);
    }

    @Override
    @PostMapping("list_all")
    public List<ScmPropertyListOutputDto> listAll() {
        log.info("获取所有属性列表");
        return propertyService.listAll();
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<ScmPropertyListOutputDto> selectPage(@RequestBody @Validated ScmPropertyQueryPageInputDto inputDto) {
        return propertyService.selectPage(inputDto);
    }
}