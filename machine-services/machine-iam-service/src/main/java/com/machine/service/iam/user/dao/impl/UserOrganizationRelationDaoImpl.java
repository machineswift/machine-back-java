package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.iam.UserDepartmentRelationTypeEnum;
import com.machine.service.iam.user.dao.IUserOrganizationRelationDao;
import com.machine.service.iam.user.dao.mapper.IUserOrganizationRelationMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserPermissionRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserOrganizationRelationDaoImpl implements IUserOrganizationRelationDao {

    @Autowired
    private IUserOrganizationRelationMapper userOrganizationRelationMapper;

    @Override
    public int insert(String organizationId,
                      String userId,
                      UserDepartmentRelationTypeEnum type) {
        UserOrganizationRelationEntity entity = new UserOrganizationRelationEntity();
        entity.setOrganizationId(organizationId);
        entity.setUserId(userId);
        entity.setType(type);
        return userOrganizationRelationMapper.insert(entity);
    }

    @Override
    public int deleteLeaderByOrganizationId(String organizationId) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .eq(UserOrganizationRelationEntity::getOrganizationId, organizationId)
                .eq(UserOrganizationRelationEntity::getType,UserDepartmentRelationTypeEnum.LEADER);
        return userOrganizationRelationMapper.delete(queryWrapper);
    }

    @Override
    public UserOrganizationRelationEntity detail(String id) {
        return userOrganizationRelationMapper.selectById(id);
    }

    @Override
    public UserOrganizationRelationEntity selectLeaderByOrganizationId(String organizationId) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .eq(UserOrganizationRelationEntity::getOrganizationId, organizationId);
        return userOrganizationRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<UserOrganizationRelationEntity> mapLeaderByOrganizationIdSet(Set<String> organizationIdIdSet) {
        Wrapper<UserOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<UserOrganizationRelationEntity>()
                .in(UserOrganizationRelationEntity::getOrganizationId, organizationIdIdSet)
                .eq(UserOrganizationRelationEntity::getType, UserDepartmentRelationTypeEnum.LEADER);
        return userOrganizationRelationMapper.selectList(queryWrapper);
    }
}
