package com.machine.app.admin.data.filecenter.attachment.business;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDataAttachmentBusiness {

    String uploadTemp(MultipartFile file);

    String thumbnail(String attachmentId);

    List<String> batchThumbnail(String attachmentId);

    String preview(String attachmentId);

    List<String> batchPreview(String attachmentId);
    
    String download(String attachmentId);

    List<String> batchDownload(String attachmentId);
}
