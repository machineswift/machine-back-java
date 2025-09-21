package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.IamThirdPartyUserEntity;

public interface IamThirdPartyUserDao {

    String insert(IamThirdPartyUserEntity entity);
}
