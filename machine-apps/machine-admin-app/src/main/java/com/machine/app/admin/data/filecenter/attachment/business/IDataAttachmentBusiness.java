package com.machine.app.admin.data.filecenter.attachment.business;

import org.springframework.web.multipart.MultipartFile;

public interface IDataAttachmentBusiness {

    /**
     * 先上传文件到临时存储
     */
    String uploadTemp(MultipartFile file);

    String getThumbnailUrl(String attachmentId,
                           int expireSecond);

}
