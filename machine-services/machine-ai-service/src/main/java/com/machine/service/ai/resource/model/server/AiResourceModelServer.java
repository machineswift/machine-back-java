package com.machine.service.ai.resource.model.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.ai.resource.model.IAiResourceModelClient;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.ai.resource.model.service.IAiResourceModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/ai/resource_center/model")
public class AiResourceModelServer implements IAiResourceModelClient {

    @Autowired
    private IAiResourceModelService aiResourceModelService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated AiResourceModelCreateInputDto inputDto) {
        log.info("资源中心新增模型: {}", inputDto);
        return aiResourceModelService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("资源中心删除模型: {}", request.getId());
        return aiResourceModelService.deleteById(request.getId());
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated AiResourceModelUpdateInputDto inputDto) {
        log.info("资源中心修改模型: {}", inputDto);
        return aiResourceModelService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated AiResourceModelUpdateStatusInputDto inputDto) {
        log.info("资源中心修改模型状态: {}", JSONUtil.toJsonStr(inputDto));
        return aiResourceModelService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("detail")
    public AiResourceModelDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return aiResourceModelService.detail(request.getId());
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<AiResourceModelListOutputDto> selectPage(@RequestBody @Validated AiResourceModelQueryPageInputDto inputDto) {
        Page<AiResourceModelListOutputDto> pageResult = aiResourceModelService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
