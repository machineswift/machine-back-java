package com.machine.service.iam.biam.permission.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.permission.dao.IBIamPermissionDao;
import com.machine.service.iam.biam.permission.dao.mapper.BIamPermissionMapper;
import com.machine.service.iam.biam.permission.dao.mapper.entity.BIamPermissionEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Permission.BIAM_PERMISSION_TREE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class BIamPermissionDaoImpl implements IBIamPermissionDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamPermissionMapper permissionMapper;

    @Override
    public String insert(BIamPermissionEntity entity) {
        permissionMapper.insert(entity);

        // 缓存
        customerRedisCommands.del(BIAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);

        // 事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int delete(String id) {
        // 缓存
        customerRedisCommands.del(BIAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);

        // 事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_DELETE, new IdDto(id));

        return permissionMapper.deleteById(id);
    }

    @Override
    public int updateParent(String id,
            String parentId) {
        BIamPermissionEntity entity = new BIamPermissionEntity();
        entity.setId(id);
        entity.setParentId(parentId);

        // 缓存
        customerRedisCommands.del(BIAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);

        // 事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE, new IdDto(id));

        return permissionMapper.updateById(entity);
    }

    @Override
    public int update(BIamPermissionEntity entity) {
        // 缓存
        customerRedisCommands.del(BIAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);

        // 事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE, new IdDto(entity.getId()));
        return permissionMapper.updateById(entity);
    }

    @Override
    public BIamPermissionEntity getById(String id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public BIamPermissionEntity getByCode(String code) {
        Wrapper<BIamPermissionEntity> wrapper = new LambdaQueryWrapper<BIamPermissionEntity>()
                .eq(BIamPermissionEntity::getCode, code);
        return permissionMapper.selectOne(wrapper);
    }

    @Override
    public BIamPermissionEntity getByParentIdAndName(String parentId, String name) {
        Wrapper<BIamPermissionEntity> wrapper = new LambdaQueryWrapper<BIamPermissionEntity>()
                .eq(BIamPermissionEntity::getParentId, parentId)
                .eq(BIamPermissionEntity::getName, name);
        return permissionMapper.selectOne(wrapper);
    }

    @Override
    public List<String> selectIdByRoleIds(List<String> roleIdList) {
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        return permissionMapper.selectIdByRoleIds(roleIdList);
    }

    @Override
    public List<BIamPermissionEntity> listByRoleId(String roleId) {
        return permissionMapper.listByRoleId(roleId);
    }

    @Override
    public List<BIamPermissionEntity> listByRoleIdSet(Collection<String> roleIdSet) {
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return List.of();
        }
        return permissionMapper.listByRoleIdSet(roleIdSet);
    }

    @Override
    public List<BIamPermissionEntity> selectByUserId(String userId) {
        return permissionMapper.selectByUserId(userId);
    }

    @Override
    public List<BIamPermissionEntity> selectByRoleIds(List<String> roleIdList) {
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        return permissionMapper.selectByRoleIds(roleIdList);
    }

    @Override
    public List<BIamPermissionEntity> listAll() {
        return permissionMapper.selectList(new LambdaQueryWrapper<>());
    }

}
