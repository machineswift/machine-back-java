package com.machine.service.iam.organization.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.organization.dto.input.IamOrganizationCreateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateInputDto;
import com.machine.client.iam.organization.dto.input.IamOrganizationUpdateParentInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationListOutputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.iam.organization.dao.IIamOrganizationDao;
import com.machine.service.iam.organization.dao.mapper.entity.IamOrganizationEntity;
import com.machine.service.iam.organization.service.IIamOrganizationService;
import com.machine.service.iam.user.dao.IIamUserOrganizationRelationDao;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_VIRTUAL_NODE;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_ORGANIZATION_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Organization.DATA_ORGANIZATION_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.Organization.DATA_ORGANIZATION_TREE_KEY;

@Slf4j
@Service
public class IamOrganizationServiceImpl implements IIamOrganizationService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IIamOrganizationDao organizationDao;

    @Autowired
    private IIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IDataLeaf4IamCodeClient leafClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamOrganizationCreateInputDto inputDto) {
        //验证 parentId 是否存在
        IamOrganizationEntity entityById = organizationDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new IamBusinessException("iam.organization.service.create.parentIdNotExists", "父ID不存在");
        }

        //验证名称在同一层级是否存在
        IamOrganizationEntity entityByName = organizationDao.getByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.organization.service.create.nameAlreadyExists", "组织名称已经存在");
        }

        IamOrganizationEntity insertEntity = new IamOrganizationEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        insertEntity.setType(entityById.getType());
        //生成编码
        insertEntity.setCode(leafClient.organizationCode());
        insertEntity.setSort(inputDto.getSort());
        insertEntity.setDescription(inputDto.getDescription());
        return organizationDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        String id = request.getId();
        IamOrganizationEntity entity = organizationDao.getById(id);
        if (null == entity) {
            return 0;
        }

        if (ORGANIZATION_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                ORGANIZATION_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new IamBusinessException("iam.organization.service.delete.rootOrganization", "根组织不能删除");
        }

        //判断是否有子节点
        if (organizationCache.recursionListSubId(entity.getType(), entity.getId()).size() > 1) {
            throw new IamBusinessException("iam.organization.service.delete.hasChildrenNode", "组织有子节点不能删除");
        }

        //获取组织是否关联门店信息
        Boolean isAssociationShop = shopOrganizationRelationClient.isAssociationShopByOrganizationId(new IdRequest(id));
        if (isAssociationShop) {
            throw new IamBusinessException("iam.organization.service.delete.associationShop", "组织关联门店不能删除");
        }

        //获取组织是否关联用户
        boolean isAssociationRole = userOrganizationRelationDao.isAssociationUserByOrganizationId(id);
        if (isAssociationRole) {
            throw new IamBusinessException("iam.organization.service.delete.associationRole", "组织关联用户不能删除");
        }

        return organizationDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamOrganizationUpdateInputDto inputDto) {
        IamOrganizationEntity entity = organizationDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (ORGANIZATION_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                ORGANIZATION_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new IamBusinessException("iam.organization.service.update.rootOrganization", "根组织不能修改");
        }

        //验证名称在同一层级是否存在
        IamOrganizationEntity entityByName = organizationDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.organization.service.update.nameAlreadyExists", "部门名称已经存在");
        }

        IamOrganizationEntity updateEntity = new IamOrganizationEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        updateEntity.setDescription(inputDto.getDescription());
        return organizationDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(IamOrganizationUpdateParentInputDto inputDto) {
        if (inputDto.getParentId().endsWith(ORGANIZATION_VIRTUAL_NODE)) {
            throw new IamBusinessException("iam.organization.service.updateParent.virtualNode", "不能选择未分配节点");
        }

        IamOrganizationEntity dbEntity = organizationDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            //相同直接返回
            return 0;
        }

        if (ORGANIZATION_ROOT_PARENT_ID.equals(dbEntity.getParentId()) ||
                ORGANIZATION_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new IamBusinessException("iam.organization.service.updateParent.rootOrganization", "根组织不能修改");
        }

        //验证名称在同一层级是否存在
        IamOrganizationEntity entityByName = organizationDao.getByParentIdAndName(inputDto.getParentId(), dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new IamBusinessException("iam.organization.service.updateParent.nameAlreadyExists", "部门名称已经存在");
        }

        //验证父部门是否存在
        IamOrganizationEntity parentEntity = organizationDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new IamBusinessException("iam.organization.service.updateParent.parentNotExists", "父部门不存在");
        }

        //验证父Id是否在当前节点下面
        Set<String> recursionIdSet = organizationCache.recursionListSubId(dbEntity.getType(), inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new IamBusinessException("iam.organization.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }
        return organizationDao.updateParentId(inputDto.getId(),inputDto.getParentId());
    }

    @Override
    public IamOrganizationDetailOutputDto detail(IdRequest request) {
        IamOrganizationEntity entity = organizationDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamOrganizationDetailOutputDto.class);
    }

    @Override
    public List<IamOrganizationListOutputDto> listAllByType(IamOrganizationTypeEnum organizationType) {
        List<IamOrganizationEntity> entityList = organizationDao.listAllByType(organizationType);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamOrganizationListOutputDto.class);
    }

    @Override
    public IamOrganizationTreeSimpleOutputDto treeAllSimple(IamOrganizationTypeEnum organizationType) {
        String typeName = organizationType.getName();
        //获取树的动态key
        String keyCode = customerRedisCommands.get(DATA_ORGANIZATION_TREE_KEY + typeName);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_ORGANIZATION_TREE_DATA + typeName + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, IamOrganizationTreeSimpleOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_ORGANIZATION_TREE + typeName);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(DATA_ORGANIZATION_TREE_KEY + typeName);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(DATA_ORGANIZATION_TREE_DATA + typeName + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, IamOrganizationTreeSimpleOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.dataOrganizationTree(organizationType);
            customerRedisCommands.set(DATA_ORGANIZATION_TREE_KEY + typeName, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<IamOrganizationEntity> entityList = organizationDao.listAllByType(organizationType);
            if (CollectionUtil.isEmpty(entityList)) {
                return null;
            }
            List<IamOrganizationTreeSimpleOutputDto> outputDtoList =
                    JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamOrganizationTreeSimpleOutputDto.class);
            IamOrganizationTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(
                    DATA_ORGANIZATION_TREE_DATA + typeName + keyCode,
                    JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }
}
