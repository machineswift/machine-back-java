package com.machine.service.data.attachment.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.attachment.IDataAttachmentCategoryClient;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryCreateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateParentInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryListOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.attachment.service.IDataAttachmentCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/attachment_category")
public class DataAttachmentCategoryServer implements IDataAttachmentCategoryClient {

    @Autowired
    private IDataAttachmentCategoryService attachmentCategoryService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataAttachmentCategoryCreateInputDto inputDto) {
        log.info("创建附件分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attachmentCategoryService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除附件分类，request={}", JSONUtil.toJsonStr(request));
        return attachmentCategoryService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataAttachmentCategoryUpdateInputDto inputDto) {
        log.info("修改附件分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attachmentCategoryService.update(inputDto);
    }

    @Override
    @PostMapping("updateParent")
    public int updateParent(@RequestBody @Validated DataAttachmentCategoryUpdateParentInputDto inputDto) {
        log.info("修改父附件分类，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attachmentCategoryService.updateParent(inputDto);
    }

    @Override
    @PostMapping("detail")
    public DataAttachmentCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return attachmentCategoryService.detail(request);
    }

    @Override
    @GetMapping("list_all")
    public List<DataAttachmentCategoryListOutputDto> listAll() {
        return attachmentCategoryService.listAll();
    }

    @Override
    @GetMapping("tree_all_simple")
    public DataAttachmentCategoryTreeSimpleOutputDto treeAllSimple() {
        return attachmentCategoryService.treeAllSimple();
    }
}
