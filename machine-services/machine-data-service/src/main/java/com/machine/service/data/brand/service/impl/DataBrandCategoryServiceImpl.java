package com.machine.service.data.brand.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.brand.dto.input.DataBrandCategoryCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateParentInputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryListOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryTreeSimpleOutputDto;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.data.brand.dao.IDataBrandCategoryDao;
import com.machine.service.data.brand.dao.IDataBrandCategoryRelationDao;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandCategoryEntity;
import com.machine.service.data.brand.service.IDataBrandCategoryService;
import com.machine.starter.redis.cache.data.RedisDataBrandCategoryCache;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.base.constant.CommonDataConstant.BrandCategory.DATA_BRAND_CATEGORY_ROOT_ID;
import static com.machine.sdk.base.constant.CommonDataConstant.BrandCategory.DATA_BRAND_CATEGORY_ROOT_PARENT_ID;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_BRAND_CATEGORY_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.BrandCategory.DATA_BRAND_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.BrandCategory.DATA_BRAND_CATEGORY_TREE_KEY;

@Service
public class DataBrandCategoryServiceImpl implements IDataBrandCategoryService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisDataBrandCategoryCache brandCategoryCache;

    @Autowired
    private IDataBrandCategoryDao brandCategoryDao;

    @Autowired
    private IDataBrandCategoryRelationDao brandCategoryRelationDao;

    @Autowired
    private IDataLeaf4DataCodeClient leaf4DataCodeClient;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataBrandCategoryCreateInputDto inputDto) {
        DataBrandCategoryEntity parent = brandCategoryDao.getById(inputDto.getParentId());
        if (parent == null) {
            throw new DataBusinessException("data.brandCategory.service.create.parentIdNotExists", "父ID不存在");
        }

        DataBrandCategoryEntity sameNameCategory =
                brandCategoryDao.getByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (sameNameCategory != null) {
            throw new DataBusinessException("data.brandCategory.service.create.nameAlreadyExists", "名称已经存在");
        }

        DataBrandCategoryEntity entity = new DataBrandCategoryEntity();
        entity.setParentId(parent.getId());
        entity.setName(inputDto.getName());
        entity.setCode(leaf4DataCodeClient.brandCategoryCode());
        entity.setSort(inputDto.getSort() == null ? 0L : inputDto.getSort());
        return brandCategoryDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataBrandCategoryEntity entity = brandCategoryDao.getById(request.getId());
        if (entity == null) {
            return 0;
        }
        if (isRoot(entity)) {
            throw new DataBusinessException("data.brandCategory.service.delete.rootCategory", "根分类不能删除");
        }

        if (brandCategoryDao.existsByParentId(entity.getId())) {
            throw new DataBusinessException("data.brandCategory.service.delete.hasChildrenNode", "有子节点不能删除");
        }

        if (brandCategoryRelationDao.selectCountByCategoryId(entity.getId()) > 0) {
            throw new DataBusinessException("data.brandCategory.service.delete.associationBrand",
                    "此分类存在关联品牌，不能删除");
        }

        return brandCategoryDao.delete(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataBrandCategoryUpdateInputDto inputDto) {
        DataBrandCategoryEntity entity = brandCategoryDao.getById(inputDto.getId());
        if (entity == null) {
            return 0;
        }
        if (isRoot(entity)) {
            throw new DataBusinessException("data.brandCategory.service.update.rootCategory", "根分类不能修改");
        }

        DataBrandCategoryEntity sameNameCategory =
                brandCategoryDao.getByParentIdAndName(entity.getParentId(), inputDto.getName());
        if (sameNameCategory != null && !sameNameCategory.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.brandCategory.service.update.nameAlreadyExists", "分类名称已经存在");
        }

        DataBrandCategoryEntity updateEntity = new DataBrandCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setName(inputDto.getName());
        updateEntity.setSort(inputDto.getSort());
        return brandCategoryDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(DataBrandCategoryUpdateParentInputDto inputDto) {
        DataBrandCategoryEntity entity = brandCategoryDao.getById(inputDto.getId());
        if (entity == null) {
            return 0;
        }
        if (isRoot(entity)) {
            throw new DataBusinessException("data.brandCategory.service.updateParent.rootCategory", "根分类不能修改");
        }

        DataBrandCategoryEntity parent = brandCategoryDao.getById(inputDto.getParentId());
        if (parent == null) {
            throw new DataBusinessException("data.brandCategory.service.updateParent.parentNotExists", "父分类不存在");
        }

        if (entity.getParentId().equals(inputDto.getParentId())) {
            return 0;
        }

        DataBrandCategoryEntity sameNameCategory =
                brandCategoryDao.getByParentIdAndName(inputDto.getParentId(), entity.getName());
        if (sameNameCategory != null && !sameNameCategory.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.brandCategory.service.updateParent.nameAlreadyExists",
                    "分类名称已经存在");
        }

        Set<String> descendantIdSet = brandCategoryCache.recursionListSubId(entity.getId());
        if (descendantIdSet.contains(inputDto.getParentId())) {
            throw new DataBusinessException("data.brandCategory.service.updateParent.parentHasInCurrent",
                    "父节点在当前节点下面");
        }

        DataBrandCategoryEntity updateEntity = new DataBrandCategoryEntity();
        updateEntity.setId(inputDto.getId());
        updateEntity.setParentId(inputDto.getParentId());
        return brandCategoryDao.update(updateEntity);
    }

    @Override
    public DataBrandCategoryDetailOutputDto detail(IdRequest request) {
        DataBrandCategoryEntity entity = brandCategoryDao.getById(request.getId());
        if (entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataBrandCategoryDetailOutputDto.class);
    }

    @Override
    public List<DataBrandCategoryListOutputDto> listAll() {
        List<DataBrandCategoryEntity> entityList = brandCategoryDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataBrandCategoryListOutputDto.class);
    }

    @Override
    public Tuples.Tuple2<String, DataBrandCategoryTreeSimpleOutputDto> treeAllSimple() {
        String keyCode = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_KEY);
        if (keyCode != null && !keyCode.isBlank()) {
            String treeJson = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_DATA + keyCode);
            if (treeJson != null && !treeJson.isBlank()) {
                return Tuples.of(keyCode, JSONUtil.toBean(treeJson, DataBrandCategoryTreeSimpleOutputDto.class));
            }
        }

        RLock lock = redissonClient.getLock(LOCK_DATA_BRAND_CATEGORY_TREE);
        lock.lock();
        try {
            keyCode = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_KEY);
            if (keyCode != null && !keyCode.isBlank()) {
                String treeJson = customerRedisCommands.get(DATA_BRAND_CATEGORY_TREE_DATA + keyCode);
                if (treeJson != null && !treeJson.isBlank()) {
                    return Tuples.of(keyCode, JSONUtil.toBean(treeJson, DataBrandCategoryTreeSimpleOutputDto.class));
                }
            }

            keyCode = leaf4RedisClient.dataBrandCategoryTree();
            customerRedisCommands.setex(DATA_BRAND_CATEGORY_TREE_KEY, keyCode, 24 * 60 * 60);

            List<DataBrandCategoryEntity> entityList = brandCategoryDao.listAll();
            List<DataBrandCategoryTreeSimpleOutputDto> nodeList =
                    JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataBrandCategoryTreeSimpleOutputDto.class);
            List<DataBrandCategoryTreeSimpleOutputDto> roots = TreeUtil.buildTree(nodeList);
            DataBrandCategoryTreeSimpleOutputDto root = roots.stream()
                    .filter(node -> DATA_BRAND_CATEGORY_ROOT_ID.equals(node.getId()))
                    .findFirst()
                    .orElseThrow(() -> new DataBusinessException(
                            "data.brandCategory.service.tree.rootNotExists", "品牌分类根节点不存在"));

            customerRedisCommands.setex(DATA_BRAND_CATEGORY_TREE_DATA + keyCode,
                    JSONUtil.toJsonStr(root), 24 * 60 * 60 + 60);
            return Tuples.of(keyCode, root);
        } finally {
            lock.unlock();
        }
    }

    private boolean isRoot(DataBrandCategoryEntity entity) {
        return DATA_BRAND_CATEGORY_ROOT_ID.equals(entity.getId())
                || DATA_BRAND_CATEGORY_ROOT_PARENT_ID.equals(entity.getParentId());
    }
}
