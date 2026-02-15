package com.machine.app.manage.data.file.attachment.business;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import org.springframework.web.multipart.MultipartFile;

public interface IDataAttachmentBusiness {

    String upload(ModuleEntityEnum entity,
                  String entityId,
                  String version,
                  MultipartFile file);

    String uploadImage(ModuleEntityEnum entity,
                       String entityId,
                       String version,
                       int thumbnailWeight,
                       int thumbnailHeight,
                       MultipartFile file);

    String getPresignedUrl(String attachmentId,
                           int expireSecond);

    String getThumbnailUrl(String attachmentId,
                           int expireSecond);

}
