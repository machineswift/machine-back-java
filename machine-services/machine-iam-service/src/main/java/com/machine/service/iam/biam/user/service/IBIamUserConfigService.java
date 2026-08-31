package com.machine.service.iam.biam.user.service;

import com.machine.client.iam.biam.user.dto.input.BIamUserConfigGetInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamUserConfigSaveInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserConfigOutputDto;

public interface IBIamUserConfigService {

    BIamUserConfigOutputDto getByKey(BIamUserConfigGetInputDto inputDto);

    void save(BIamUserConfigSaveInputDto inputDto);

}
