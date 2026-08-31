package com.machine.service.iam.biam.organization.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.organization.dao.IBIamOrganizationDao;
import com.machine.service.iam.biam.organization.dao.mapper.BIamOrganizationMapper;
import com.machine.service.iam.biam.organization.dao.mapper.entity.BIamOrganizationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Organization.BIAM_ORGANIZATION_TREE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class BIamOrganizationDaoImpl implements IBIamOrganizationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamOrganizationMapper organizationMapper;

    @Override
    public String insert(BIamOrganizationEntity entity) {
        organizationMapper.insert(entity);

        //缓存
        customerRedisCommands.del(BIAM_ORGANIZATION_TREE_KEY + entity.getType().getName());
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_CREATE, new IdDto(entity.getId()));
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        BIamOrganizationEntity dbEntity = organizationMapper.selectById(id);
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(BIAM_ORGANIZATION_TREE_KEY + dbEntity.getType().getName());
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_DELETE, new IdDto(id));

        return organizationMapper.deleteById(id);
    }

    @Override
    public int update(BIamOrganizationEntity entity) {
        BIamOrganizationEntity dbEntity = organizationMapper.selectById(entity.getId());
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(BIAM_ORGANIZATION_TREE_KEY + dbEntity.getType().getName());
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_UPDATE, new IdDto(entity.getId()));

        return organizationMapper.updateById(entity);
    }

    @Override
    public int updateParentId(String id,
                              String parentId) {
        BIamOrganizationEntity dbEntity = organizationMapper.selectById(id);
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(BIAM_ORGANIZATION_TREE_KEY + dbEntity.getType().getName());
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ORGANIZATION_UPDATE, new IdDto(id));

        BIamOrganizationEntity entity = new BIamOrganizationEntity();
        entity.setId(id);
        entity.setParentId(parentId);
        return organizationMapper.updateById(entity);
    }

    @Override
    public BIamOrganizationEntity getById(String id) {
        return organizationMapper.selectById(id);
    }

    @Override
    public BIamOrganizationEntity getByParentIdAndName(String parentId,
                                                       String name) {
        Wrapper<BIamOrganizationEntity> wrapper = new LambdaQueryWrapper<BIamOrganizationEntity>()
                .eq(BIamOrganizationEntity::getParentId, parentId)
                .eq(BIamOrganizationEntity::getName, name);
        return organizationMapper.selectOne(wrapper);
    }

    @Override
    public List<BIamOrganizationEntity> listAllByType(BIamOrganizationTypeEnum organizationType) {
        Wrapper<BIamOrganizationEntity> wrapper = new LambdaQueryWrapper<BIamOrganizationEntity>()
                .eq(BIamOrganizationEntity::getType, organizationType);
        return organizationMapper.selectList(wrapper);
    }
}
