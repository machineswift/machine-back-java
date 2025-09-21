package com.machine.service.iam.user.service;

import com.machine.client.iam.user.dto.input.IamThirdPartyUserBindInputDto;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserCreateInputDto;

public interface IIamThirdPartyUserService {

    String create(IamThirdPartyUserCreateInputDto inputDto);

    void bind(IamThirdPartyUserBindInputDto inputDto);
}
