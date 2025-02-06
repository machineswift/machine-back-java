package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;

public interface IUserRoleRelationDao {
    UserRoleRelationEntity detail(String id);
}
