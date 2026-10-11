package com.machine.service.data.brand.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.brand.IDataBrandCategoryClient;
import com.machine.client.data.brand.dto.input.DataBrandCategoryCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateParentInputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryListOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.data.brand.service.IDataBrandCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/brand/category")
public class DataBrandCategoryServer implements IDataBrandCategoryClient {

    @Autowired
    private IDataBrandCategoryService brandCategoryService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataBrandCategoryCreateInputDto inputDto) {
        log.info("新增品牌分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return brandCategoryService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除品牌分类，request={}", JSONUtil.toJsonStr(request));
        return brandCategoryService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataBrandCategoryUpdateInputDto inputDto) {
        log.info("修改品牌分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return brandCategoryService.update(inputDto);
    }

    @Override
    @PostMapping("updateParent")
    public int updateParent(@RequestBody @Validated DataBrandCategoryUpdateParentInputDto inputDto) {
        log.info("修改品牌分类父节点，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return brandCategoryService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataBrandCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return brandCategoryService.detail(request);
    }

    @Override
    @GetMapping("list_all")
    public List<DataBrandCategoryListOutputDto> listAll() {
        return brandCategoryService.listAll();
    }

    @Override
    @GetMapping("tree_all_simple")
    public Tuples.Tuple2<String, DataBrandCategoryTreeSimpleOutputDto> treeAllSimple() {
        return brandCategoryService.treeAllSimple();
    }
}
