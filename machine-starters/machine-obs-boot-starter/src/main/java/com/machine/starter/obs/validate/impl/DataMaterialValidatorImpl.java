package com.machine.starter.obs.validate.impl;

import com.machine.client.data.filecenter.material.IDataMaterialClient;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialDetailOutputDto;
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
public class DataMaterialValidatorImpl implements IModuleEntityValidator {

    private final static Set<String> ATTACHMENT_GROUP_SET = Set.of(ATTACHMENT_DEFAULT_GROUP);

    @Autowired
    private IDataMaterialClient dataMaterialClient;

    @Override
    public ModuleEntityEnum getSupportedEnum() {
        return ModuleEntityEnum.DATA_MATERIAL;
    }

    @Override
    public void validateAttachmentGroup(String attachmentGroup) {
        if (ATTACHMENT_GROUP_SET.contains(attachmentGroup)) {
            return;
        }

        log.error("素材分组不支持,attachmentGroup:{}", attachmentGroup);
        throw new DataObsBusinessException("data.obs.validate.DATA_MATERIAL.attachmentGroupNotSupported", "素材分组不支持");
    }

    @Override
    public void validateEntityId(String entityId) {
        DataMaterialDetailOutputDto outputDto = dataMaterialClient.getById(new IdRequest(entityId));
        if (null == outputDto) {
            throw new DataObsBusinessException("data.obs.validate.DATA_MATERIAL.entityNotExists", "素材不存在");
        }
    }

}
