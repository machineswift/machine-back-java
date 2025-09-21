package com.machine.app.manage.data.attachment.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.attachment.business.IDataAttachmentCategoryBusiness;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentCategoryDetailResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryCreateRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】附件分类模块")
@RestController
@RequestMapping("manage/data/attachment_category")
public class DataAttachmentCategoryController {

    @Autowired
    private IDataAttachmentCategoryBusiness attachmentCategoryBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated DataAttachmentCategoryCreateRequestVo request) {
        log.info("创建附件分类，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(attachmentCategoryBusiness.create(request));
    }

    @Operation(summary = "删除")
    @PostMapping("delete")
    public void delete(@RequestBody @Validated IdRequest request) {
        log.info("删除附件分类，request={}", JSONUtil.toJsonStr(request));
        attachmentCategoryBusiness.delete(request);
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated DataAttachmentCategoryUpdateRequestVo request) {
        log.info("修改附件分类，request={}", JSONUtil.toJsonStr(request));
        attachmentCategoryBusiness.update(request);
    }

    @Operation(summary = "修改父ID")
    @PostMapping("update_parent")
    public void updateParent(@RequestBody @Validated DataAttachmentCategoryUpdateParentRequestVo request) {
        log.info("修改父附件分类，request={}", JSONUtil.toJsonStr(request));
        attachmentCategoryBusiness.updateParent(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public DataAttachmentCategoryDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return attachmentCategoryBusiness.detail(request);
    }

    @Operation(summary = "树(应用于组件弹窗)")
    @GetMapping("tree_simple")
    public DataAttachmentCategorySimpleTreeResponseVo treeAllSimple() {
        return attachmentCategoryBusiness.treeAllSimple();
    }
}