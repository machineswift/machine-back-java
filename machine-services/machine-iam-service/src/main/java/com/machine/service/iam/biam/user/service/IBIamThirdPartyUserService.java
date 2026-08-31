package com.machine.service.iam.biam.user.service;

import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserBindInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserCreateInputDto;

public interface IBIamThirdPartyUserService {

    String create(BIamThirdPartyUserCreateInputDto inputDto);

    void bind(BIamThirdPartyUserBindInputDto inputDto);
}
