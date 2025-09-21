package com.machine.service.iam.user.dao.impl;

import com.machine.service.iam.user.dao.IamUserThirdPartyUserRelationDao;
import com.machine.service.iam.user.dao.mapper.IamUserThirdPartyUserRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserThirdPartyUserRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IamUserThirdPartyUserRelationDaoImpl implements IamUserThirdPartyUserRelationDao {

    @Autowired
   private IamUserThirdPartyUserRelationMapper userThirdPartyUserRelationMapper;

    @Override
    public String insert(IamUserThirdPartyUserRelationEntity entity) {
        userThirdPartyUserRelationMapper.insert(entity);
        return entity.getId();
    }
}
