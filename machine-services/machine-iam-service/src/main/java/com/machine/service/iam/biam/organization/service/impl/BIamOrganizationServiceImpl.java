package com.machine.service.iam.biam.organization.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationCreateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateInputDto;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateParentInputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationListOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.iam.biam.organization.dao.IBIamOrganizationDao;
import com.machine.service.iam.biam.organization.dao.mapper.entity.BIamOrganizationEntity;
import com.machine.service.iam.biam.organization.service.IBIamOrganizationService;
import com.machine.service.iam.biam.user.dao.IBIamUserOrganizationRelationDao;
import com.machine.starter.redis.cache.biam.RedisBIamOrganizationCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.base.constant.CommonBIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_ORGANIZATION_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Organization.BIAM_ORGANIZATION_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Organization.BIAM_ORGANIZATION_TREE_KEY;

@Slf4j
@Service
public class BIamOrganizationServiceImpl implements IBIamOrganizationService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisBIamOrganizationCache organizationCache;

    @Autowired
    private IBIamOrganizationDao organizationDao;

    @Autowired
    private IBIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IDataLeaf4IamCodeClient leafClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamOrganizationCreateInputDto inputDto) {
        // 验证 parentId 是否存在
        BIamOrganizationEntity entityById = organizationDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new BIamBusinessException("biam.organization.service.create.parentIdNotExists", "父ID不存在");
        }

        // 验证名称在同一层级是否存在
        BIamOrganizationEntity entityByName = organizationDao.getByParentIdAndName(inputDto.getParentId(),
                inputDto.getName());
        if (null != entityByName) {
            throw new BIamBusinessException("biam.organization.service.create.nameAlreadyExists", "名称已经存在");
        }

        BIamOrganizationEntity insertEntity = new BIamOrganizationEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        insertEntity.setType(entityById.getType());
        // 生成编码
        insertEntity.setCode(leafClient.organizationCode());
        insertEntity.setSort(inputDto.getSort());
        insertEntity.setDescription(inputDto.getDescription());
        return organizationDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        String id = request.getId();
        BIamOrganizationEntity entity = organizationDao.getById(id);
        if (null == entity) {
            return 0;
        }

        if (DATA_ORGANIZATION_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_ORGANIZATION_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new BIamBusinessException("biam.organization.service.delete.rootNode", "根组织不能删除");
        }

        // 判断是否有子节点
        if (organizationCache.recursionListSubId(entity.getType(), entity.getId()).size() > 1) {
            throw new BIamBusinessException("biam.organization.service.delete.hasChildrenNode", "有子节点不能删除");
        }

        // 获取组织是否关联门店信息
        Boolean isAssociationShop = shopOrganizationRelationClient.isAssociationShopByOrganizationId(new IdRequest(id));
        if (isAssociationShop) {
            throw new BIamBusinessException("biam.organization.service.delete.associationShop", "关联门店不能删除");
        }

        // 获取组织是否关联用户
        boolean isAssociationRole = userOrganizationRelationDao.isAssociationUserByOrganizationId(id);
        if (isAssociationRole) {
            throw new BIamBusinessException("biam.organization.service.delete.associationRole", "关联用户不能删除");
        }

        return organizationDao.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BIamOrganizationUpdateInputDto inputDto) {
        BIamOrganizationEntity entity = organizationDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (DATA_ORGANIZATION_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                DATA_ORGANIZATION_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new BIamBusinessException("biam.organization.service.update.rootNode", "根节点不能修改");
        }

        // 验证名称在同一层级是否存在
        BIamOrganizationEntity entityByName = organizationDao.getByParentIdAndName(entity.getParentId(),
                inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new BIamBusinessException("biam.organization.service.update.nameAlreadyExists", "名称已经存在");
        }

        BIamOrganizationEntity updateEntity = new BIamOrganizationEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        updateEntity.setDescription(inputDto.getDescription());
        return organizationDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(BIamOrganizationUpdateParentInputDto inputDto) {
        if (inputDto.getParentId().endsWith(DATA_ORGANIZATION_VIRTUAL_NODE)) {
            throw new BIamBusinessException("biam.organization.service.updateParent.virtualNode", "不能选择未分配节点");
        }

        BIamOrganizationEntity dbEntity = organizationDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            // 相同直接返回
            return 0;
        }

        if (DATA_ORGANIZATION_ROOT_PARENT_ID.equals(dbEntity.getParentId()) ||
                DATA_ORGANIZATION_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new BIamBusinessException("biam.organization.service.updateParent.rootNode", "根节点不能修改");
        }

        // 验证名称在同一层级是否存在
        BIamOrganizationEntity entityByName = organizationDao.getByParentIdAndName(inputDto.getParentId(),
                dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new BIamBusinessException("biam.organization.service.updateParent.nameAlreadyExists", "名称已经存在");
        }

        // 验证父部门是否存在
        BIamOrganizationEntity parentEntity = organizationDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new BIamBusinessException("biam.organization.service.updateParent.parentNotExists", "父节点不存在");
        }

        // 验证父Id是否在当前节点下面
        Set<String> recursionIdSet = organizationCache.recursionListSubId(dbEntity.getType(), inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new BIamBusinessException("biam.organization.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }
        return organizationDao.updateParentId(inputDto.getId(), inputDto.getParentId());
    }

    @Override
    public BIamOrganizationDetailOutputDto detail(IdRequest request) {
        BIamOrganizationEntity entity = organizationDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamOrganizationDetailOutputDto.class);
    }

    @Override
    public List<BIamOrganizationListOutputDto> listAllByType(BIamOrganizationTypeEnum type) {
        List<BIamOrganizationEntity> entityList = organizationDao.listAllByType(type);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamOrganizationListOutputDto.class);
    }

    @Override
    public Tuples.Tuple2<String, BIamOrganizationTreeSimpleOutputDto> treeAllSimple(BIamOrganizationTypeEnum type) {
        String typeName = type.getName();
        // 获取树的动态key
        String keyCode = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_KEY + typeName);

        // 如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_DATA + typeName + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return Tuples.of(keyCode, JSONUtil.toBean(treeJson, BIamOrganizationTreeSimpleOutputDto.class));
            }
        }

        // 缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_ORGANIZATION_TREE + typeName);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_KEY + typeName);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(BIAM_ORGANIZATION_TREE_DATA + typeName + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return Tuples.of(keyCode, JSONUtil.toBean(treeJson, BIamOrganizationTreeSimpleOutputDto.class));
                }
            }

            // 重新生成树的动态key
            keyCode = leaf4RedisClient.dataOrganizationTree(type);
            customerRedisCommands.setex(BIAM_ORGANIZATION_TREE_KEY + typeName, keyCode, 24 * 60 * 60);

            // 查询DB组装树
            List<BIamOrganizationEntity> entityList = organizationDao.listAllByType(type);
            List<BIamOrganizationTreeSimpleOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList),
                    BIamOrganizationTreeSimpleOutputDto.class);
            BIamOrganizationTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            // Tree 数据缓存到redis
            customerRedisCommands.setex(
                    BIAM_ORGANIZATION_TREE_DATA + typeName + keyCode,
                    JSONUtil.toJsonStr(treeOutputDto),
                    24 * 60 * 60 + 60);

            return Tuples.of(keyCode, treeOutputDto);
        } finally {
            lock.unlock();
        }
    }
}
