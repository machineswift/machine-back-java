package com.machine.service.data.filecenter.material.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.material.IDataMaterialReferenceClient;
import com.machine.client.data.filecenter.material.dto.input.DataMaterialReferenceQueryInputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialReferenceDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.filecenter.material.service.IDataMaterialReferenceService;
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
@RequestMapping("server/data/file/material/reference")
public class DataMaterialReferenceServer implements IDataMaterialReferenceClient {

    @Autowired
    private IDataMaterialReferenceService materialReferenceService;

    @Override
    @PostMapping("list_by_materialId")
    public List<DataMaterialReferenceDetailOutputDto> listByMaterialId(@RequestBody @Validated IdRequest request) {
        log.info("查询素材引用，materialId={}", request.getId());
        return materialReferenceService.listByMaterialId(request.getId());
    }

    @Override
    @PostMapping("list_by_materialIdSet")
    public Map<String, List<DataMaterialReferenceDetailOutputDto>> mapByMaterialIdSet(@RequestBody @Validated IdSetRequest request) {
        log.info("批量查询素材引用，idSet={}", JSONUtil.toJsonStr(request.getIdSet()));
        return materialReferenceService.mapByMaterialIdSet(request.getIdSet());
    }

    @Override
    @PostMapping("list_by_entity")
    public List<DataMaterialReferenceDetailOutputDto> listByEntity(@RequestBody @Validated DataMaterialReferenceQueryInputDto inputDto) {
        log.info("根据实体查询素材引用，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return materialReferenceService.listByEntity(
                inputDto.getEntity(),
                inputDto.getEntityId());
    }
}
