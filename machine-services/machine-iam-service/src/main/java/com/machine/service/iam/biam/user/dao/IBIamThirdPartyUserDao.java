package com.machine.service.iam.biam.user.dao;

import com.machine.service.iam.biam.user.dao.mapper.entity.BIamThirdPartyUserEntity;

public interface IBIamThirdPartyUserDao {

    String insert(BIamThirdPartyUserEntity entity);
}
