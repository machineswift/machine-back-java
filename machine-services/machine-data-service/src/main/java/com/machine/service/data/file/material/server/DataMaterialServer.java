package com.machine.service.data.file.material.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.material.IDataMaterialClient;
import com.machine.client.data.file.material.dto.input.DataMaterialCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateCategoryInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.data.file.material.service.IDataMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("server/data/file/material")
public class DataMaterialServer implements IDataMaterialClient {

    @Autowired
    private IDataMaterialService materialService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataMaterialCreateInputDto inputDto) {
        log.info("创建素材，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return materialService.create(inputDto);
    }

    @Override
    @PostMapping("update")
    public void update(@RequestBody @Validated DataMaterialUpdateInputDto inputDto) {
        log.info("修改素材，inputDto={}", JSONUtil.toJsonStr(inputDto));
        materialService.update(inputDto);
    }

    @Override
    @PostMapping("update_category")
    public void updateCategory(@RequestBody @Validated DataMaterialUpdateCategoryInputDto inputDto) {
        log.info("修改素材分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        materialService.updateCategory(inputDto);
    }

    @Override
    @PostMapping("get_by_id")
    public DataMaterialDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return materialService.getById(request);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataMaterialDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        List<DataMaterialDetailOutputDto> outputDtoList = materialService.listByIdSet(request);
        return outputDtoList.stream()
                .collect(Collectors.toMap(DataMaterialDetailOutputDto::getId, p -> p));
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<DataMaterialListOutputDto> selectPage(@RequestBody @Validated DataMaterialQueryPageInputDto inputDto) {
        Page<DataMaterialListOutputDto> pageResult = materialService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
