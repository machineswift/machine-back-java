package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.service.iam.user.dao.IUserTypeDao;
import com.machine.service.iam.user.dao.mapper.IUserTypeMapper;
import com.machine.service.iam.user.dao.mapper.entity.UserTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserTypeDaoImpl implements IUserTypeDao {

    @Autowired
    private IUserTypeMapper userTypeMapper;

    @Override
    public void insertOrUpdate(String userId,
                               UserTypeEnum userTypeEnum) {
        UserTypeEntity entity = new UserTypeEntity();
        entity.setUserId(userId);
        entity.setUserType(userTypeEnum);
        userTypeMapper.insertOrUpdate(entity);
    }

    @Override
    public boolean exists(String userId,
                          UserTypeEnum userType) {
        Wrapper<UserTypeEntity> deleteWrapper = new LambdaQueryWrapper<UserTypeEntity>()
                .eq(UserTypeEntity::getUserId, userId)
                .eq(UserTypeEntity::getUserType, userType);
        UserTypeEntity entity = userTypeMapper.selectOne(deleteWrapper);
        return null != entity;
    }

    @Override
    public boolean existsType(IamUserTypeExistsTypeInputDto inputDto) {
        return userTypeMapper.existsType(inputDto);
    }

    @Override
    public List<UserTypeEntity> selectByUserId(String userId) {
        Wrapper<UserTypeEntity> deleteWrapper = new LambdaQueryWrapper<UserTypeEntity>()
                .eq(UserTypeEntity::getUserId, userId);
        return userTypeMapper.selectList(deleteWrapper);
    }

    @Override
    public List<UserTypeEntity> selectByUserIds(Set<String> userIdSet) {
        Wrapper<UserTypeEntity> deleteWrapper = new LambdaQueryWrapper<UserTypeEntity>()
                .in(UserTypeEntity::getUserId, userIdSet);
        return userTypeMapper.selectList(deleteWrapper);
    }
}
