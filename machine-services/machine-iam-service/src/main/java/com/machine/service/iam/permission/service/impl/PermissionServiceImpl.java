package com.machine.service.iam.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.PermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionListOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.tree.TreeNode;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.iam.permission.dao.IPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;
import com.machine.service.iam.permission.service.IPermissionService;
import com.machine.service.iam.role.dao.IRoleDao;
import com.machine.service.iam.role.dao.IRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;
import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;
import com.machine.service.iam.user.dao.IUserDao;
import com.machine.service.iam.user.dao.IUserPermissionRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserPermissionRelationEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.IAM_PERMISSION_TREE_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Permission.*;

@Slf4j
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IUserPermissionRelationDao userPermissionRelationDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamPermissionCreateInputDto inputDto) {
        //验证 parentId 是否存在
        PermissionEntity entityById = permissionDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new IamBusinessException("iam.permission.create.parentIdNotExists", "父ID不存在");
        }
        if (PermissionResourceTypeEnum.BUTTON == inputDto.getResourceType() &&
                PermissionResourceTypeEnum.MODULE == entityById.getResourceType()) {
            throw new IamBusinessException("iam.permission.create.notSupportedUnderModule", "模块下面不支持添加按钮");
        }

        if (PermissionResourceTypeEnum.BUTTON == entityById.getResourceType()) {
            throw new IamBusinessException("iam.permission.create.notSupportedUnderButton", "按钮下面不支持添加权限");
        }

        //验证 code 是否存在
        PermissionEntity entityByCode = permissionDao.getByCode(inputDto.getCode());
        if (null != entityByCode) {
            throw new IamBusinessException("iam.permission.create.codeAlreadyExists", "编码已经存在");
        }

        //验证名称在同一层级是否存在
        PermissionEntity entityByName = permissionDao.getByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.permission.create.nameAlreadyExists", "权限名称已经存在");
        }

        PermissionEntity insertEntity = new PermissionEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setResourceType(inputDto.getResourceType());
        insertEntity.setCode(inputDto.getCode());
        insertEntity.setName(inputDto.getName());
        insertEntity.setPath(inputDto.getPath());
        insertEntity.setIconUrl(inputDto.getIconUrl());
        insertEntity.setSort(inputDto.getSort());
        insertEntity.setDataMetaInto(inputDto.getDataMetaInto());
        insertEntity.setDescription(inputDto.getDescription());
        return permissionDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        PermissionEntity entity = permissionDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        //子权限信息
        PermissionTreeOutputDto allTreeOutputDto = treeAll();
        PermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, entity.getId());
        assert targetNode != null : "权限不存在";
        if (CollectionUtil.isNotEmpty(targetNode.getChildren())) {
            throw new IamBusinessException("iam.permission.delete.hasSubPermission", "下面有子数据，不能删除");
        }

        //是否关联角色
        List<RolePermissionRelationEntity> rolePermissionRelationEntityList = rolePermissionRelationDao
                .selectByPermissionId(request.getId());
        if (!CollectionUtil.isEmpty(rolePermissionRelationEntityList)) {
            Set<String> roleIdSet = rolePermissionRelationEntityList.stream().map(RolePermissionRelationEntity::getRoleId).collect(Collectors.toSet());
            List<RoleEntity> roleEntityList = roleDao.selectByIdSet(roleIdSet);
            Set<String> roleNameSet = roleEntityList.stream().map(RoleEntity::getName).collect(Collectors.toSet());
            StringBuilder sbRoleName = new StringBuilder();
            Iterator<String> iterator = roleNameSet.iterator();
            while (iterator.hasNext()) {
                String roleName = iterator.next();
                sbRoleName.append(roleName);
                if (iterator.hasNext()) {
                    sbRoleName.append(",");
                }
            }
            throw new IamBusinessException("iam.permission.delete.associationRole", "权限关联角色，不能删除! 角色名称:" + sbRoleName);
        }

        //是否关联用户
        List<UserPermissionRelationEntity> userPermissionRelationEntityList = userPermissionRelationDao
                .selectByPermissionId(request.getId());
        if (!CollectionUtil.isEmpty(userPermissionRelationEntityList)) {
            Set<String> userIdSet = userPermissionRelationEntityList.stream().map(UserPermissionRelationEntity::getUserId).collect(Collectors.toSet());
            List<UserEntity> userEntityList = userDao.selectByIdSet(userIdSet);
            Set<String> userNameSet = userEntityList.stream().map(UserEntity::getName).collect(Collectors.toSet());
            StringBuilder sbUserName = new StringBuilder();
            Iterator<String> iterator = userNameSet.iterator();
            while (iterator.hasNext()) {
                String roleName = iterator.next();
                sbUserName.append(roleName);
                if (iterator.hasNext()) {
                    sbUserName.append(",");
                }
            }
            throw new IamBusinessException("iam.permission.delete.associationUser", "权限关联用户，不能删除! 用户名称:" + sbUserName);
        }

        return permissionDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamPermissionUpdateInputDto inputDto) {
        PermissionEntity entity = permissionDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("iam.permission.update.notExists", "权限不存在");
        }

        //验证 code 是否存在
        PermissionEntity entityByCode = permissionDao.getByCode(inputDto.getCode());
        if (null != entityByCode && !entityByCode.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.permission.create.codeAlreadyExists", "编码已经存在");
        }

        //验证名称在同一层级是否存在
        PermissionEntity entityByName = permissionDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.permission.create.nameAlreadyExists", "权限名称已经存在");
        }

        PermissionEntity updateEntity = new PermissionEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setCode(inputDto.getCode());
        updateEntity.setName(inputDto.getName());
        updateEntity.setPath(inputDto.getPath());
        updateEntity.setIconUrl(inputDto.getIconUrl());
        updateEntity.setSort(inputDto.getSort());
        updateEntity.setDataMetaInto(inputDto.getDataMetaInto());
        updateEntity.setDescription(inputDto.getDescription());
        return permissionDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(IamPermissionUpdateStatusInputDto inputDto) {
        PermissionEntity entity = permissionDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        return permissionDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(IamPermissionUpdateParentInputDto inputDto) {
        PermissionEntity entity = permissionDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证名称在同一层级是否存在
        PermissionEntity entityByName = permissionDao.getByParentIdAndName(inputDto.getParentId(), entity.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.permission.updateParent.nameAlreadyExists", "权限名称已经存在");
        }

        //验证父Id是否在当前节点下面
        PermissionTreeOutputDto allTreeOutputDto = treeAll();
        PermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, inputDto.getId());
        List<PermissionTreeOutputDto> treeOutputDtoList = TreeUtil.collectAllNodes(targetNode);
        Set<String> recursionIdSet = treeOutputDtoList.stream().map(TreeNode::getId).collect(Collectors.toSet());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new IamBusinessException("iam.permission.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        return permissionDao.updateParent(inputDto.getId(), inputDto.getParentId());
    }

    @Override
    public PermissionDetailOutputDto detail(IdRequest request) {
        PermissionEntity entity = permissionDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), PermissionDetailOutputDto.class);
    }

    @Override
    public List<PermissionListOutputDto> listByRoleId(IdRequest request) {
        List<PermissionEntity> entityList = permissionDao.listByRoleId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), PermissionListOutputDto.class);
    }

    @Override
    public PermissionTreeOutputDto treeAll() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(IAM_PERMISSION_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(IAM_PERMISSION_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, PermissionTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(IAM_PERMISSION_TREE_LOCK);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(IAM_PERMISSION_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(IAM_PERMISSION_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, PermissionTreeOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.iamPermissionTree();
            customerRedisCommands.set(IAM_PERMISSION_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<PermissionEntity> entityList = permissionDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                return null;
            }
            List<PermissionTreeOutputDto> outputDtoList =
                    JSONUtil.toList(JSONUtil.toJsonStr(entityList), PermissionTreeOutputDto.class);
            PermissionTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(
                    IAM_PERMISSION_TREE_DATA + keyCode,
                    JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }
}
