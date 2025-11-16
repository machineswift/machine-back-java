package com.machine.service.data.tag.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.IDataTagOptionClient;
import com.machine.client.data.tag.dto.input.*;
import com.machine.client.data.tag.dto.output.DataTagOptionDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagOptionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.tag.service.IDataTagOptionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/tag/option")
public class DataTagOptionServer implements IDataTagOptionClient {

    @Autowired
    private IDataTagOptionService tagOptionService;

    @Operation(summary = "创建智能标签选项")
    @PostMapping("create")
    public String create(@RequestBody @Validated DataTagOptionCreateInputDto inputDto) {
        log.info("创建智能标签选项，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagOptionService.create(inputDto);
    }

    @Operation(summary = "删除智能标签选项")
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除智能标签选项，request={}", JSONUtil.toJsonStr(request));
        return tagOptionService.delete(request);
    }

    @Operation(summary = "更新智能标签选项")
    @PostMapping("update")
    public int update(@RequestBody @Validated DataTagOptionUpdateInputDto inputDto) {
        log.info("更新智能标签选项，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagOptionService.update(inputDto);
    }

    @Override
    @PostMapping("update_code")
    public int updateCode(@RequestBody @Validated DataTagOptionUpdateCodeInputDto inputDto) {
        log.info("更新智能标签选项编码，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagOptionService.updateCode(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated DataTagOptionUpdateStatusInputDto inputDto) {
        log.info("更新智能标签选项状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagOptionService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_sort")
    public int updateSort(@RequestBody @Validated DataTagOptionUpdateSortInputDto inputDto) {
        log.info("更新智能标签选项排序，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagOptionService.updateSort(inputDto);
    }

    @Operation(summary = "智能标签选项详情")
    @PostMapping("detail")
    public DataTagOptionDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return tagOptionService.detail(request);
    }

    @Override
    @PostMapping("select_list")
    public List<DataTagOptionListOutputDto> selectList(@RequestBody @Validated DataTagOptionQueryListInputDto inputDto) {
        return tagOptionService.selectList(inputDto);
    }
}
