package com.machine.starter.obs.validate.impl;

import com.machine.client.data.filecenter.download.IDataDownloadClient;
import com.machine.client.data.filecenter.download.dto.output.DataDownloadDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.exception.data.DataObsBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.obs.validate.IModuleEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.machine.starter.obs.constant.ObsFileConstant.ATTACHMENT_DEFAULT_GROUP;

@Slf4j
@Component
public class DataDownloadValidatorImpl implements IModuleEntityValidator {

    private final static Set<String> ATTACHMENT_GROUP_SET = Set.of(ATTACHMENT_DEFAULT_GROUP);

    @Autowired
    private IDataDownloadClient dataDownloadClient;

    @Override
    public ModuleEntityEnum getSupportedEnum() {
        return ModuleEntityEnum.DATA_DOWNLOAD;
    }

    @Override
    public void validateAttachmentGroup(String attachmentGroup) {
        if (ATTACHMENT_GROUP_SET.contains(attachmentGroup)) {
            return;
        }

        log.error("下载中心分组不支持,attachmentGroup:{}", attachmentGroup);
        throw new DataObsBusinessException("data.obs.validate.DATA_DOWNLOAD.attachmentGroupNotSupported", "下载中心分组不支持");
    }

    @Override
    public void validateEntityId(String entityId) {
        DataDownloadDetailOutputDto outputDto = dataDownloadClient.getById(new IdRequest(entityId));
        if (null == outputDto) {
            throw new DataObsBusinessException("data.obs.validate.DATA_DOWNLOAD.entityNotExists", "下载中心文件不存在");
        }
    }

}
