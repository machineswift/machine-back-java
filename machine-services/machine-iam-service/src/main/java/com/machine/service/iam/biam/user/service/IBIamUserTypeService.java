package com.machine.service.iam.biam.user.service;

import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserTypeOutputDto;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IBIamUserTypeService {
    boolean existsType(BIamUserTypeExistsTypeInputDto inputDto);

    List<BIamUserTypeOutputDto> listByUserId(IdRequest request);

    List<BIamUserTypeEnum> listTypeByUserId(IdRequest request);

    Map<String, List<BIamUserTypeEnum>> mapTypeByUserIdSet(IdSetRequest request);
}
