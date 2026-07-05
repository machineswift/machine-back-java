package com.machine.service.data.filecenter.attachment.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.IDataAttachmentVersionClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionRollbackInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentVersionDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentVersionService;
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
@RequestMapping("server/data/file_center/attachment_version")
public class DataAttachmentVersionServer implements IDataAttachmentVersionClient {

    @Autowired
    private IDataAttachmentVersionService attachmentVersionService;

    @Override
    @PostMapping("update")
    public void update(@RequestBody @Validated DataAttachmentVersionUpdateInputDto inputDto) {
        log.info("修改附件版本，inputDto={}", JSONUtil.toJsonStr(inputDto));
        attachmentVersionService.update(inputDto);
    }

    @Override
    @PostMapping("rollback")
    public void rollback(DataAttachmentVersionRollbackInputDto inputDto) {
        log.info("回滚附件版本，inputDto={}", JSONUtil.toJsonStr(inputDto));
        attachmentVersionService.rollback(inputDto);
    }

    @Override
    @PostMapping("get_by_id")
    public DataAttachmentVersionDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        return attachmentVersionService.getById(request.getId());
    }

    @Override
    public Map<String, DataAttachmentVersionDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<DataAttachmentVersionDetailOutputDto> outputDtoList = attachmentVersionService.listByIdSet(request);
        return outputDtoList.stream()
                .collect(Collectors.toMap(DataAttachmentVersionDetailOutputDto::getId, p -> p));
    }

}
