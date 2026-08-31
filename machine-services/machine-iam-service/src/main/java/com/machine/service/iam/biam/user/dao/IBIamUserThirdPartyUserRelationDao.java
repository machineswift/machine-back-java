package com.machine.service.iam.biam.user.dao;

import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserThirdPartyUserRelationEntity;

public interface IBIamUserThirdPartyUserRelationDao {

    String insert(BIamUserThirdPartyUserRelationEntity entity);
}
