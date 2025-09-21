package com.machine.service.data.brand.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.IDataBrandClient;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.brand.service.IDataBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/data/brand")
public class DataBrandServer implements IDataBrandClient {

    @Autowired
    private IDataBrandService brandService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataBrandCreateInputDto inputDto) {
        log.info("创建品牌， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return brandService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除品牌， inputDto={}", JSONUtil.toJsonStr(request));
        return brandService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataBrandUpdateInputDto inputDto) {
        log.info("修改品牌， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return brandService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated DataBrandUpdateStatusInputDto inputDto) {
        log.info("修改品牌状态， inputDto={}", JSONUtil.toJsonStr(inputDto));
        return brandService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataBrandDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return brandService.detail(request);
    }

    @Override
    @PostMapping("page")
    public PageResponse<DataBrandListOutputDto> page(@RequestBody @Validated DataBrandQueryPageInputDto inputDto) {
        Page<DataBrandListOutputDto> pageResult = brandService.page(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataBrandDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return brandService.mapByIdSet(request);
    }
}
