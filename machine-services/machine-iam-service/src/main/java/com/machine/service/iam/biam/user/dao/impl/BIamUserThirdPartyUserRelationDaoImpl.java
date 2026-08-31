package com.machine.service.iam.biam.user.dao.impl;

import com.machine.service.iam.biam.user.dao.IBIamUserThirdPartyUserRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserThirdPartyUserRelationMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserThirdPartyUserRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BIamUserThirdPartyUserRelationDaoImpl implements IBIamUserThirdPartyUserRelationDao {

    @Autowired
   private BIamUserThirdPartyUserRelationMapper userThirdPartyUserRelationMapper;

    @Override
    public String insert(BIamUserThirdPartyUserRelationEntity entity) {
        userThirdPartyUserRelationMapper.insert(entity);
        return entity.getId();
    }
}
