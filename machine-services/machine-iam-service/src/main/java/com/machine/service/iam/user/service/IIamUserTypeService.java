package com.machine.service.iam.user.service;

import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.client.iam.user.dto.output.IamUserTypeOutputDto;
import com.machine.sdk.base.envm.iam.user.IamUserTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IIamUserTypeService {
    boolean existsType(IamUserTypeExistsTypeInputDto inputDto);

    List<IamUserTypeOutputDto> listByUserId(IdRequest request);

    List<IamUserTypeEnum> listTypeByUserId(IdRequest request);

    Map<String, List<IamUserTypeEnum>> mapTypeByUserIdSet(IdSetRequest request);
}
