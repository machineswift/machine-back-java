package com.machine.service.scm.attribute.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.dto.ScmAttributeGroupBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryListInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateAttributeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.scm.attribute.service.IScmAttributeGroupService;
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
@RequestMapping("server/scm/attribute/attribute_group")
public class ScmAttributeGroupServer implements IScmAttributeGroupClient {

    @Autowired
    private IScmAttributeGroupService attributeGroupService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmAttributeGroupCreateInputDto inputDto) {
        log.info("创建属性分组，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性分组，id={}", request.getId());
        return attributeGroupService.deleteById(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmAttributeGroupUpdateInputDto inputDto) {
        log.info("修改属性分组，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated ScmAttributeGroupUpdateStatusInputDto inputDto) {
        log.info("修改属性分组状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_back_category")
    public int updateBackCategory(@RequestBody @Validated ScmAttributeGroupUpdateBackCategoryInputDto inputDto) {
        log.info("修改属性分组关联后台分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupService.updateBackCategory(inputDto);
    }

    @Override
    @PostMapping("update_attribute")
    public int updateAttribute(@RequestBody @Validated ScmAttributeGroupUpdateAttributeInputDto inputDto) {
        log.info("修改属性分组关联属性，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupService.updateAttribute(inputDto);
    }

    @Override
    @PostMapping("update_code")
    public int updateCode(@RequestBody @Validated ScmAttributeGroupUpdateCodeInputDto inputDto) {
        log.info("修改属性分组编码，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeGroupService.updateCode(inputDto);
    }

    @Override
    @PostMapping("map_back_category_count")
    public Map<String, Integer> mapBackCategoryCountByGroupIdSet(@RequestBody @Validated IdSetRequest request) {
        return attributeGroupService.mapBackCategoryCountByGroupIdSet(request);
    }

    @Override
    @PostMapping("map_attribute_count")
    public Map<String, Integer> mapAttributeCountByGroupIdSet(@RequestBody @Validated IdSetRequest request) {
        return attributeGroupService.mapAttributeCountByGroupIdSet(request);
    }

    @Override
    @PostMapping("map_attribute_group_count")
    public Map<String, Integer> mapAttributeGroupCountByBackCategoryIdSet(@RequestBody @Validated IdSetRequest request) {
        return attributeGroupService.mapAttributeGroupCountByBackCategoryIdSet(request);
    }

    @Override
    @PostMapping("map_attribute_group_count_by_attribute")
    public Map<String, Integer> mapAttributeGroupCountByAttributeIdSet(@RequestBody @Validated IdSetRequest request) {
        return attributeGroupService.mapAttributeGroupCountByAttributeIdSet(request);
    }

    @Override
    @PostMapping("map_attribute_group")
    public Map<String, List<ScmAttributeGroupItemOutputDto>> mapAttributeGroupByBackCategoryIdSet(
            @RequestBody @Validated IdSetRequest request) {
        return attributeGroupService.mapAttributeGroupByBackCategoryIdSet(request);
    }

    @Override
    @PostMapping("detail")
    public ScmAttributeGroupDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return attributeGroupService.getById(request);
    }

    @Override
    @PostMapping("list_back_category")
    public List<ScmAttributeGroupBackCategoryItemDto> listBackCategoryByGroupId(
            @RequestBody @Validated IdRequest request) {
        return attributeGroupService.listBackCategoryByGroupId(request);
    }

    @Override
    @PostMapping("list_by_id_set")
    public List<ScmAttributeGroupSimpleListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        return attributeGroupService.listByIdSet(request);
    }

    @Override
    @PostMapping("list_simple")
    public List<ScmAttributeGroupSimpleListOutputDto> listSimple(
            @RequestBody @Validated ScmAttributeGroupQueryListInputDto inputDto) {
        return attributeGroupService.listSimple(inputDto);
    }

    @Override
    @PostMapping("page_expand")
    public PageResponse<ScmAttributeGroupListOutputDto> pageExpand(
            @RequestBody @Validated ScmAttributeGroupQueryPageInputDto inputDto) {
        return attributeGroupService.pageExpand(inputDto);
    }

}
