package com.machine.service.data.area.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.area.dto.input.DataAreaCreateInputDto;
import com.machine.client.data.area.dto.input.DataAreaTreeInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateParentInputDto;
import com.machine.client.data.area.dto.output.DataAreaDetailOutputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.data.area.dao.IDataAreaDao;
import com.machine.service.data.area.dao.mapper.entity.DataAreaEntity;
import com.machine.service.data.area.service.IDataAreaService;
import com.machine.starter.redis.cache.data.RedisCacheDataArea;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonIamConstant.Area.ROOT_AREA_PARENT_ID;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_AREA_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_KEY;

@Slf4j
@Service
public class DataAreaServiceImpl implements IDataAreaService {

    @Autowired
    private RedisCacheDataArea areaCache;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataAreaDao areaDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataAreaCreateInputDto inputDto) {
        //验证 parentId 是否存在
        DataAreaEntity entityById = areaDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new DataBusinessException("data.area.service.create.parentIdNotExists", "父ID不存在");
        }

        //验证编码是否存在
        DataAreaEntity entityByCode = areaDao.getByCode(inputDto.getCode());
        if (null != entityByCode) {
            throw new DataBusinessException("data.area.service.create.codeAlreadyExists", "区域编码已经存在");
        }

        //验证名称在同一层级是否存在
        DataAreaEntity entityByName = areaDao.getByParentIdAndName(inputDto.getParentId(), inputDto.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.area.service.create.nameAlreadyExists", "区域名称已经存在");
        }

        DataAreaEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataAreaEntity.class);
        return areaDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataAreaEntity entityById = areaDao.getById(request.getId());
        if (null == entityById) {
            return 0;
        }

        if (ROOT_AREA_PARENT_ID.equals(entityById.getParentId())) {
            throw new DataBusinessException("data.area.service.delete.rootArea", "根区域不能删除");
        }

        List<DataAreaEntity> entityListByParentId = areaDao.selectByParentId(request.getId());
        if (CollectionUtil.isNotEmpty(entityListByParentId)) {
            throw new DataBusinessException("data.area.service.delete.hasChildren", "有子节点不能删除");
        }

        return areaDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataAreaUpdateInputDto inputDto) {
        DataAreaEntity entityById = areaDao.getById(inputDto.getId());
        if (null == entityById) {
            return 0;
        }

        if (ROOT_AREA_PARENT_ID.equals(entityById.getParentId())) {
            throw new DataBusinessException("data.area.service.update.rootArea", "根区域不能修改");
        }

        //验证边编码是否存在
        DataAreaEntity entityByCode = areaDao.getByCode(inputDto.getName());
        if (null != entityByCode && !entityByCode.getId().equals(entityById.getId())) {
            throw new DataBusinessException("data.area.service.update.codeAlreadyExists", "区域编码已经存在");
        }

        //验证名称在同一层级是否存在
        DataAreaEntity entityByName = areaDao.getByParentIdAndName(entityById.getParentId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entityById.getId())) {
            throw new DataBusinessException("data.area.service.update.nameAlreadyExists", "区域名称已经存在");
        }

        DataAreaEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataAreaEntity.class);
        return areaDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(DataAreaUpdateParentInputDto inputDto) {
        DataAreaEntity entityById = areaDao.getById(inputDto.getId());
        if (null == entityById) {
            return 0;
        }

        if (ROOT_AREA_PARENT_ID.equals(entityById.getParentId())) {
            throw new DataBusinessException("data.area.service.update.rootArea", "根区域不能修改");
        }

        if (entityById.getParentId().equals(inputDto.getParentId())) {
            return 0;
        }

        //验证名称在同一层级是否存在
        DataAreaEntity entityByName = areaDao.getByParentIdAndName(inputDto.getParentId(), entityById.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.area.service.update.nameAlreadyExists", "区域名称已经存在");
        }

        //验证区域门是否存在
        DataAreaEntity parentEntity = areaDao.getById(inputDto.getId());
        if (null == parentEntity) {
            throw new DataBusinessException("data.area.service.updateParent.parentNotExists", "父区域不存在");
        }

        //验证父Id是否在当前节点下面
        DataAreaTreeOutputDto areaTree = areaCache.tree(areaDao.findRootById(inputDto.getId()).getCode());
        Set<String> recursionIdSet = areaCache.recursionListSubId(inputDto.getId(), areaTree);
        if (recursionIdSet.contains(inputDto.getParentId())) {
            throw new DataBusinessException("data.area.service.updateParent.updateParent.parentHasInCurrent", "父节点在当前节点下面");
        }
        return areaDao.updateParent(inputDto.getId(),inputDto.getParentId());
    }

    @Override
    public DataAreaDetailOutputDto detail(IdRequest request) {
        DataAreaEntity entity = areaDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataAreaDetailOutputDto.class);
    }


    @Override
    public DataAreaTreeOutputDto tree(DataAreaTreeInputDto inputDto) {
        String countryCode = inputDto.getCountryCode();

        //获取区域树的动态key
        String keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY + countryCode);
        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + countryCode + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, DataAreaTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_AREA_TREE + countryCode);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY + countryCode);
            if (StrUtil.isNotEmpty(keyCode)) {
                String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + countryCode + keyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    return JSONUtil.toBean(treeJson, DataAreaTreeOutputDto.class);
                }
            }

            //重新生成区域树的动态key
            keyCode = leaf4RedisClient.dataAreaTree();
            customerRedisCommands.set(DATA_AREA_TREE_KEY + countryCode, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<DataAreaEntity> entityList = areaDao.findAllChildrenBFS(countryCode);

            DataAreaTreeOutputDto treeOutputDto= null;
            if(CollectionUtil.isNotEmpty(entityList)){
                List<DataAreaTreeOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataAreaTreeOutputDto.class);
                 treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();
                TreeUtil.sortTreeNodes(treeOutputDto.getChildren(), true);
                //Tree 数据缓存到redis
                customerRedisCommands.set(DATA_AREA_TREE_DATA + countryCode + keyCode, JSONUtil.toJsonStr(treeOutputDto), 24 * 60 * 60 + 60);
            }else {
                //Tree 数据缓存到redis
                customerRedisCommands.set(DATA_AREA_TREE_DATA + countryCode + keyCode, "{}", 24 * 60 * 60 + 60);
            }
            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }
}
