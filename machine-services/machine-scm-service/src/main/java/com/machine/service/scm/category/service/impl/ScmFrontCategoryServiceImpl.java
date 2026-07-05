package com.machine.service.scm.category.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmFrontCategoryTreeOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.service.scm.category.dao.IScmBackCategoryDao;
import com.machine.service.scm.category.dao.IScmFrontBackCategoryRelationDao;
import com.machine.service.scm.category.dao.IScmFrontCategoryDao;
import com.machine.service.scm.category.dao.mapper.entity.ScmFrontBackCategoryRelationEntity;
import com.machine.service.scm.category.dao.mapper.entity.ScmFrontCategoryEntity;
import com.machine.service.scm.category.service.IScmFrontCategoryService;
import com.machine.starter.redis.cache.scm.RedisScmFrontCategoryCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.sdk.base.constant.CommonScmConstant.BackCategory.SCM_BACK_CATEGORY_ROOT_PARENT_ID;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Scm.LOCK_SCM_FRONT_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_KEY;

@Slf4j
@Service
public class ScmFrontCategoryServiceImpl implements IScmFrontCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands redisCommands;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IDataLeaf4ScmCodeClient leaf4ScmCodeClient;

    @Autowired
    private RedisScmFrontCategoryCache cacheScmFrontCategory;

    @Autowired
    private IScmBackCategoryDao backCategoryDao;

    @Autowired
    private IScmFrontCategoryDao frontCategoryDao;

    @Autowired
    private IScmFrontBackCategoryRelationDao frontBackCategoryRelationDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmFrontCategoryCreateInputDto inputDto) {
        ScmFrontCategoryEntity entityById = frontCategoryDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new ScmBusinessException("scm.frontCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        long countByName = frontCategoryDao.countByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (countByName > 0) {
            throw new ScmBusinessException("scm.frontCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        Set<String> backCategoryIdSet = inputDto.getBackCategoryIdSet();
        if (CollectionUtil.isNotEmpty(backCategoryIdSet)) {
            long backCategoryCount = backCategoryDao.countByIdSet(backCategoryIdSet);
            if (backCategoryCount < backCategoryIdSet.size()) {
                throw new ScmBusinessException("scm.frontCategory.service.create.backCategoryNotExists", "后台分类不存在");
            }
        }

        ScmFrontCategoryEntity insertEntity = new ScmFrontCategoryEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        insertEntity.setCode(leaf4ScmCodeClient.frontCategoryCode());
        insertEntity.setSort(inputDto.getSort());
        String frontCategoryId = frontCategoryDao.insert(insertEntity);

        // 前台分类和后台分类关系数据
        batchInsertRelation(frontCategoryId, inputDto.getBackCategoryIdSet());

        return frontCategoryId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        String id = request.getId();
        ScmFrontCategoryEntity entity = frontCategoryDao.getById(id);
        if (null == entity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new ScmBusinessException("scm.frontCategory.service.delete.rootNode", "根节点不能删除");
        }

        //判断是否有子节点
        Set<String> recursionSubIdSet = cacheScmFrontCategory.recursionSubId(id);
        if (cacheScmFrontCategory.recursionSubId(id).size() > 1) {
            throw new ScmBusinessException("scm.frontCategory.service.delete.hasChildrenNode", "有子节点不能删除");
        }

        // 判断是否关联前端分类
        long countByBackIdSet = frontBackCategoryRelationDao.countByBackCategoryIdSet(recursionSubIdSet);
        if (countByBackIdSet > 0) {
            throw new ScmBusinessException("scm.frontCategory.service.delete.associationBackCategory", "关联后台分类不能删除");
        }

        // todo 是否关联数据

        return frontCategoryDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmFrontCategoryUpdateInputDto inputDto) {
        String frontCategoryId = inputDto.getId();
        ScmFrontCategoryEntity entity = frontCategoryDao.getById(frontCategoryId);
        if (null == entity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new ScmBusinessException("scm.frontCategory.service.update.rootNode", "根节点不能修改");
        }

        ScmFrontCategoryEntity entityByName = frontCategoryDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new ScmBusinessException("scm.frontCategory.service.update.nameAlreadyExists", "名称已经存在");
        }

        Set<String> backCategoryIdSet = inputDto.getBackCategoryIdSet();
        if (CollectionUtil.isNotEmpty(backCategoryIdSet)) {
            long backCategoryCount = backCategoryDao.countByIdSet(backCategoryIdSet);
            if (backCategoryCount < backCategoryIdSet.size()) {
                throw new ScmBusinessException("scm.frontCategory.service.update.backCategoryNotExists", "后台分类不存在");
            }
        }

        // 处理前后台分类关系数据
        frontBackCategoryRelationDao.deleteByFrontCategoryId(inputDto.getId());
        // 前台分类和后台分类关系数据
        batchInsertRelation(frontCategoryId, inputDto.getBackCategoryIdSet());

        ScmFrontCategoryEntity updateEntity = new ScmFrontCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return frontCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(ScmFrontCategoryUpdateParentInputDto inputDto) {
        ScmFrontCategoryEntity dbEntity = frontCategoryDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.frontCategory.service.updateParent.rootNode", "根节点不能修改");
        }

        ScmFrontCategoryEntity entityByName = frontCategoryDao.getByParentIdAndName(inputDto.getParentId(), dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.frontCategory.service.updateParent.nameAlreadyExists", "名称已经存在");
        }

        ScmFrontCategoryEntity parentEntity = frontCategoryDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new ScmBusinessException("scm.frontCategory.service.updateParent.parentNotExists", "父节点不存在");
        }

        Set<String> recursionIdSet = cacheScmFrontCategory.recursionSubId(inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new ScmBusinessException("scm.frontCategory.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }

        return frontCategoryDao.updateParentId(inputDto.getId(), inputDto.getParentId());
    }

    @Override
    public ScmFrontCategoryDetailOutputDto getById(IdRequest request) {
        ScmFrontCategoryEntity entity = frontCategoryDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmFrontCategoryDetailOutputDto.class);
    }

    @Override
    public ScmFrontCategoryTreeOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = redisCommands.get(SCM_FRONT_CATEGORY_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = redisCommands.get(SCM_FRONT_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, ScmFrontCategoryTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_SCM_FRONT_CATEGORY_TREE);
        try {
            lock.lock();

            keyCode = redisCommands.get(SCM_FRONT_CATEGORY_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = redisCommands.get(SCM_FRONT_CATEGORY_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, ScmFrontCategoryTreeOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.scmFrontCategoryTree();
            redisCommands.set(SCM_FRONT_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<ScmFrontCategoryEntity> entityList = frontCategoryDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                //Tree 数据缓存到redis
                redisCommands.set(SCM_FRONT_CATEGORY_TREE_DATA + keyCode, EMPTY_LIST_STR, 24 * 60 * 60 + 60);
                return null;
            }
            List<ScmFrontCategoryTreeOutputDto> outputDtoList = new ArrayList<>();
            for (ScmFrontCategoryEntity entity : entityList) {
                ScmFrontCategoryTreeOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmFrontCategoryTreeOutputDto.class);

                outputDtoList.add(outputDto);
            }

            ScmFrontCategoryTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            redisCommands.set(SCM_FRONT_CATEGORY_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto), 24 * 60 * 60 + 60);
            redisCommands.set(SCM_FRONT_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<ScmFrontCategoryListOutputDto> listAll() {
        List<ScmFrontCategoryEntity> entityList = frontCategoryDao.listAll();
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmFrontCategoryListOutputDto.class);
    }

    private void batchInsertRelation(String frontCategoryId,
                                     Set<String> backCategoryIdSet) {
        if (CollectionUtil.isNotEmpty(backCategoryIdSet)) {
            List<ScmFrontBackCategoryRelationEntity> relationEntityList = new ArrayList<>(backCategoryIdSet.size());
            for (String backCategoryId : backCategoryIdSet) {
                ScmFrontBackCategoryRelationEntity relationEntity = new ScmFrontBackCategoryRelationEntity();
                relationEntity.setFrontCategoryId(frontCategoryId);
                relationEntity.setBackCategoryId(backCategoryId);
                relationEntity.setSort((long) backCategoryIdSet.size() - relationEntityList.size());
                relationEntityList.add(relationEntity);
            }
            frontBackCategoryRelationDao.batchInsert(relationEntityList);
        }
    }
}