package com.machine.service.data.tag.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.IDataTagClient;
import com.machine.client.data.tag.dto.input.DataTagCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateCategoryDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateInputDto;
import com.machine.client.data.tag.dto.output.DataTagDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.tag.service.IDataTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/data/tag")
public class DataTagServer implements IDataTagClient {

    @Autowired
    private IDataTagService tagService;

    @PostMapping("create")
    public String create(@RequestBody @Validated DataTagCreateInputDto inputDto) {
        log.info("创建智能标签，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagService.create(inputDto);
    }

    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除智能标签，request={}", JSONUtil.toJsonStr(request));
        return tagService.delete(request);
    }

    @PostMapping("update")
    public int update(@RequestBody @Validated DataTagUpdateInputDto inputDto) {
        log.info("更新智能标签，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagService.update(inputDto);
    }

    @Override
    @PostMapping("update_category")
    public int updateCategory(@RequestBody @Validated DataTagUpdateCategoryDto inputDto) {
        log.info("修改智能标签关联分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return tagService.updateCategory(inputDto);
    }

    @PostMapping("detail")
    public DataTagDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return tagService.detail(request);
    }

    @PostMapping("select_page")
    public PageResponse<DataTagListOutputDto> selectPage(@RequestBody @Validated DataTagQueryPageInputDto inputDto) {
        Page<DataTagListOutputDto> pageResult = tagService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
