package com.machine.service.data.attachment.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.IDataAttachmentClient;
import com.machine.client.data.attachment.dto.input.DataAttachmentBindTableNameInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCreateTemporaryInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentUpdateInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.attachment.service.IDataAttachmentService;
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
@RequestMapping("server/data/attachment")
public class DataAttachmentServer implements IDataAttachmentClient {

    @Autowired
    private IDataAttachmentService attachmentService;

    @Override
    @PostMapping("create_temporary")
    public String createTemporary(@RequestBody @Validated DataAttachmentCreateTemporaryInputDto inputDto) {
        log.info("创建临时附件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attachmentService.createTemporary(inputDto);
    }

    @Override
    @PostMapping("update")
    public void update(@RequestBody @Validated DataAttachmentUpdateInputDto inputDto) {
        log.info("修改附件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        attachmentService.update(inputDto);
    }

    @Override
    public void bindTableName(DataAttachmentBindTableNameInputDto inputDto) {
        log.info("附件绑定业务表，inputDto={}", JSONUtil.toJsonStr(inputDto));
        attachmentService.bindTableName(inputDto);
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
    @PostMapping("select_page")
    public PageResponse<DataAttachmentListOutputDto> selectPage(@RequestBody @Validated DataAttachmentQueryPageInputDto inputDto) {
        Page<DataAttachmentListOutputDto> pageResult = attachmentService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
