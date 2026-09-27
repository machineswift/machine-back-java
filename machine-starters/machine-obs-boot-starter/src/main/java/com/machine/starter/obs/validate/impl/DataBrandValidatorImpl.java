package com.machine.starter.obs.validate.impl;

import com.machine.client.data.brand.IDataBrandClient;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.exception.data.DataObsBusinessException;
import com.machine.sdk.base.model.dto.IdNameDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.obs.validate.IModuleEntityValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.machine.starter.obs.constant.ObsFileConstant.ATTACHMENT_DEFAULT_GROUP;

@Slf4j
@Component
public class DataBrandValidatorImpl implements IModuleEntityValidator {

    private final static Set<String> ATTACHMENT_GROUP_SET = Set.of(ATTACHMENT_DEFAULT_GROUP);

    @Autowired
    private IDataBrandClient dataBrandClient;

    @Override
    public ModuleEntityEnum getSupportedEnum() {
        return ModuleEntityEnum.DATA_BRAND;
    }

    @Override
    public void validateAttachmentGroup(String attachmentGroup) {
        if (ATTACHMENT_GROUP_SET.contains(attachmentGroup)) {
            return;
        }

        log.error("品牌LOGO分组不支持,attachmentGroup:{}", attachmentGroup);
        throw new DataObsBusinessException("data.obs.validate.DATA_BRAND.attachmentGroupNotSupported", "品牌LOGO分组不支持");
    }

    @Override
    public void validateEntityId(String entityId) {
        boolean exists = dataBrandClient.exists(new IdRequest(entityId));
        if (!exists) {
            throw new DataObsBusinessException("data.obs.validate.DATA_BRAND.entityNotExists", "品牌不存在");
        }
    }

    @Override
    public IdNameDto getNameInfo(String entityId) {
        DataBrandDetailOutputDto outputDto = dataBrandClient.detail(new IdRequest(entityId));
        if (null == outputDto) {
            return null;
        }

        return new IdNameDto(entityId, outputDto.getName());
    }

}
