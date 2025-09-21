package com.machine.service.data.external.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.external.IDataExternalFieldDataClient;
import com.machine.client.data.external.dto.input.DataExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.service.IDataExternalFieldDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/external_field_data")
public class DataExternalFieldDataServer implements IDataExternalFieldDataClient {

    @Autowired
    private IDataExternalFieldDataService externalFieldDataService;


    @Override
    @PostMapping("create")
    public String create(DataExternalFieldDataCreateInputDto inputDto) {
        log.info("新增扩展数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return externalFieldDataService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(DataExternalFieldDataDeleteInputDto inputDto) {
        log.info("删除扩展数据，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return externalFieldDataService.delete(inputDto);
    }

    @Override
    public String getValue(DataExternalFieldDataGetValueInputDto inputDto) {
        return externalFieldDataService.getValue(inputDto);
    }
}
