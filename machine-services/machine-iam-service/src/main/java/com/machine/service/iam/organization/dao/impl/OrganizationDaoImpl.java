package com.machine.service.iam.organization.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.organization.dao.IOrganizationDao;
import com.machine.service.iam.organization.dao.mapper.IOrganizationMapper;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Organization.DATA_ORGANIZATION_TREE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class OrganizationDaoImpl implements IOrganizationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IOrganizationMapper organizationMapper;

    @Override
    public String insert(OrganizationEntity entity) {
        organizationMapper.insert(entity);

        //缓存
        customerRedisCommands.del(DATA_ORGANIZATION_TREE_KEY + entity.getType().getName());
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_CREATE, new IdDto(entity.getId()));
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        OrganizationEntity dbEntity = organizationMapper.selectById(id);
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(DATA_ORGANIZATION_TREE_KEY + dbEntity.getType().getName());
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_DELETE, new IdDto(id));

        return organizationMapper.deleteById(id);
    }

    @Override
    public int update(OrganizationEntity entity) {
        OrganizationEntity dbEntity = organizationMapper.selectById(entity.getId());
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(DATA_ORGANIZATION_TREE_KEY + dbEntity.getType().getName());
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_UPDATE, new IdDto(entity.getId()));

        return organizationMapper.updateById(entity);
    }

    @Override
    public OrganizationEntity getById(String id) {
        return organizationMapper.selectById(id);
    }

    @Override
    public OrganizationEntity getByParentIdAndName(String parentId,
                                                   String name) {
        Wrapper<OrganizationEntity> queryWrapper = new LambdaQueryWrapper<OrganizationEntity>()
                .eq(OrganizationEntity::getParentId, parentId)
                .eq(OrganizationEntity::getName, name);
        return organizationMapper.selectOne(queryWrapper);
    }


    @Override
    public List<OrganizationEntity> selectByIdSet(Set<String> idSet) {
        return organizationMapper.selectByIds(idSet);
    }

    @Override
    public List<OrganizationEntity> listAllByType(OrganizationTypeEnum organizationType) {
        Wrapper<OrganizationEntity> queryWrapper = new LambdaQueryWrapper<OrganizationEntity>()
                .eq(OrganizationEntity::getType, organizationType);
        return organizationMapper.selectList(queryWrapper);
    }
}
