package com.machine.service.iam.biam.user.dao;

import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserTypeEntity;

import java.util.List;
import java.util.Set;

public interface IBIamUserTypeDao {

    void insertOrUpdate(String userId,
                        BIamUserTypeEnum userTypeEnum);

    boolean notExists(String userId,
                      BIamUserTypeEnum userTypeEnum);

    boolean existsType(BIamUserTypeExistsTypeInputDto inputDto);

    List<BIamUserTypeEntity> selectByUserId(String userId);

    List<BIamUserTypeEntity> selectByUserIds(Set<String> userIdSet);
}
