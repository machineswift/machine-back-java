package com.machine.service.iam.biam.user.service.impl;

import com.machine.client.iam.biam.user.dto.input.BIamUserConfigGetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigSaveInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserConfigOutputDto;
import com.machine.service.iam.biam.user.dao.IBIamUserConfigDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserConfigEntity;
import com.machine.service.iam.biam.user.service.IBIamUserConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BIamUserConfigServiceImpl implements IBIamUserConfigService {

    @Autowired
    private IBIamUserConfigDao userConfigDao;

    @Override
    public BIamUserConfigOutputDto getByKey(BIamUserConfigGetInputDto inputDto) {
        BIamUserConfigEntity entity = userConfigDao.selectByUserIdAndKey(inputDto.getUserId(), inputDto.getConfigKey());
        if (entity == null) {
            return null;
        }

        BIamUserConfigOutputDto outputDto = new BIamUserConfigOutputDto();
        outputDto.setConfigKey(entity.getConfigKey());
        outputDto.setConfigValue(entity.getConfigValue());
        return outputDto;
    }

    @Override
    public void save(BIamUserConfigSaveInputDto inputDto) {
        userConfigDao.insertOrUpdate(inputDto.getUserId(), inputDto.getConfigKey(), inputDto.getConfigValue());
    }
}
