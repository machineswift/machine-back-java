package com.machine.service.data.external.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.external.IExternalFieldDataClient;
import com.machine.client.data.external.dto.input.ExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.service.IExternalFieldDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/external_field_data")
public class ExternalFieldDataServer implements IExternalFieldDataClient {

    @Autowired
    private IExternalFieldDataService externalFieldDataService;


    @Override
    @PostMapping("create")
    public String create(ExternalFieldDataCreateInputDto inputDto) {
        log.info("新增扩展数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return externalFieldDataService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(ExternalFieldDataDeleteInputDto inputDto) {
        log.info("删除扩展数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return externalFieldDataService.delete(inputDto);
    }

    @Override
    public String getValue(ExternalFieldDataGetValueInputDto inputDto) {
        return externalFieldDataService.getValue(inputDto);
    }
}
