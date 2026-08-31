package com.machine.service.iam.biam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.service.iam.biam.user.dao.IBIamUserTypeDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserTypeMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class BIamUserTypeDaoImpl implements IBIamUserTypeDao {

    @Autowired
    private BIamUserTypeMapper userTypeMapper;

    @Override
    public void insertOrUpdate(String userId,
                               BIamUserTypeEnum userTypeEnum) {
        BIamUserTypeEntity entity = new BIamUserTypeEntity();
        entity.setUserId(userId);
        entity.setUserType(userTypeEnum);
        userTypeMapper.insertOrUpdate(entity);
    }

    @Override
    public boolean notExists(String userId,
                             BIamUserTypeEnum userType) {
        Wrapper<BIamUserTypeEntity> wrapper = new LambdaQueryWrapper<BIamUserTypeEntity>()
                .eq(BIamUserTypeEntity::getUserId, userId)
                .eq(BIamUserTypeEntity::getUserType, userType);
        return !userTypeMapper.exists(wrapper);
    }

    @Override
    public boolean existsType(BIamUserTypeExistsTypeInputDto inputDto) {
        return userTypeMapper.existsType(inputDto);
    }

    @Override
    public List<BIamUserTypeEntity> selectByUserId(String userId) {
        Wrapper<BIamUserTypeEntity> wrapper = new LambdaQueryWrapper<BIamUserTypeEntity>()
                .eq(BIamUserTypeEntity::getUserId, userId);
        return userTypeMapper.selectList(wrapper);
    }

    @Override
    public List<BIamUserTypeEntity> selectByUserIds(Set<String> userIdSet) {
        Wrapper<BIamUserTypeEntity> wrapper = new LambdaQueryWrapper<BIamUserTypeEntity>()
                .in(BIamUserTypeEntity::getUserId, userIdSet);
        return userTypeMapper.selectList(wrapper);
    }
}
