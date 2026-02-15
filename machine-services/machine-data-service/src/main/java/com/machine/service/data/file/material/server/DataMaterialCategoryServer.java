package com.machine.service.data.file.material.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.material.IDataMaterialCategoryClient;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryUpdateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryUpdateParentInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryListOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.file.material.service.IDataMaterialCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/file/material_category")
public class DataMaterialCategoryServer implements IDataMaterialCategoryClient {

    @Autowired
    private IDataMaterialCategoryService materialCategoryService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataMaterialCategoryCreateInputDto inputDto) {
        log.info("创建素材分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return materialCategoryService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除素材分类，request={}", JSONUtil.toJsonStr(request));
        return materialCategoryService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataMaterialCategoryUpdateInputDto inputDto) {
        log.info("修改素材分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return materialCategoryService.update(inputDto);
    }

    @Override
    @PostMapping("updateParent")
    public int updateParent(@RequestBody @Validated DataMaterialCategoryUpdateParentInputDto inputDto) {
        log.info("修改父素材分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return materialCategoryService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataMaterialCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return materialCategoryService.detail(request);
    }

    @Override
    @GetMapping("list_all")
    public List<DataMaterialCategoryListOutputDto> listAll() {
        return materialCategoryService.listAll();
    }

    @Override
    @GetMapping("tree_all_simple")
    public DataMaterialCategoryTreeSimpleOutputDto treeAllSimple() {
        return materialCategoryService.treeAllSimple();
    }
}
