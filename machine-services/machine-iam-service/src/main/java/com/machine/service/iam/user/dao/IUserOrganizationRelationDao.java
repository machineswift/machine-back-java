package com.machine.service.iam.user.dao;

import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;

public interface IUserOrganizationRelationDao {

    UserOrganizationRelationEntity detail(String id);

}
