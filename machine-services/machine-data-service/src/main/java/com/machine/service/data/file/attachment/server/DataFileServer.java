package com.machine.service.data.file.attachment.server;

import com.machine.client.data.file.attachment.IDataFileClient;
import com.machine.client.data.file.attachment.dto.output.DataFileDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.file.attachment.service.IDataFileService;
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
@RequestMapping("server/data/file/file")
public class DataFileServer implements IDataFileClient {

    @Autowired
    private IDataFileService fileService;

    @Override
    @PostMapping("get_by_id")
    public DataFileDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return fileService.getById(request);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataFileDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        List<DataFileDetailOutputDto> outputDtoList = fileService.listByIdSet(request);
        return outputDtoList.stream()
                .collect(Collectors.toMap(DataFileDetailOutputDto::getId, p -> p));
    }

}
