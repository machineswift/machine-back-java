package com.machine.service.iam.user.dao.impl;

import com.machine.service.iam.user.dao.IUserOrganizationRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserOrganizationRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserOrganizationRelationDaoImpl implements IUserOrganizationRelationDao {

    @Autowired
    private IUserOrganizationRelationMapper userOrganizationRelationMapper;

    @Override
    public UserOrganizationRelationEntity detail(String id) {
        return userOrganizationRelationMapper.selectById(id);
    }
}
