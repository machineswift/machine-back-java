package com.machine.service.iam.user.dao.impl;

import com.machine.service.iam.user.dao.IamThirdPartyUserDao;
import com.machine.service.iam.user.dao.mapper.IamThirdPartyUserMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamThirdPartyUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IamThirdPartyUserDaoImpl implements IamThirdPartyUserDao {

    @Autowired
   private IamThirdPartyUserMapper thirdPartyUserMapper;

    @Override
    public String insert(IamThirdPartyUserEntity entity) {
        thirdPartyUserMapper.insert(entity);
        return entity.getId();
    }
}
