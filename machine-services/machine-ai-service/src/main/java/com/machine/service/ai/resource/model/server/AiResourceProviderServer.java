package com.machine.service.ai.resource.model.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.ai.resource.model.IAiResourceProviderClient;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderListInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.ai.resource.model.service.IAiResourceProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/ai/resource_center/provider")
public class AiResourceProviderServer implements IAiResourceProviderClient {

    @Autowired
    private IAiResourceProviderService aiResourceProviderService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody AiResourceProviderCreateInputDto inputDto) {
        log.info("资源中心新增厂商: {}", inputDto);
        return aiResourceProviderService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody IdRequest request) {
        log.info("资源中心删除厂商: {}", request.getId());
        return aiResourceProviderService.deleteById(request.getId());
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody AiResourceProviderUpdateInputDto inputDto) {
        log.info("资源中心修改厂商: {}", inputDto);
        return aiResourceProviderService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody AiResourceProviderUpdateStatusInputDto inputDto) {
        log.info("资源中心修改厂商状态: {}", JSONUtil.toJsonStr(inputDto));
        return aiResourceProviderService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("detail")
    public AiResourceProviderDetailOutputDto detail(@RequestBody IdRequest request) {
        return aiResourceProviderService.detail(request.getId());
    }

    @Override
    @PostMapping("list")
    public List<AiResourceProviderListOutputDto> list(@RequestBody AiResourceProviderListInputDto inputDto) {
        return aiResourceProviderService.list(inputDto.getStatus());
    }
}
