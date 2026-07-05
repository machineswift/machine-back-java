package com.machine.service.scm.category.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.category.IScmFrontCategoryClient;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.category.service.IScmFrontCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/scm/category/front_category")
public class ScmFrontCategoryServer implements IScmFrontCategoryClient {

    @Autowired
    private IScmFrontCategoryService frontCategoryService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmFrontCategoryCreateInputDto inputDto) {
        log.info("创建前台分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return frontCategoryService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除前台分类，id={}", request.getId());
        return frontCategoryService.deleteById(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmFrontCategoryUpdateInputDto inputDto) {
        log.info("修改前台分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return frontCategoryService.update(inputDto);
    }

    @Override
    @PostMapping("update_parent")
    public int updateParent(@RequestBody @Validated ScmFrontCategoryUpdateParentInputDto inputDto) {
        log.info("修改前台分类父ID，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return frontCategoryService.updateParent(inputDto);
    }


    @Override
    @PostMapping("detail")
    public ScmFrontCategoryDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return frontCategoryService.getById(request);
    }

    @Override
    @GetMapping("tree_all_simple")
    public ScmFrontCategoryTreeOutputDto treeAllSimple() {
        return frontCategoryService.treeAllSimple();
    }

    @Override
    @GetMapping("list_all")
    public List<ScmFrontCategoryListOutputDto> listAll() {
        return frontCategoryService.listAll();
    }
}