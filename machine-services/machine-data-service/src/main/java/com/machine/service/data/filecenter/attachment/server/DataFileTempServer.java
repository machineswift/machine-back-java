package com.machine.service.data.filecenter.attachment.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.data.filecenter.attachment.service.IDataFileTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/file_center/file_temp")
public class DataFileTempServer implements IDataFileTempClient {

    @Autowired
    private IDataFileTempService dataFileTempService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataFileTempCreateInputDto inputDto) {
        log.info("创建临时文件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return dataFileTempService.create(inputDto);
    }

    @Override
    @PostMapping("get_by_id")
    public DataFileTempDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return dataFileTempService.getById(request.getId());
    }
}
