package com.machine.service.iam.permission.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.permission.dao.IPermissionDao;
import com.machine.service.iam.permission.dao.mapper.IPermissionMapper;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Permission.IAM_PERMISSION_TREE_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;

@Repository
public class PermissionDaoImpl implements IPermissionDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IPermissionMapper permissionMapper;

    @Override
    public String insert(PermissionEntity entity) {
        permissionMapper.insert(entity);

        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int delete(String id) {
        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_DELETE, new IdDto(id));

        return permissionMapper.deleteById(id);
    }

    @Override
    public int updateStatus(String id,
                            StatusEnum status) {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setStatus(status);

        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE_STATUS, new IdStatusDto(id,status));
        return permissionMapper.updateById(entity);
    }

    @Override
    public int updateParent(String id,
                            String parentId) {
        PermissionEntity entity = new PermissionEntity();
        entity.setId(id);
        entity.setParentId(parentId);

        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE, new IdDto(id));

        return permissionMapper.updateById(entity);
    }

    @Override
    public int update(PermissionEntity entity) {
        //缓存
        customerRedisCommands.del(IAM_PERMISSION_TREE_KEY);
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_PERMISSION_UPDATE, new IdDto(entity.getId()));
        return permissionMapper.updateById(entity);
    }

    @Override
    public PermissionEntity getById(String id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public PermissionEntity getByCode(String code) {
        Wrapper<PermissionEntity> queryWrapper = new LambdaQueryWrapper<PermissionEntity>()
                .eq(PermissionEntity::getCode, code);
        return permissionMapper.selectOne(queryWrapper);
    }

    @Override
    public PermissionEntity getByParentIdAndName(String parentId, String name) {
        Wrapper<PermissionEntity> queryWrapper = new LambdaQueryWrapper<PermissionEntity>()
                .eq(PermissionEntity::getParentId, parentId)
                .eq(PermissionEntity::getName, name);
        return permissionMapper.selectOne(queryWrapper);
    }

    @Override
    public List<PermissionEntity> listByRoleId(String roleId) {
        return permissionMapper.listByRoleId(roleId);
    }

    @Override
    public List<PermissionEntity> selectByUserId(String userId) {
        return permissionMapper.selectByUserId(userId);
    }

    @Override
    public List<PermissionEntity> selectByRoleIds(List<String> roleIdList) {
        if (CollectionUtil.isEmpty(roleIdList)) {
            return List.of();
        }
        return permissionMapper.selectByRoleIds(roleIdList);
    }

    @Override
    public List<PermissionEntity> listAll() {
        return permissionMapper.selectList(new LambdaQueryWrapper<>());
    }

}
