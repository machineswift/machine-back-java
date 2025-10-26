package com.machine.service.iam.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.IamPermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionListOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.dto.iam.DataPermissionMetaDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.tree.TreeNode;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.iam.permission.dao.IIamPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.IamPermissionEntity;
import com.machine.service.iam.permission.service.IIamPermissionService;
import com.machine.service.iam.role.dao.IIamRoleDao;
import com.machine.service.iam.role.dao.IIamRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.IamRoleEntity;
import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;
import com.machine.service.iam.user.dao.IIamUserDao;
import com.machine.service.iam.user.dao.IIamUserPermissionRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.IamUserEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserPermissionRelationEntity;
import com.machine.starter.redis.cache.iam.RedisCacheIamPermission;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_PERMISSION_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Permission.*;

@Slf4j
@Service
public class IamPermissionServiceImpl implements IIamPermissionService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheIamPermission redisCacheIamPermission;

    @Autowired
    private IIamRoleDao roleDao;

    @Autowired
    private IIamUserDao userDao;

    @Autowired
    private IIamPermissionDao permissionDao;

    @Autowired
    private IIamRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IIamUserPermissionRelationDao userPermissionRelationDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamPermissionCreateInputDto inputDto) {
        IamPermissionResourceTypeEnum resourceType = inputDto.getResourceType();
        if (IamPermissionResourceTypeEnum.APP == resourceType ||
                IamPermissionResourceTypeEnum.MODULE == resourceType) {
            throw new IamBusinessException("iam.permission.service.create", "暂不支持新增APP和MODULE");
        }

        //验证 parentId 是否存在
        IamPermissionEntity entityById = permissionDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new IamBusinessException("iam.permission.service.create.parentIdNotExists", "父ID不存在");
        }
        IamPermissionResourceTypeEnum dbResourceType = entityById.getResourceType();

        if (IamPermissionResourceTypeEnum.DIRECTORY == resourceType) {
            if (IamPermissionResourceTypeEnum.APP == dbResourceType ||
                    IamPermissionResourceTypeEnum.MODULE == dbResourceType ||
                    IamPermissionResourceTypeEnum.DIRECTORY == dbResourceType) {
            }else {
                throw new IamBusinessException("iam.permission.service.create.notSupportedParent", "目录只能在应用、模块、目录下面");
            }
        }

        if (IamPermissionResourceTypeEnum.MENU == resourceType) {
            if (IamPermissionResourceTypeEnum.DIRECTORY != dbResourceType) {
                throw new IamBusinessException("iam.permission.service.create.notSupportedParent", "菜单只能在目录下面");
            }
        }

        if (IamPermissionResourceTypeEnum.BUTTON == resourceType) {
            if (IamPermissionResourceTypeEnum.MENU == dbResourceType ||
                    IamPermissionResourceTypeEnum.BUTTON == dbResourceType) {
            }else {
                throw new IamBusinessException("iam.permission.service.create.notSupportedParent", "按钮只能在模块或按钮下面");
            }
        }

        //验证 code 是否存在
        IamPermissionEntity entityByCode = permissionDao.getByCode(inputDto.getCode());
        if (null != entityByCode) {
            throwCodeAlreadyExists(entityByCode.getId());
        }

        //验证名称在同一层级是否存在
        IamPermissionEntity entityByName = permissionDao.getByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.permission.service.create.nameAlreadyExists", "权限名称已经存在");
        }

        IamPermissionEntity insertEntity = new IamPermissionEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setResourceType(inputDto.getResourceType());
        insertEntity.setCode(inputDto.getCode());
        insertEntity.setName(inputDto.getName());
        insertEntity.setIcon(inputDto.getIcon());
        insertEntity.setSort(inputDto.getSort());
        if (IamPermissionResourceTypeEnum.MENU == resourceType) {
            if (CollectionUtil.isNotEmpty(inputDto.getDataPermissionMetaList())) {
                insertEntity.setDataMetaInto(JSONUtil.toJsonStr(inputDto.getDataPermissionMetaList()));
            } else {
                insertEntity.setDataMetaInto(EMPTY_LIST_STR);
            }
        }
        insertEntity.setDescription(inputDto.getDescription());
        return permissionDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        IamPermissionEntity entity = permissionDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        //子权限信息
        IamPermissionTreeOutputDto allTreeOutputDto = treeAll();
        IamPermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, entity.getId());
        if (CollectionUtil.isNotEmpty(targetNode.getChildren())) {
            throw new IamBusinessException("iam.permission.service.delete.hasSubPermission", "下面有子数据，不能删除");
        }

        //是否关联角色
        List<IamRolePermissionRelationEntity> iamRolePermissionRelationEntityList = rolePermissionRelationDao
                .selectByPermissionId(request.getId());
        if (!CollectionUtil.isEmpty(iamRolePermissionRelationEntityList)) {
            Set<String> roleIdSet = iamRolePermissionRelationEntityList.stream().map(IamRolePermissionRelationEntity::getRoleId).collect(Collectors.toSet());
            List<IamRoleEntity> iamRoleEntityList = roleDao.selectByIdSet(roleIdSet);
            Set<String> roleNameSet = iamRoleEntityList.stream().map(IamRoleEntity::getName).collect(Collectors.toSet());
            StringBuilder sbRoleName = new StringBuilder();
            Iterator<String> iterator = roleNameSet.iterator();
            while (iterator.hasNext()) {
                String roleName = iterator.next();
                sbRoleName.append(roleName);
                if (iterator.hasNext()) {
                    sbRoleName.append(",");
                }
            }
            throw new IamBusinessException("iam.permission.service.delete.associationRole", "权限关联角色，不能删除! 角色名称:" + sbRoleName);
        }

        //是否关联用户
        List<IamUserPermissionRelationEntity> iamUserPermissionRelationEntityList = userPermissionRelationDao
                .selectByPermissionId(request.getId());
        if (!CollectionUtil.isEmpty(iamUserPermissionRelationEntityList)) {
            Set<String> userIdSet = iamUserPermissionRelationEntityList.stream().map(IamUserPermissionRelationEntity::getUserId).collect(Collectors.toSet());
            List<IamUserEntity> iamUserEntityList = userDao.selectByIdSet(userIdSet);
            Set<String> userNameSet = iamUserEntityList.stream().map(IamUserEntity::getName).collect(Collectors.toSet());
            StringBuilder sbUserName = new StringBuilder();
            Iterator<String> iterator = userNameSet.iterator();
            while (iterator.hasNext()) {
                String roleName = iterator.next();
                sbUserName.append(roleName);
                if (iterator.hasNext()) {
                    sbUserName.append(",");
                }
            }
            throw new IamBusinessException("iam.permission.service.delete.associationUser", "权限关联用户，不能删除! 用户名称:" + sbUserName);
        }

        return permissionDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamPermissionUpdateInputDto inputDto) {
        IamPermissionEntity entity = permissionDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("iam.permission.service.update.notExists", "权限不存在");
        }

        //验证 code 是否存在
        IamPermissionEntity entityByCode = permissionDao.getByCode(inputDto.getCode());
        if (null != entityByCode && !entityByCode.getId().equals(entity.getId())) {
            throwCodeAlreadyExists(entityByCode.getId());
        }

        //验证名称在同一层级是否存在
        IamPermissionEntity entityByName = permissionDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.permission.service.create.nameAlreadyExists", "权限名称已经存在");
        }

        IamPermissionEntity updateEntity = new IamPermissionEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setCode(inputDto.getCode());
        updateEntity.setName(inputDto.getName());
        updateEntity.setIcon(inputDto.getIcon());
        updateEntity.setSort(inputDto.getSort());
        if (IamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
            if (CollectionUtil.isNotEmpty(inputDto.getDataPermissionMetaList())) {
                updateEntity.setDataMetaInto(JSONUtil.toJsonStr(inputDto.getDataPermissionMetaList()));
            } else {
                updateEntity.setDataMetaInto(EMPTY_LIST_STR);
            }
        }
        updateEntity.setDescription(inputDto.getDescription());
        return permissionDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(IamPermissionUpdateParentInputDto inputDto) {
        IamPermissionEntity dbEntity = permissionDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            //相同直接返回
            return 0;
        }

        IamPermissionResourceTypeEnum resourceType = dbEntity.getResourceType();
        if (IamPermissionResourceTypeEnum.APP == resourceType ||
                IamPermissionResourceTypeEnum.MODULE == resourceType) {
            throw new IamBusinessException("iam.permission.service.updateParent", "暂不支持修改APP和MODULE的父节点");
        }

        //验证名称在同一层级是否存在
        IamPermissionEntity entityByName = permissionDao.getByParentIdAndName(inputDto.getParentId(), dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new IamBusinessException("iam.permission.service.updateParent.nameAlreadyExists", "权限名称已经存在");
        }

        //验证父Id是否在当前节点下面
        IamPermissionTreeOutputDto allTreeOutputDto = treeAll();
        IamPermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, inputDto.getId());
        List<IamPermissionTreeOutputDto> treeOutputDtoList = TreeUtil.collectAllNodes(targetNode);
        Set<String> recursionIdSet = treeOutputDtoList.stream().map(TreeNode::getId).collect(Collectors.toSet());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new IamBusinessException("iam.permission.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        //验证父权限是否存在
        IamPermissionEntity parentEntity = permissionDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new IamBusinessException("iam.permission.service.updateParent.parentNotExists", "父权限不存在");
        }

        IamPermissionResourceTypeEnum parentResourceType = dbEntity.getResourceType();
        if (IamPermissionResourceTypeEnum.DIRECTORY == resourceType) {
            if (IamPermissionResourceTypeEnum.APP == parentResourceType ||
                    IamPermissionResourceTypeEnum.MODULE == parentResourceType ||
                    IamPermissionResourceTypeEnum.DIRECTORY == parentResourceType) {
            }else {
                throw new IamBusinessException("iam.permission.service.updateParent.notSupportedParent", "目录只能在应用、目录、模块下面");
            }
        }

        if (IamPermissionResourceTypeEnum.MENU == resourceType) {
            if (IamPermissionResourceTypeEnum.DIRECTORY != parentResourceType) {
                throw new IamBusinessException("iam.permission.service.updateParent.notSupportedParent", "菜单只能在目录下面");
            }
        }

        if (IamPermissionResourceTypeEnum.BUTTON == resourceType) {
            if (IamPermissionResourceTypeEnum.MENU == parentResourceType ||
                    IamPermissionResourceTypeEnum.BUTTON == parentResourceType) {
            }else {
                throw new IamBusinessException("iam.permission.service.updateParent.notSupportedParent", "按钮只能在模块或按钮下面");
            }
        }

        return permissionDao.updateParent(inputDto.getId(), inputDto.getParentId());
    }

    @Override
    public IamPermissionDetailOutputDto detail(IdRequest request) {
        IamPermissionEntity entity = permissionDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        IamPermissionDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamPermissionDetailOutputDto.class);
        if (IamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
            if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                    !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                outputDto.setDataPermissionMetaList(JSONUtil.toList(entity.getDataMetaInto(), DataPermissionMetaDto.class));
            }
        }
        return outputDto;
    }

    @Override
    public IamPermissionDetailOutputDto detailByCode(IdRequest request) {
        IamPermissionEntity entity = permissionDao.getByCode(request.getId());
        if (null == entity) {
            return null;
        }
        IamPermissionDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamPermissionDetailOutputDto.class);
        if (IamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
            if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                    !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                outputDto.setDataPermissionMetaList(JSONUtil.toList(entity.getDataMetaInto(), DataPermissionMetaDto.class));
            }
        }
        return outputDto;
    }

    @Override
    public List<IamPermissionListOutputDto> listByRoleId(IdRequest request) {
        List<IamPermissionEntity> entityList = permissionDao.listByRoleId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        List<IamPermissionListOutputDto> outputDtoList = new ArrayList<>();
        for (IamPermissionEntity entity : entityList) {
            IamPermissionListOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamPermissionListOutputDto.class);
            if (IamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
                if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                        !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                    outputDto.setDataPermissionMetaList(JSONUtil.toList(entity.getDataMetaInto(), DataPermissionMetaDto.class));
                }
            }
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

    @Override
    public IamPermissionTreeOutputDto treeAll() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(IAM_PERMISSION_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(IAM_PERMISSION_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, IamPermissionTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_IAM_PERMISSION_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(IAM_PERMISSION_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(IAM_PERMISSION_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, IamPermissionTreeOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.iamPermissionTree();
            customerRedisCommands.set(IAM_PERMISSION_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<IamPermissionEntity> entityList = permissionDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                //Tree 数据缓存到redis
                customerRedisCommands.set(IAM_PERMISSION_TREE_DATA + keyCode, EMPTY_LIST_STR, 24 * 60 * 60 + 60);
                return null;
            }
            List<IamPermissionTreeOutputDto> outputDtoList = new ArrayList<>();
            for (IamPermissionEntity entity : entityList) {
                IamPermissionTreeOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamPermissionTreeOutputDto.class);
                if (IamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
                    if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                            !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                        outputDto.setDataPermissionMetaList(JSONUtil.toList(entity.getDataMetaInto(), DataPermissionMetaDto.class));
                    }
                }
                outputDtoList.add(outputDto);
            }

            IamPermissionTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(IAM_PERMISSION_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto), 24 * 60 * 60 + 60);
            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }

    private void throwCodeAlreadyExists(String permissionId) {
        IamPermissionTreeOutputDto treeAllOutputDto = redisCacheIamPermission.treeAll();
        //找到指定的节点
        IamPermissionTreeOutputDto treeNode = TreeUtil.findNode(treeAllOutputDto, permissionId);

        //获取指定组织的所有父节点列表（list元素第一个是当前节点，最后一个是根节点，从左至右组织层级递增）
        List<String> parentNameList = new ArrayList<>();
        do {
            parentNameList.add(treeNode.getName());
            treeNode = TreeUtil.findNode(treeAllOutputDto, treeNode.getParentId());
        } while (null != treeNode);

        CollectionUtil.reverse(parentNameList);

        StringBuilder sbParentName = new StringBuilder();
        for (int i = 1; i < parentNameList.size(); i++) {
            sbParentName.append(parentNameList.get(i)).append("->");
        }
        sbParentName.deleteCharAt(sbParentName.length() - 2);


        throw new IamBusinessException("iam.permission.service.create.codeAlreadyExists",
                "编码已经存在已被【" + sbParentName + "】占用，请修改后重新配置！");
    }
}
