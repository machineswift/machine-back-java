package com.machine.app.manage.data.attachment.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.attachment.business.IDataAttachmentCategoryBusiness;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentCategoryDetailResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryCreateRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryUpdateRequestVo;
import com.machine.client.data.attachment.IDataAttachmentCategoryClient;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryCreateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateParentInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.redis.cache.RedisCacheDataAttachmentCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DataAttachmentCategoryBusinessImpl implements IDataAttachmentCategoryBusiness {

    @Autowired
    private RedisCacheDataAttachmentCategory attachmentCategoryCache;

    @Autowired
    private IDataAttachmentCategoryClient attachmentCategoryClient;

    @Override
    public String create(DataAttachmentCategoryCreateRequestVo request) {
        DataAttachmentCategoryCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAttachmentCategoryCreateInputDto.class);
        return attachmentCategoryClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        attachmentCategoryClient.delete(request);
    }

    @Override
    public void update(DataAttachmentCategoryUpdateRequestVo request) {
        DataAttachmentCategoryUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAttachmentCategoryUpdateInputDto.class);
        attachmentCategoryClient.update(inputDto);
    }

    @Override
    public void updateParent(DataAttachmentCategoryUpdateParentRequestVo request) {
        DataAttachmentCategoryUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAttachmentCategoryUpdateParentInputDto.class);
        attachmentCategoryClient.updateParent(inputDto);
    }

    @Override
    public DataAttachmentCategoryDetailResponseVo detail(IdRequest request) {
        DataAttachmentCategoryDetailOutputDto outputDto = attachmentCategoryClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataAttachmentCategoryDetailResponseVo.class);
    }

    @Override
    public DataAttachmentCategorySimpleTreeResponseVo treeAllSimple() {
        DataAttachmentCategoryTreeSimpleOutputDto allTreeOutputDto = attachmentCategoryCache.treeAllSimple();
        return JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutputDto), DataAttachmentCategorySimpleTreeResponseVo.class);
    }
}
