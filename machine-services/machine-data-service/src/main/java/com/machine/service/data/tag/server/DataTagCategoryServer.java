package com.machine.service.data.tag.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.dto.input.DataTagCategoryCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateParentInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateSortInputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryListOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.tag.service.IDataTagCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/tag/category")
public class DataTagCategoryServer implements IDataTagCategoryClient {

    @Autowired
    private IDataTagCategoryService tagCategoryService;

    @Operation(summary = "创建智能标签分类")
    @PostMapping("create")
    public String create(@RequestBody @Validated DataTagCategoryCreateInputDto inputDto) {
        log.info("创建智能标签分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagCategoryService.create(inputDto);
    }

    @Operation(summary = "删除智能标签分类")
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除智能标签分类，request={}", JSONUtil.toJsonStr(request));
        return tagCategoryService.delete(request);
    }

    @Operation(summary = "更新智能标签分类")
    @PostMapping("update")
    public int update(@RequestBody @Validated DataTagCategoryUpdateInputDto inputDto) {
        log.info("更新智能标签分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagCategoryService.update(inputDto);
    }

    @Override
    @PostMapping("update_sort")
    public int updateSort(@RequestBody @Validated DataTagCategoryUpdateSortInputDto inputDto) {
        log.info("更新智能标签排序，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagCategoryService.updateSort(inputDto);
    }

    @Operation(summary = "更新智能标签分类父ID")
    @PostMapping("update_parent")
    public int updateParent(@RequestBody @Validated DataTagCategoryUpdateParentInputDto inputDto) {
        log.info("更新智能标签分类父ID，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagCategoryService.updateParent(inputDto);
    }

    @Operation(summary = "智能标签分类详情")
    @PostMapping("detail")
    public DataTagCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return tagCategoryService.detail(request);
    }

    @Override
    @GetMapping("list_all_by_type")
    public List<DataTagCategoryListOutputDto> listAllByType(@RequestParam("type") ProfileSubjectTypeEnum type) {
        return tagCategoryService.listAllByType(type);
    }

    @Override
    @GetMapping("tree_all_simple")
    public DataTagCategoryTreeSimpleOutputDto treeAllSimple(@RequestParam("type") ProfileSubjectTypeEnum type) {
        return tagCategoryService.treeAllSimple(type);
    }
}
