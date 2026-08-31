package com.machine.service.iam.biam.user.dao.impl;

import com.machine.service.iam.biam.user.dao.IBIamThirdPartyUserDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamThirdPartyUserMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamThirdPartyUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BIamThirdPartyUserDaoImpl implements IBIamThirdPartyUserDao {

    @Autowired
   private BIamThirdPartyUserMapper thirdPartyUserMapper;

    @Override
    public String insert(BIamThirdPartyUserEntity entity) {
        thirdPartyUserMapper.insert(entity);
        return entity.getId();
    }
}
