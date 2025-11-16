package com.machine.service.iam.user.dao;

import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.sdk.common.envm.iam.user.IamUserTypeEnum;
import com.machine.service.iam.user.dao.mapper.entity.IamUserTypeEntity;

import java.util.List;
import java.util.Set;

public interface IIamUserTypeDao {

    void insertOrUpdate(String userId,
                        IamUserTypeEnum userTypeEnum);

    boolean notExists(String userId,
                      IamUserTypeEnum userTypeEnum);

    boolean existsType(IamUserTypeExistsTypeInputDto inputDto);

    List<IamUserTypeEntity> selectByUserId(String userId);

    List<IamUserTypeEntity> selectByUserIds(Set<String> userIdSet);
}
