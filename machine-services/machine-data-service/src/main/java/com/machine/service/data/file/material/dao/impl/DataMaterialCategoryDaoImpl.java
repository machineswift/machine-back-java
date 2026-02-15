package com.machine.service.data.file.material.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.file.material.dao.IDataMaterialCategoryDao;
import com.machine.service.data.file.material.dao.mapper.DataMaterialCategoryMapper;
import com.machine.service.data.file.material.dao.mapper.entity.DataMaterialCategoryEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_TREE_KEY;

@Repository
public class DataMaterialCategoryDaoImpl implements IDataMaterialCategoryDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private DataMaterialCategoryMapper materialCategoryMapper;

    @Override
    public String insert(DataMaterialCategoryEntity entity) {
        materialCategoryMapper.insert(entity);

        //缓存
        customerRedisCommands.del(DATA_MATERIAL_CATEGORY_TREE_KEY);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        DataMaterialCategoryEntity dbEntity = materialCategoryMapper.selectById(id);
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(DATA_MATERIAL_CATEGORY_TREE_KEY );
        return materialCategoryMapper.deleteById(id);
    }

    @Override
    public int update(DataMaterialCategoryEntity entity) {
        DataMaterialCategoryEntity dbEntity = materialCategoryMapper.selectById(entity.getId());
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(DATA_MATERIAL_CATEGORY_TREE_KEY );
        return materialCategoryMapper.updateById(entity);
    }

    @Override
    public DataMaterialCategoryEntity getById(String id) {
        return materialCategoryMapper.selectById(id);
    }

    @Override
    public DataMaterialCategoryEntity getByParentIdAndName(String parentId, String name) {
        Wrapper<DataMaterialCategoryEntity> queryWrapper = new LambdaQueryWrapper<DataMaterialCategoryEntity>()
                .eq(DataMaterialCategoryEntity::getParentId, parentId)
                .eq(DataMaterialCategoryEntity::getName, name);
        return materialCategoryMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataMaterialCategoryEntity> selectByIdSet(Set<String> idSet) {
        return materialCategoryMapper.selectByIds(idSet);
    }

    @Override
    public List<DataMaterialCategoryEntity> listAll() {
        Wrapper<DataMaterialCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        return materialCategoryMapper.selectList(queryWrapper);
    }
}
