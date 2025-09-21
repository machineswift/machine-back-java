package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.user.dao.IIamUserPermissionRelationDao;
import com.machine.service.iam.user.dao.mapper.IamUserPermissionRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserPermissionRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IamUserPermissionRelationDaoImpl implements IIamUserPermissionRelationDao {

    @Autowired
    private IamUserPermissionRelationMapper userPermissionRelationMapper;

    @Override
    public List<IamUserPermissionRelationEntity> selectByPermissionId(String permissionId) {
        Wrapper<IamUserPermissionRelationEntity> queryWrapper = new LambdaQueryWrapper<IamUserPermissionRelationEntity>()
                .eq(IamUserPermissionRelationEntity::getPermissionId, permissionId);
        return userPermissionRelationMapper.selectList(queryWrapper);
    }
}
