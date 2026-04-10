package com.machine.starter.obs.validate.impl;

import com.machine.client.data.file.material.IDataMaterialClient;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.exception.data.DataFileBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.obs.validate.IModuleEntityValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataMaterialValidateImpl implements IModuleEntityValidate {

    @Autowired
    private IDataMaterialClient dataMaterialClient;

    @Override
    public ModuleEntityEnum getSupportedEnum() {
        return ModuleEntityEnum.DATA_MATERIAL;
    }

    @Override
    public void validate(String entityId) {
        DataMaterialDetailOutputDto outputDto = dataMaterialClient.getById(new IdRequest(entityId));
        if (null == outputDto) {
            throw new DataFileBusinessException("data.obs.validate.DATA_MATERIAL.notExists", "素材不存在");
        }
    }

}
