package com.machine.service.iam.role.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.role.dto.input.IamRoleListSubInputDto;
import com.machine.client.iam.role.dto.input.IamRoleQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.role.dao.IRoleDao;
import com.machine.service.iam.role.dao.mapper.IRoleMapper;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserFunctionPermission.IAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class RoleDaoImpl implements IRoleDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IRoleMapper roleMapper;

    @Override
    public String insert(RoleEntity entity) {
        roleMapper.insert(entity);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ROLE_CREATE, new IdDto(entity.getId()));

        return entity.getId();
    }

    @Override
    public int delete(String id) {
        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ROLE_DELETE, new IdDto(id));

        return roleMapper.deleteById(id);
    }


    @Override
    public int updateStatus(String roleId,
                            StatusEnum status) {
        RoleEntity updateEntity = new RoleEntity();
        updateEntity.setId(roleId);
        updateEntity.setStatus(status);

        //缓存
        customerRedisCommands.del(IAM_USER_FUNCTION_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ROLE_UPDATE_STATUS, new IdStatusDto(roleId, status));

        return roleMapper.updateById(updateEntity);
    }

    @Override
    public int update(RoleEntity entity) {

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ROLE_UPDATE, new IdDto(entity.getId()));

        return roleMapper.updateById(entity);
    }

    @Override
    public RoleEntity getById(String roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    public RoleEntity getByCode(String code) {
        Wrapper<RoleEntity> queryWrapper = new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getCode, code);
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public RoleEntity getByName(String name) {
        Wrapper<RoleEntity> queryWrapper = new LambdaQueryWrapper<RoleEntity>()
                .eq(RoleEntity::getName, name);
        return roleMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> listSubId(IamRoleListSubInputDto inputDto) {
        return roleMapper.listSubId(inputDto);
    }

    @Override
    public List<String> listIdByType(RoleTypeEnum type) {
        return roleMapper.listIdByType(type);
    }

    @Override
    public List<String> listParentByTarget(String id) {
        String currentId = id;
        List<String> parentIdList = new ArrayList<>();

        while (true) {
            RoleEntity entity = roleMapper.selectById(currentId);
            if (null == entity) {
                break;
            }
            currentId = entity.getParentId();
            parentIdList.add(entity.getId());
        }
        return parentIdList;
    }

    @Override
    public List<String> listAllCode() {
        return roleMapper.listAllCode();
    }

    @Override
    public List<RoleEntity> listSub(IamRoleListSubInputDto inputDto) {
        return roleMapper.listSub(inputDto);
    }

    @Override
    public List<RoleEntity> selectByUserId(String userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public List<RoleEntity> selectByIdSet(Set<String> idSet) {
        return roleMapper.selectByIds(idSet);
    }

    @Override
    public Page<RoleEntity> selectPage(IamRoleQueryPageInputDto inputDto) {
        IPage<RoleEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return roleMapper.selectPage(inputDto, page);
    }
}
