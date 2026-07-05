package com.machine.service.data.filecenter.attachment.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.IDataAttachmentOperationLogClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.service.data.filecenter.attachment.service.IDataAttachmentOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("server/data/file_center/attachment_operation_log")
public class DataAttachmentOperationLogServer implements IDataAttachmentOperationLogClient {

    @Autowired
    private IDataAttachmentOperationLogService attachmentOperationLogService;

    @Override
    @PostMapping("create")
    public void create(@RequestBody @Validated DataAttachmentOperationLogCreateInputDto inputDto) {
        log.info("记录附件操作日志，inputDto={}", JSONUtil.toJsonStr(inputDto));
        attachmentOperationLogService.create(inputDto);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<DataAttachmentOperationLogListOutputDto> selectPage(@RequestBody @Validated DataAttachmentOperationLogPageInputDto inputDto) {
        Page<DataAttachmentOperationLogListOutputDto> pageResult = attachmentOperationLogService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }
}
