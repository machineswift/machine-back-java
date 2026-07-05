package com.machine.service.data.filecenter.attachment.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentService;
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
@RequestMapping("server/data/file_center/attachment")
public class DataAttachmentServer implements IDataAttachmentClient {

    @Autowired
    private IDataAttachmentService attachmentService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataAttachmentCreateInputDto inputDto) {
        log.info("创建附件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attachmentService.create(inputDto);
    }

    @Override
    @PostMapping("get_by_id")
    public DataAttachmentDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return attachmentService.getById(request);
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, DataAttachmentDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        List<DataAttachmentDetailOutputDto> outputDtoList = attachmentService.listByIdSet(request);
        return outputDtoList.stream()
                .collect(Collectors.toMap(DataAttachmentDetailOutputDto::getId, p -> p));
    }

    @Override
    @PostMapping("get_by_attachmentId")
    public DataAttachmentWithCurrentFileInfoOutputDto getCurrentByAttachmentId(@RequestBody @Validated IdRequest request) {
        return attachmentService.getCurrentByAttachmentId(request);
    }

    @Override
    @PostMapping("map_by_attachmentIdSet")
    public Map<String, DataAttachmentWithCurrentFileInfoOutputDto> mapCurrentByAttachmentIdSet(@RequestBody @Validated IdSetRequest request) {
        return attachmentService.mapCurrentByAttachmentIdSet(request);
    }
}
