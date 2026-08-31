package com.machine.service.iam.biam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.biam.user.dao.IBIamUserPermissionRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserPermissionRelationMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserPermissionRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BIamUserPermissionRelationDaoImpl implements IBIamUserPermissionRelationDao {

    @Autowired
    private BIamUserPermissionRelationMapper userPermissionRelationMapper;

    @Override
    public List<BIamUserPermissionRelationEntity> selectByPermissionId(String permissionId) {
        Wrapper<BIamUserPermissionRelationEntity> wrapper = new LambdaQueryWrapper<BIamUserPermissionRelationEntity>()
                .eq(BIamUserPermissionRelationEntity::getPermissionId, permissionId);
        return userPermissionRelationMapper.selectList(wrapper);
    }
}
