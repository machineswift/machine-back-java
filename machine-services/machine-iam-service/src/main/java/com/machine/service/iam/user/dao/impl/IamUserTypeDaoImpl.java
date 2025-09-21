package com.machine.service.iam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import com.machine.service.iam.user.dao.IIamUserTypeDao;
import com.machine.service.iam.user.dao.mapper.IamUserTypeMapper;
import com.machine.service.iam.user.dao.mapper.entity.IamUserTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class IamUserTypeDaoImpl implements IIamUserTypeDao {

    @Autowired
    private IamUserTypeMapper userTypeMapper;

    @Override
    public void insertOrUpdate(String userId,
                               IamUserTypeEnum userTypeEnum) {
        IamUserTypeEntity entity = new IamUserTypeEntity();
        entity.setUserId(userId);
        entity.setUserType(userTypeEnum);
        userTypeMapper.insertOrUpdate(entity);
    }

    @Override
    public boolean notExists(String userId,
                             IamUserTypeEnum userType) {
        Wrapper<IamUserTypeEntity> wrapper = new LambdaQueryWrapper<IamUserTypeEntity>()
                .eq(IamUserTypeEntity::getUserId, userId)
                .eq(IamUserTypeEntity::getUserType, userType);
        return !userTypeMapper.exists(wrapper);
    }

    @Override
    public boolean existsType(IamUserTypeExistsTypeInputDto inputDto) {
        return userTypeMapper.existsType(inputDto);
    }

    @Override
    public List<IamUserTypeEntity> selectByUserId(String userId) {
        Wrapper<IamUserTypeEntity> wrapper = new LambdaQueryWrapper<IamUserTypeEntity>()
                .eq(IamUserTypeEntity::getUserId, userId);
        return userTypeMapper.selectList(wrapper);
    }

    @Override
    public List<IamUserTypeEntity> selectByUserIds(Set<String> userIdSet) {
        Wrapper<IamUserTypeEntity> wrapper = new LambdaQueryWrapper<IamUserTypeEntity>()
                .in(IamUserTypeEntity::getUserId, userIdSet);
        return userTypeMapper.selectList(wrapper);
    }
}
