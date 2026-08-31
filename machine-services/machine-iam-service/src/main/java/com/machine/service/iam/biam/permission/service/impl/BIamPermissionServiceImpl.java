package com.machine.service.iam.biam.permission.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionCreateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateParentInputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionDetailOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionListOutputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.envm.biam.permission.BIamPermissionResourceTypeEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionMetaDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.tree.TreeNode;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.iam.biam.permission.dao.IBIamPermissionDao;
import com.machine.service.iam.biam.permission.dao.mapper.entity.BIamPermissionEntity;
import com.machine.service.iam.biam.permission.service.IBIamPermissionService;
import com.machine.service.iam.biam.role.dao.IBIamRoleDao;
import com.machine.service.iam.biam.role.dao.IBIamRolePermissionRelationDao;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;
import com.machine.service.iam.biam.user.dao.IBIamUserDao;
import com.machine.service.iam.biam.user.dao.IBIamUserPermissionRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserPermissionRelationEntity;
import com.machine.starter.redis.cache.biam.RedisBIamPermissionCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
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

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.sdk.base.constant.CommonConstant.EMPTY_OBJECT;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_PERMISSION_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Permission.*;

@Slf4j
@Service
public class BIamPermissionServiceImpl implements IBIamPermissionService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisBIamPermissionCache redisIamPermissionCache;

    @Autowired
    private IBIamRoleDao roleDao;

    @Autowired
    private IBIamUserDao userDao;

    @Autowired
    private IBIamPermissionDao permissionDao;

    @Autowired
    private IBIamRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IBIamUserPermissionRelationDao userPermissionRelationDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamPermissionCreateInputDto inputDto) {
        BIamPermissionResourceTypeEnum resourceType = inputDto.getResourceType();
        if (BIamPermissionResourceTypeEnum.APP == resourceType ||
                BIamPermissionResourceTypeEnum.MODULE == resourceType) {
            throw new BIamBusinessException("iam.permission.service.create", "暂不支持新增APP和MODULE");
        }

        // 验证 parentId 是否存在
        BIamPermissionEntity parenEntityById = permissionDao.getById(inputDto.getParentId());
        if (null == parenEntityById) {
            throw new BIamBusinessException("iam.permission.service.create.parentIdNotExists", "父ID不存在");
        }
        BIamPermissionResourceTypeEnum dbParentResourceType = parenEntityById.getResourceType();

        if (BIamPermissionResourceTypeEnum.DIRECTORY == resourceType) {
            if (BIamPermissionResourceTypeEnum.APP == dbParentResourceType ||
                    BIamPermissionResourceTypeEnum.MODULE == dbParentResourceType ||
                    BIamPermissionResourceTypeEnum.DIRECTORY == dbParentResourceType) {
            } else {
                throw new BIamBusinessException("iam.permission.service.create.notSupportedParent", "目录只能在应用、模块、目录下面");
            }
        }

        if (BIamPermissionResourceTypeEnum.MENU == resourceType) {
            if (BIamPermissionResourceTypeEnum.MODULE == dbParentResourceType ||
                    BIamPermissionResourceTypeEnum.DIRECTORY == dbParentResourceType) {
            } else {
                throw new BIamBusinessException("iam.permission.service.create.notSupportedParent", "菜单只能在模块或目录下面");
            }
        }

        if (BIamPermissionResourceTypeEnum.BUTTON == resourceType) {
            if (BIamPermissionResourceTypeEnum.MENU == dbParentResourceType ||
                    BIamPermissionResourceTypeEnum.BUTTON == dbParentResourceType) {
            } else {
                throw new BIamBusinessException("iam.permission.service.create.notSupportedParent", "按钮只能在菜单或按钮下面");
            }
        }

        // 验证 code 是否存在
        BIamPermissionEntity entityByCode = permissionDao.getByCode(inputDto.getCode());
        if (null != entityByCode) {
            throwCodeAlreadyExists(entityByCode.getId());
        }

        // 验证名称在同一层级是否存在
        BIamPermissionEntity entityByName = permissionDao.getByParentIdAndName(inputDto.getParentId(),
                inputDto.getName());
        if (null != entityByName) {
            throw new BIamBusinessException("iam.permission.service.create.nameAlreadyExists", "权限名称已经存在");
        }

        BIamPermissionEntity insertEntity = new BIamPermissionEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setResourceType(inputDto.getResourceType());
        insertEntity.setCode(inputDto.getCode());
        insertEntity.setName(inputDto.getName());
        insertEntity.setIcon(inputDto.getIcon());
        insertEntity.setSort(inputDto.getSort());
        if (BIamPermissionResourceTypeEnum.MENU == resourceType) {
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
        BIamPermissionEntity entity = permissionDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        // 子权限信息
        BIamPermissionTreeOutputDto allTreeOutputDto = treeAll()._2();
        BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, entity.getId());
        if (CollectionUtil.isNotEmpty(targetNode.getChildren())) {
            throw new BIamBusinessException("iam.permission.service.delete.hasSubPermission", "下面有子数据，不能删除");
        }

        // 是否关联角色
        List<BIamRolePermissionRelationEntity> iamRolePermissionRelationEntityList = rolePermissionRelationDao
                .selectByPermissionId(request.getId());
        if (!CollectionUtil.isEmpty(iamRolePermissionRelationEntityList)) {
            Set<String> roleIdSet = iamRolePermissionRelationEntityList.stream()
                    .map(BIamRolePermissionRelationEntity::getRoleId).collect(Collectors.toSet());
            List<BIamRoleEntity> iamRoleEntityList = roleDao.selectByIdSet(roleIdSet);
            Set<String> roleNameSet = iamRoleEntityList.stream().map(BIamRoleEntity::getName)
                    .collect(Collectors.toSet());
            StringBuilder sbRoleName = new StringBuilder();
            Iterator<String> iterator = roleNameSet.iterator();
            while (iterator.hasNext()) {
                String roleName = iterator.next();
                sbRoleName.append(roleName);
                if (iterator.hasNext()) {
                    sbRoleName.append(",");
                }
            }
            throw new BIamBusinessException("iam.permission.service.delete.associationRole",
                    "权限关联角色，不能删除! 角色名称:" + sbRoleName);
        }

        // 是否关联用户
        List<BIamUserPermissionRelationEntity> iamUserPermissionRelationEntityList = userPermissionRelationDao
                .selectByPermissionId(request.getId());
        if (!CollectionUtil.isEmpty(iamUserPermissionRelationEntityList)) {
            Set<String> userIdSet = iamUserPermissionRelationEntityList.stream()
                    .map(BIamUserPermissionRelationEntity::getUserId).collect(Collectors.toSet());
            List<BIamUserEntity> iamUserEntityList = userDao.selectByIdSet(userIdSet);
            Set<String> userNameSet = iamUserEntityList.stream().map(BIamUserEntity::getName)
                    .collect(Collectors.toSet());
            StringBuilder sbUserName = new StringBuilder();
            Iterator<String> iterator = userNameSet.iterator();
            while (iterator.hasNext()) {
                String roleName = iterator.next();
                sbUserName.append(roleName);
                if (iterator.hasNext()) {
                    sbUserName.append(",");
                }
            }
            throw new BIamBusinessException("iam.permission.service.delete.associationUser",
                    "权限关联用户，不能删除! 用户名称:" + sbUserName);
        }

        return permissionDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BIamPermissionUpdateInputDto inputDto) {
        BIamPermissionEntity entity = permissionDao.getById(inputDto.getId());
        if (null == entity) {
            throw new BIamBusinessException("iam.permission.service.update.notExists", "权限不存在");
        }

        // 验证 code 是否存在
        BIamPermissionEntity entityByCode = permissionDao.getByCode(inputDto.getCode());
        if (null != entityByCode && !entityByCode.getId().equals(entity.getId())) {
            throwCodeAlreadyExists(entityByCode.getId());
        }

        // 验证名称在同一层级是否存在
        BIamPermissionEntity entityByName = permissionDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new BIamBusinessException("iam.permission.service.create.nameAlreadyExists", "权限名称已经存在");
        }

        BIamPermissionEntity updateEntity = new BIamPermissionEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setCode(inputDto.getCode());
        updateEntity.setName(inputDto.getName());
        updateEntity.setIcon(inputDto.getIcon());
        updateEntity.setSort(inputDto.getSort());
        if (BIamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
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
    public int updateParent(BIamPermissionUpdateParentInputDto inputDto) {
        BIamPermissionEntity dbEntity = permissionDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            // 相同直接返回
            return 0;
        }

        BIamPermissionResourceTypeEnum resourceType = dbEntity.getResourceType();
        if (BIamPermissionResourceTypeEnum.APP == resourceType ||
                BIamPermissionResourceTypeEnum.MODULE == resourceType) {
            throw new BIamBusinessException("iam.permission.service.updateParent", "暂不支持修改APP和MODULE的父节点");
        }

        // 验证名称在同一层级是否存在
        BIamPermissionEntity entityByName = permissionDao.getByParentIdAndName(inputDto.getParentId(),
                dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new BIamBusinessException("iam.permission.service.updateParent.nameAlreadyExists", "权限名称已经存在");
        }

        // 验证父Id是否在当前节点下面
        BIamPermissionTreeOutputDto allTreeOutputDto = treeAll()._2();
        BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, inputDto.getId());
        List<BIamPermissionTreeOutputDto> treeOutputDtoList = TreeUtil.collectAllNodes(targetNode);
        Set<String> recursionIdSet = treeOutputDtoList.stream().map(TreeNode::getId).collect(Collectors.toSet());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new BIamBusinessException("iam.permission.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        // 验证父权限是否存在
        BIamPermissionEntity parentEntity = permissionDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new BIamBusinessException("iam.permission.service.updateParent.parentNotExists", "父权限不存在");
        }

        BIamPermissionResourceTypeEnum parentResourceType = parentEntity.getResourceType();
        if (BIamPermissionResourceTypeEnum.DIRECTORY == resourceType) {
            if (BIamPermissionResourceTypeEnum.APP == parentResourceType ||
                    BIamPermissionResourceTypeEnum.MODULE == parentResourceType ||
                    BIamPermissionResourceTypeEnum.DIRECTORY == parentResourceType) {
            } else {
                throw new BIamBusinessException("iam.permission.service.updateParent.notSupportedParent",
                        "目录只能在应用、目录、模块下面");
            }
        }

        if (BIamPermissionResourceTypeEnum.MENU == resourceType) {
            if (BIamPermissionResourceTypeEnum.MODULE == parentResourceType ||
                    BIamPermissionResourceTypeEnum.DIRECTORY == parentResourceType) {
            } else {
                throw new BIamBusinessException("iam.permission.service.updateParent.notSupportedParent", "菜单只能在模块或目录下面");
            }
        }

        if (BIamPermissionResourceTypeEnum.BUTTON == resourceType) {
            if (BIamPermissionResourceTypeEnum.MENU == parentResourceType ||
                    BIamPermissionResourceTypeEnum.BUTTON == parentResourceType) {
            } else {
                throw new BIamBusinessException("iam.permission.service.updateParent.notSupportedParent", "按钮只能在菜单或按钮下面");
            }
        }

        return permissionDao.updateParent(inputDto.getId(), inputDto.getParentId());
    }

    @Override
    public BIamPermissionDetailOutputDto detail(IdRequest request) {
        BIamPermissionEntity entity = permissionDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        BIamPermissionDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity),
                BIamPermissionDetailOutputDto.class);
        if (BIamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
            if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                    !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                outputDto.setDataPermissionMetaList(
                        JSONUtil.toList(entity.getDataMetaInto(), BIamDataPermissionMetaDto.class));
            }
        }
        return outputDto;
    }

    @Override
    public BIamPermissionDetailOutputDto detailByCode(IdRequest request) {
        BIamPermissionEntity entity = permissionDao.getByCode(request.getId());
        if (null == entity) {
            return null;
        }
        BIamPermissionDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity),
                BIamPermissionDetailOutputDto.class);
        if (BIamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
            if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                    !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                outputDto.setDataPermissionMetaList(
                        JSONUtil.toList(entity.getDataMetaInto(), BIamDataPermissionMetaDto.class));
            }
        }
        return outputDto;
    }

    @Override
    public List<BIamPermissionListOutputDto> listByRoleId(IdRequest request) {
        List<BIamPermissionEntity> entityList = permissionDao.listByRoleId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        List<BIamPermissionListOutputDto> outputDtoList = new ArrayList<>();
        for (BIamPermissionEntity entity : entityList) {
            BIamPermissionListOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity),
                    BIamPermissionListOutputDto.class);
            if (BIamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
                if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                        !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                    outputDto.setDataPermissionMetaList(
                            JSONUtil.toList(entity.getDataMetaInto(), BIamDataPermissionMetaDto.class));
                }
            }
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

    @Override
    public List<BIamPermissionListOutputDto> listByRoleIdSet(IdSetRequest request) {
        if (CollectionUtil.isEmpty(request.getIdSet())) {
            return List.of();
        }
        List<BIamPermissionEntity> entityList = permissionDao.listByRoleIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        List<BIamPermissionListOutputDto> outputDtoList = new ArrayList<>();
        for (BIamPermissionEntity entity : entityList) {
            BIamPermissionListOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity),
                    BIamPermissionListOutputDto.class);
            if (BIamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
                if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                        !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                    outputDto.setDataPermissionMetaList(
                            JSONUtil.toList(entity.getDataMetaInto(), BIamDataPermissionMetaDto.class));
                }
            }
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

    @Override
    public Tuples.Tuple2<String, BIamPermissionTreeOutputDto> treeAll() {
        // 获取树的动态key
        String keyCode = customerRedisCommands.get(BIAM_PERMISSION_TREE_KEY);

        // 如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(BIAM_PERMISSION_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return Tuples.of(keyCode, JSONUtil.toBean(treeJson, BIamPermissionTreeOutputDto.class));
            }
        }

        // 缓存击穿
        RLock lock = redissonClient.getLock(LOCK_IAM_PERMISSION_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(BIAM_PERMISSION_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(BIAM_PERMISSION_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return Tuples.of(keyCode, JSONUtil.toBean(treeJson, BIamPermissionTreeOutputDto.class));
                }
            }

            // 重新生成树的动态key
            keyCode = leaf4RedisClient.iamPermissionTree();
            customerRedisCommands.setex(BIAM_PERMISSION_TREE_KEY, keyCode, 24 * 60 * 60);

            // 查询DB组装树
            List<BIamPermissionEntity> entityList = permissionDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                // Tree 数据缓存到redis
                customerRedisCommands.setex(BIAM_PERMISSION_TREE_DATA + keyCode, EMPTY_OBJECT, 24 * 60 * 60 + 60);
                return Tuples.of(keyCode, new BIamPermissionTreeOutputDto());
            }
            List<BIamPermissionTreeOutputDto> outputDtoList = new ArrayList<>();
            for (BIamPermissionEntity entity : entityList) {
                BIamPermissionTreeOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity),
                        BIamPermissionTreeOutputDto.class);
                if (BIamPermissionResourceTypeEnum.MENU == entity.getResourceType()) {
                    if (StrUtil.isNotBlank(entity.getDataMetaInto()) &&
                            !EMPTY_LIST_STR.equals(entity.getDataMetaInto())) {
                        outputDto.setDataPermissionMetaList(
                                JSONUtil.toList(entity.getDataMetaInto(), BIamDataPermissionMetaDto.class));
                    }
                }
                outputDtoList.add(outputDto);
            }

            BIamPermissionTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            // Tree 数据缓存到redis
            customerRedisCommands.setex(BIAM_PERMISSION_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);
            return Tuples.of(keyCode, treeOutputDto);
        } finally {
            lock.unlock();
        }
    }

    private void throwCodeAlreadyExists(String permissionId) {
        BIamPermissionTreeOutputDto treeAllOutputDto = redisIamPermissionCache.treeAll();
        // 找到指定的节点
        BIamPermissionTreeOutputDto treeNode = TreeUtil.findNode(treeAllOutputDto, permissionId);

        // 获取指定组织的所有父节点列表（list元素第一个是当前节点，最后一个是根节点，从左至右组织层级递增）
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

        throw new BIamBusinessException("iam.permission.service.create.codeAlreadyExists",
                "编码已经存在已被【" + sbParentName + "】占用，请修改后重新配置！");
    }
}
