package com.machine.service.iam.user.dao;

import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.service.iam.user.dao.mapper.entity.UserTypeEntity;

import java.util.List;
import java.util.Set;

public interface IUserTypeDao {

    void insertOrUpdate(String userId,
                        UserTypeEnum userTypeEnum);

    boolean exists(String userId,
                   UserTypeEnum userTypeEnum);

    boolean existsType(IamUserTypeExistsTypeInputDto inputDto);

    List<UserTypeEntity> selectByUserId(String userId);

    List<UserTypeEntity> selectByUserIds(Set<String> userIdSet);
}
