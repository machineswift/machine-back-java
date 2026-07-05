package com.machine.service.scm.category.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.category.service.IScmBackCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/scm/category/back_category")
public class ScmBackCategoryServer implements IScmBackCategoryClient {

    @Autowired
    private IScmBackCategoryService backCategoryService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmBackCategoryCreateInputDto inputDto) {
        log.info("创建后台分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return backCategoryService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除后台分类，id={}", request.getId());
        return backCategoryService.deleteById(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmBackCategoryUpdateInputDto inputDto) {
        log.info("修改后台分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return backCategoryService.update(inputDto);
    }

    @Override
    @PostMapping("update_parent")
    public int updateParent(@RequestBody @Validated ScmBackCategoryUpdateParentInputDto inputDto) {
        log.info("修改后台分类父ID，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return backCategoryService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public ScmBackCategoryDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return backCategoryService.getById(request);
    }

    @Override
    @GetMapping("list_all")
    public List<ScmBackCategoryListOutputDto> listAll() {
        return backCategoryService.listAll();
    }

    @Override
    @GetMapping("tree_all_simple")
    public ScmBackCategoryTreeSimpleOutputDto treeAllSimple() {
        return backCategoryService.treeAllSimple();
    }

}