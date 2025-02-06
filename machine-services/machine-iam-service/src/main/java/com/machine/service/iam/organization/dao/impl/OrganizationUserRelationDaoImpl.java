package com.machine.service.iam.organization.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.iam.organization.dao.IOrganizationUserRelationDao;
import com.machine.service.iam.organization.dao.mapper.IOrganizationUserRelationMapper;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationUserRelationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public class OrganizationUserRelationDaoImpl implements IOrganizationUserRelationDao {

    @Autowired
    private IOrganizationUserRelationMapper organizationUserRelationMapper;

    @Override
    public void insert(String organizationId,
                       String userId) {
        OrganizationUserRelationEntity entity = new OrganizationUserRelationEntity();
        entity.setOrganizationId(organizationId);
        entity.setUserId(userId);
        organizationUserRelationMapper.insert(entity);
    }

    @Override
    public int deleteByOrganizationId(String organizationId) {
        Wrapper<OrganizationUserRelationEntity> deleteWrapper = new LambdaQueryWrapper<OrganizationUserRelationEntity>()
                .eq(OrganizationUserRelationEntity::getOrganizationId, organizationId);
        return organizationUserRelationMapper.delete(deleteWrapper);
    }

    @Override
    public OrganizationUserRelationEntity selectByOrganizationId(String organizationId) {
        Wrapper<OrganizationUserRelationEntity> queryWrapper = new LambdaQueryWrapper<OrganizationUserRelationEntity>()
                .eq(OrganizationUserRelationEntity::getOrganizationId, organizationId);
        return organizationUserRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<OrganizationUserRelationEntity> selectByOrganizationIdSet(Set<String> organizationIdSet) {
        if (CollectionUtil.isEmpty(organizationIdSet)) {
            return List.of();
        }
        Wrapper<OrganizationUserRelationEntity> queryWrapper = new LambdaQueryWrapper<OrganizationUserRelationEntity>()
                .in(OrganizationUserRelationEntity::getOrganizationId, organizationIdSet);
        return organizationUserRelationMapper.selectList(queryWrapper);
    }
}
