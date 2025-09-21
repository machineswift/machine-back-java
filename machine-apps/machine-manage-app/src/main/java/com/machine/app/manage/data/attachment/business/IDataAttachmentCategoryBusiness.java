package com.machine.app.manage.data.attachment.business;

import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentCategoryDetailResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryCreateRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentCategoryUpdateRequestVo;
import com.machine.sdk.common.model.request.IdRequest;


public interface IDataAttachmentCategoryBusiness {

    String create(DataAttachmentCategoryCreateRequestVo request);

    void delete(IdRequest request);

    void update(DataAttachmentCategoryUpdateRequestVo request);

    void updateParent(DataAttachmentCategoryUpdateParentRequestVo request);

    DataAttachmentCategoryDetailResponseVo detail(IdRequest request);

    DataAttachmentCategorySimpleTreeResponseVo treeAllSimple();
}
