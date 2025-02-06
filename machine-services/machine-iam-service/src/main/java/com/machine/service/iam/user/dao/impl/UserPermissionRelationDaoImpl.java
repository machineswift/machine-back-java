package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.user.dao.IUserPermissionRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserPermissionRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserPermissionRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserPermissionRelationDaoImpl implements IUserPermissionRelationDao {

    @Autowired
    private IUserPermissionRelationMapper userPermissionRelationMapper;

    @Override
    public List<UserPermissionRelationEntity> selectByPermissionId(String permissionId) {
        Wrapper<UserPermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<UserPermissionRelationEntity>()
                .eq(UserPermissionRelationEntity::getPermissionId, permissionId);
        return userPermissionRelationMapper.selectList(queryWrapper);
    }
}
