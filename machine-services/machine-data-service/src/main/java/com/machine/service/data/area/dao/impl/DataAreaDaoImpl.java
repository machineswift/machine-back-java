package com.machine.service.data.area.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.area.dao.IDataAreaDao;
import com.machine.service.data.area.dao.mapper.DataAreaMapper;
import com.machine.service.data.area.dao.mapper.entity.DataAreaEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.machine.sdk.common.constant.CommonIamConstant.Area.ROOT_AREA_PARENT_ID;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_KEY;

@Repository
public class DataAreaDaoImpl implements IDataAreaDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private DataAreaMapper dataAreaMapper;

    @Override
    public String insert(DataAreaEntity entity) {
        dataAreaMapper.insert(entity);

        customerRedisCommands.del(DATA_AREA_TREE_KEY + findRootById(entity.getId()).getCode());

        return entity.getId();
    }

    @Override
    public int delete(String id) {
        customerRedisCommands.del(DATA_AREA_TREE_KEY + findRootById(id).getCode());

        return dataAreaMapper.deleteById(id);
    }

    @Override
    public int update(DataAreaEntity entity) {
        customerRedisCommands.del(DATA_AREA_TREE_KEY + findRootById(entity.getId()).getCode());

        DataAreaEntity dbEntity = dataAreaMapper.selectById(entity.getId());
        //修改数据
        dataAreaMapper.updateById(entity);

        if (!entity.getCode().equals(dbEntity.getCode())) {
            //修改id为编码（保持id和编码一致）
            dataAreaMapper.updateIdByCode(entity.getCode());
        }
        return 1;
    }

    @Override
    public int updateParent(String id,
                            String parentId) {

        customerRedisCommands.del(DATA_AREA_TREE_KEY + findRootById(id).getCode());

        DataAreaEntity entity = new DataAreaEntity();
        entity.setId(id);
        entity.setParentId(parentId);
        return dataAreaMapper.updateById(entity);
    }

    @Override
    public DataAreaEntity getById(String id) {
        return dataAreaMapper.selectById(id);
    }

    @Override
    public DataAreaEntity getByCode(String code) {
        Wrapper<DataAreaEntity> queryWrapper = new LambdaQueryWrapper<DataAreaEntity>()
                .eq(DataAreaEntity::getCode, code);
        return dataAreaMapper.selectOne(queryWrapper);
    }

    @Override
    public DataAreaEntity getByParentIdAndName(String parentId, String name) {
        Wrapper<DataAreaEntity> queryWrapper = new LambdaQueryWrapper<DataAreaEntity>()
                .eq(DataAreaEntity::getParentId, parentId)
                .eq(DataAreaEntity::getName, name);
        return dataAreaMapper.selectOne(queryWrapper);
    }

    @Override
    public DataAreaEntity findRootById(String id) {
        DataAreaEntity entity = dataAreaMapper.selectById(id);
        if (ROOT_AREA_PARENT_ID.equals(entity.getParentId())) {
            return entity;
        }
        return findRootById(entity.getParentId());
    }

    @Override
    public List<DataAreaEntity> selectByParentId(String parentId) {
        Wrapper<DataAreaEntity> queryWrapper = new LambdaQueryWrapper<DataAreaEntity>()
                .eq(DataAreaEntity::getParentId, parentId);
        return dataAreaMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataAreaEntity> selectByParentIdSet(Set<String> parentIdSet) {
        Wrapper<DataAreaEntity> queryWrapper = new LambdaQueryWrapper<DataAreaEntity>()
                .in(DataAreaEntity::getParentId, parentIdSet);
        return dataAreaMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataAreaEntity> findAllChildrenBFS(String code) {
        DataAreaEntity entity = getByCode(code);
        if (null == entity) {
            return List.of();
        }

        List<DataAreaEntity> result = new ArrayList<>();
        result.add(entity);

        Queue<String> currentLevel = new LinkedList<>();
        currentLevel.offer(entity.getId());

        while (!currentLevel.isEmpty()) {
            Set<String> parentIds = new HashSet<>(currentLevel);
            currentLevel.clear();

            List<DataAreaEntity> children = selectByParentIdSet(parentIds);
            result.addAll(children);

            children.forEach(child -> currentLevel.offer(child.getId()));
        }
        return result;
    }

}
