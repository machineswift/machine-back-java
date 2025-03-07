package com.machine.service.data.label.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.label.ILabelOptionClient;
import com.machine.client.data.label.dto.input.*;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.label.service.ILabelOptionService;
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
@RequestMapping("server/data/label_option")
public class DataLabelOptionServer implements ILabelOptionClient {

    @Autowired
    private ILabelOptionService labelOptionService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataLabelOptionCreateInputDto inputDto) {
        log.info("创建标签，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return labelOptionService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除标签，request={}", JSONUtil.toJsonStr(request));
        return labelOptionService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataLabelOptionUpdateInputDto inputDto) {
        log.info("修改标签，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return labelOptionService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated DataLabelOptionUpdateStatusInputDto inputDto) {
        log.info("修改标签状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return labelOptionService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataLabelOptionDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return labelOptionService.detail(request);
    }

    @Override
    @PostMapping("list_by_idSet")
    public List<DataLabelOptionListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request) {
        return labelOptionService.listByIdSet(request);
    }

    @Override
    @PostMapping("list_by_offset")
    public List<DataLabelOptionListOutputDto> listByOffset(@RequestBody @Validated DataLabelOptionQueryListOffsetInputDto inputDto) {
        return labelOptionService.listByOffset(inputDto);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataLabelOptionListOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return labelOptionService.mapByIdSet(request);
    }

    @Override
    @PostMapping("page")
    public PageResponse<DataLabelOptionListOutputDto> page(@RequestBody @Validated DataLabelOptionQueryPageInputDto inputDto) {
        Page<DataLabelOptionListOutputDto> pageResult = labelOptionService.page(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
