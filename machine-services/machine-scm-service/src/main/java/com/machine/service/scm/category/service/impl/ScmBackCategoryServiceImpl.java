package com.machine.service.scm.category.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.exception.scm.ScmBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.service.scm.category.dao.IScmBackCategoryDao;
import com.machine.service.scm.category.dao.IScmFrontBackCategoryRelationDao;
import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryEntity;
import com.machine.service.scm.category.service.IScmBackCategoryService;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
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
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Scm.LOCK_SCM_BACK_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_KEY;

@Slf4j
@Service
public class ScmBackCategoryServiceImpl implements IScmBackCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Autowired
    private IDataLeaf4ScmCodeClient leaf4ScmCodeClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IScmBackCategoryDao backCategoryDao;

    @Autowired
    private IScmFrontBackCategoryRelationDao frontBackCategoryRelationDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ScmBackCategoryCreateInputDto inputDto) {
        //验证 parentId 是否存在
        ScmBackCategoryEntity entityById = backCategoryDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new ScmBusinessException("scm.backCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        //验证名称在同一层级是否存在
        long countByName = backCategoryDao.countByNameAndParentId(inputDto.getParentId(), inputDto.getName());
        if (countByName > 0) {
            throw new ScmBusinessException("scm.backCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        ScmBackCategoryEntity insertEntity = new ScmBackCategoryEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        //生成编码
        insertEntity.setCode(leaf4ScmCodeClient.backCategoryCode());
        insertEntity.setSort(inputDto.getSort());
        return backCategoryDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(IdRequest request) {
        String id = request.getId();
        ScmBackCategoryEntity entity = backCategoryDao.getById(id);
        if (null == entity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.delete.rootNode", "根节点不能删除");
        }

        //判断是否有子节点
        Set<String> recursionSubIdSet = cacheScmBackCategory.recursionSubId(id);
        if (recursionSubIdSet.size() > 1) {
            throw new ScmBusinessException("scm.backCategory.service.delete.hasChildrenNode", "有子节点不能删除");
        }

        // 判断是否关联前端分类
        long countByBackIdSet = frontBackCategoryRelationDao.countByBackCategoryIdSet(recursionSubIdSet);
        if (countByBackIdSet > 0) {
            throw new ScmBusinessException("scm.backCategory.service.delete.associationFrontCategory", "关联前台分类不能删除");
        }


        // todo 是否关联数据

        return backCategoryDao.deleteById(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(ScmBackCategoryUpdateInputDto inputDto) {
        ScmBackCategoryEntity entity = backCategoryDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(entity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.update.rootNode", "根节点不能修改");
        }

        //验证名称在同一层级是否存在
        ScmBackCategoryEntity entityByName = backCategoryDao.getByNameAndParentId(entity.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.update.nameAlreadyExists", "名称已经存在");
        }

        ScmBackCategoryEntity updateEntity = new ScmBackCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return backCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(ScmBackCategoryUpdateParentInputDto inputDto) {
        ScmBackCategoryEntity dbEntity = backCategoryDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getParentId().equals(dbEntity.getParentId())) {
            //相同直接返回
            return 0;
        }

        if (SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getParentId()) ||
                SCM_BACK_CATEGORY_ROOT_PARENT_ID.equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.rootNode", "根节点不能修改");
        }

        //验证名称在同一层级是否存在
        ScmBackCategoryEntity entityByName = backCategoryDao.getByNameAndParentId(inputDto.getParentId(), dbEntity.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.nameAlreadyExists", "名称已经存在");
        }

        //验证父节点是否存在
        ScmBackCategoryEntity parentEntity = backCategoryDao.getById(inputDto.getParentId());
        if (null == parentEntity) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.parentNotExists", "父节点不存在");
        }

        //验证父Id是否在当前节点下面
        Set<String> recursionIdSet = cacheScmBackCategory.recursionSubId(inputDto.getId());
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new ScmBusinessException("scm.backCategory.service.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }
        return backCategoryDao.updateParentId(inputDto.getId(), inputDto.getParentId());
    }


    @Override
    public ScmBackCategoryDetailOutputDto getById(IdRequest request) {
        ScmBackCategoryEntity entity = backCategoryDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmBackCategoryDetailOutputDto.class);
    }

    @Override
    public ScmBackCategoryTreeSimpleOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotBlank(keyCode)) {
            String treeJson = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_DATA + keyCode);
            if (StrUtil.isNotBlank(treeJson)) {
                return JSONUtil.toBean(treeJson, ScmBackCategoryTreeSimpleOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_SCM_BACK_CATEGORY_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_KEY);
            if (StrUtil.isNotBlank(keyCode)) {
                String treeJson = customerRedisCommands.get(SCM_BACK_CATEGORY_TREE_DATA + keyCode);
                if (StrUtil.isNotBlank(treeJson)) {
                    return JSONUtil.toBean(treeJson, ScmBackCategoryTreeSimpleOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.scmBackCategoryTree();
            customerRedisCommands.set(SCM_BACK_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<ScmBackCategoryEntity> entityList = backCategoryDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                //Tree 数据缓存到redis
                customerRedisCommands.set(SCM_BACK_CATEGORY_TREE_DATA + keyCode, EMPTY_LIST_STR, 24 * 60 * 60 + 60);
                return null;
            }
            List<ScmBackCategoryTreeSimpleOutputDto> outputDtoList = new ArrayList<>();
            for (ScmBackCategoryEntity entity : entityList) {
                ScmBackCategoryTreeSimpleOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), ScmBackCategoryTreeSimpleOutputDto.class);

                outputDtoList.add(outputDto);
            }

            ScmBackCategoryTreeSimpleOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(SCM_BACK_CATEGORY_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto), 24 * 60 * 60 + 60);
            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<ScmBackCategoryListOutputDto> listAll() {
        List<ScmBackCategoryEntity> entityList = backCategoryDao.listAll();
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), ScmBackCategoryListOutputDto.class);
    }

}