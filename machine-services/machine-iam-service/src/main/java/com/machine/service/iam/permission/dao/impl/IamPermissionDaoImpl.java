package com.machine.service.iam.permission.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.permission.dao.IIamPermissionDao;
import com.machine.service.iam.permission.dao.mapper.IamPermissionMapper;
import com.machine.service.iam.permission.dao.mapper.entity.IamPermissionEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Permission.IAM_PERMISSION_TREE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class IamPermissionDaoImpl implements IIamPermissionDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IamPermissionMapper permissionMapper;

    @Override
    public String insert(IamPermissionEntity entity) {
        permissionMapper.insert(entity);

        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int delete(String id) {
        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_DELETE, new IdDto(id));

        return permissionMapper.deleteById(id);
    }

    @Override
    public int updateParent(String id,
                            String parentId) {
        IamPermissionEntity entity = new IamPermissionEntity();
        entity.setId(id);
        entity.setParentId(parentId);

        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE, new IdDto(id));

        return permissionMapper.updateById(entity);
    }

    @Override
    public int update(IamPermissionEntity entity) {
        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE, new IdDto(entity.getId()));
        return permissionMapper.updateById(entity);
    }

    @Override
    public IamPermissionEntity getById(String id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public IamPermissionEntity getByCode(String code) {
        Wrapper<IamPermissionEntity> queryWrapper = new LambdaQueryWrapper<IamPermissionEntity>()
                .eq(IamPermissionEntity::getCode, code);
        return permissionMapper.selectOne(queryWrapper);
    }

    @Override
    public IamPermissionEntity getByParentIdAndName(String parentId, String name) {
        Wrapper<IamPermissionEntity> queryWrapper = new LambdaQueryWrapper<IamPermissionEntity>()
                .eq(IamPermissionEntity::getParentId, parentId)
                .eq(IamPermissionEntity::getName, name);
        return permissionMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> selectIdByRoleIds(List<String> roleIdList) {
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        return permissionMapper.selectIdByRoleIds(roleIdList);
    }

    @Override
    public List<IamPermissionEntity> listByRoleId(String roleId) {
        return permissionMapper.listByRoleId(roleId);
    }

    @Override
    public List<IamPermissionEntity> selectByUserId(String userId) {
        return permissionMapper.selectByUserId(userId);
    }

    @Override
    public List<IamPermissionEntity> selectByRoleIds(List<String> roleIdList) {
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        return permissionMapper.selectByRoleIds(roleIdList);
    }

    @Override
    public List<IamPermissionEntity> listAll() {
        return permissionMapper.selectList(new LambdaQueryWrapper<>());
    }

}
