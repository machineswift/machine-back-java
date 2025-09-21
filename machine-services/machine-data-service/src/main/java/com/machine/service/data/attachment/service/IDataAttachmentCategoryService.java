package com.machine.service.data.attachment.service;

import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryCreateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateParentInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryListOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IDataAttachmentCategoryService {

    String create(DataAttachmentCategoryCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataAttachmentCategoryUpdateInputDto inputDto);

    int updateParent(DataAttachmentCategoryUpdateParentInputDto inputDto);

    DataAttachmentCategoryDetailOutputDto detail(IdRequest request);

    List<DataAttachmentCategoryListOutputDto> listAll();

    DataAttachmentCategoryTreeSimpleOutputDto treeAllSimple();
}
