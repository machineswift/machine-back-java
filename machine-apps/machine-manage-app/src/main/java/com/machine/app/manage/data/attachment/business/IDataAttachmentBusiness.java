package com.machine.app.manage.data.attachment.business;

import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentDetailResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentExpandListResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentQueryPageRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentUpdateRequestVo;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IDataAttachmentBusiness {

    String upload(DataAttachmentTypeEnum attachmentType,
                  MultipartFile file);

    String uploadImage(int thumbnailWeight,
                       int thumbnailHeight,
                       MultipartFile file);

    void update(DataAttachmentUpdateRequestVo request);

    String getPresignedUrl(String attachmentId);

    String getPresignedUrl(String attachmentId,
                           int expireSecond);

    String getThumbnailUrl(String attachmentId);

    String getThumbnailUrl(String attachmentId,
                           int expireSecond);

    DataAttachmentDetailResponseVo detail(IdRequest request);

    PageResponse<DataAttachmentExpandListResponseVo> pageExpand(DataAttachmentQueryPageRequestVo request);

}
