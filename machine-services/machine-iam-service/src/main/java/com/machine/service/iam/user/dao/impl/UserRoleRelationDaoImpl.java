package com.machine.service.iam.user.dao.impl;

import com.machine.service.iam.user.dao.IUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserRoleRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRoleRelationDaoImpl implements IUserRoleRelationDao {

    @Autowired
    private IUserRoleRelationMapper userRoleRelationMapper;

    @Override
    public UserRoleRelationEntity detail(String id) {
        return userRoleRelationMapper.selectById(id);
    }
}
