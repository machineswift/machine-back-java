package com.machine.service.scm.attribute.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.dto.input.ScmAttributeCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateAttributeGroupInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.scm.attribute.service.IScmAttributeService;
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
@RequestMapping("server/scm/attribute/attribute")
public class ScmAttributeServer implements IScmAttributeClient {

    @Autowired
    private IScmAttributeService attributeService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmAttributeCreateInputDto inputDto) {
        log.info("创建属性，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        return attributeService.deleteById(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmAttributeUpdateInputDto inputDto) {
        log.info("修改属性，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeService.update(inputDto);
    }

    @Override
    @PostMapping("update_code")
    public int updateCode(@RequestBody @Validated ScmAttributeUpdateCodeInputDto inputDto) {
        log.info("修改属性编码，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeService.updateCode(inputDto);
    }

    @Override
    @PostMapping("update_attribute_group")
    public int updateAttributeGroup(@RequestBody @Validated ScmAttributeUpdateAttributeGroupInputDto inputDto) {
        log.info("修改属性关联的属性分组，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeService.updateAttributeGroup(inputDto);
    }

    @Override
    @PostMapping("update_back_category")
    public int updateBackCategory(@RequestBody @Validated ScmAttributeUpdateBackCategoryInputDto inputDto) {
        log.info("修改属性关联后台分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeService.updateBackCategory(inputDto);
    }

    @Override
    @PostMapping("detail")
    public ScmAttributeDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性详情，id={}", request.getId());
        return attributeService.getById(request);
    }

    @Override
    @PostMapping("list_all")
    public List<ScmAttributeListOutputDto> listAll() {
        log.info("获取所有属性列表");
        return attributeService.listAll();
    }

    @Override
    @PostMapping("list_by_id_set")
    public List<ScmAttributeListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        log.info("根据ID集合获取属性列表，request={}", JSONUtil.toJsonStr(request));
        return attributeService.listByIdSet(request);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<ScmAttributeListOutputDto> selectPage(@RequestBody @Validated ScmAttributeQueryPageInputDto inputDto) {
        return attributeService.selectPage(inputDto);
    }
}