package com.machine.service.iam.biam.role.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.role.dto.input.BIamRoleListSubInputDto;
import com.machine.client.iam.biam.role.dto.input.BIamRoleQueryPageInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.dto.IdDto;
import com.machine.sdk.base.model.dto.IdStatusDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.iam.biam.role.dao.IBIamRoleDao;
import com.machine.service.iam.biam.role.dao.mapper.BIamRoleMapper;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserFunctionPermission.BIAM_USER_FUNCTION_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserManageDataPermission.BIAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.UserSuperAppDataPermission.BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class BIamRoleDaoImpl implements IBIamRoleDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private BIamRoleMapper roleMapper;

    @Override
    public String insert(BIamRoleEntity entity) {
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
        BIamRoleEntity updateEntity = new BIamRoleEntity();
        updateEntity.setId(roleId);
        updateEntity.setStatus(status);

        //缓存
        customerRedisCommands.del(BIAM_USER_FUNCTION_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ROLE_UPDATE_STATUS, new IdStatusDto(roleId, status));

        return roleMapper.updateById(updateEntity);
    }

    @Override
    public int update(BIamRoleEntity entity) {

        //缓存
        customerRedisCommands.del(BIAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(BIAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(EventTypeEnum.IAM_ROLE_UPDATE, new IdDto(entity.getId()));

        return roleMapper.updateById(entity);
    }

    @Override
    public long countByIds(Set<String> idSet) {
        if (CollectionUtil.isEmpty(idSet)) {
            return 0L;
        }

        Wrapper<BIamRoleEntity> wrapper = new LambdaQueryWrapper<BIamRoleEntity>()
                .in(BIamRoleEntity::getId, idSet);
        return roleMapper.selectCount(wrapper);
    }

    @Override
    public BIamRoleEntity getById(String roleId) {
        return roleMapper.selectById(roleId);
    }

    @Override
    public BIamRoleEntity getByName(String name) {
        Wrapper<BIamRoleEntity> wrapper = new LambdaQueryWrapper<BIamRoleEntity>()
                .eq(BIamRoleEntity::getName, name);
        return roleMapper.selectOne(wrapper);
    }

    @Override
    public List<String> listSubId(BIamRoleListSubInputDto inputDto) {
        return roleMapper.listSubId(inputDto);
    }

    @Override
    public List<String> listParentByTarget(String id) {
        String currentId = id;
        List<String> parentIdList = new ArrayList<>();

        while (true) {
            BIamRoleEntity entity = roleMapper.selectById(currentId);
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
    public List<BIamRoleEntity> listSub(BIamRoleListSubInputDto inputDto) {
        return roleMapper.listSub(inputDto);
    }

    @Override
    public List<BIamRoleEntity> selectByUserId(String userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public List<BIamRoleEntity> selectByIdSet(Set<String> idSet) {
        return roleMapper.selectByIds(idSet);
    }

    @Override
    public Page<BIamRoleEntity> selectPage(BIamRoleQueryPageInputDto inputDto) {
        IPage<BIamRoleEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return roleMapper.selectPage(inputDto, page);
    }
}
