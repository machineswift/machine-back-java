package com.machine.service.data.brand.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.brand.dao.IDataBrandCategoryDao;
import com.machine.service.data.brand.dao.mapper.DataBrandCategoryMapper;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandCategoryEntity;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.BrandCategory.DATA_BRAND_CATEGORY_TREE_KEY;

@Repository
public class DataBrandCategoryDaoImpl implements IDataBrandCategoryDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private DataBrandCategoryMapper brandCategoryMapper;

    @Override
    public String insert(DataBrandCategoryEntity entity) {
        brandCategoryMapper.insert(entity);

        customerRedisCommands.del(DATA_BRAND_CATEGORY_TREE_KEY);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        DataBrandCategoryEntity dbEntity = brandCategoryMapper.selectById(id);
        if (dbEntity == null) {
            return 0;
        }

        customerRedisCommands.del(DATA_BRAND_CATEGORY_TREE_KEY);
        return brandCategoryMapper.deleteById(id);
    }

    @Override
    public int update(DataBrandCategoryEntity entity) {
        DataBrandCategoryEntity dbEntity = brandCategoryMapper.selectById(entity.getId());
        if (dbEntity == null) {
            return 0;
        }

        customerRedisCommands.del(DATA_BRAND_CATEGORY_TREE_KEY);
        return brandCategoryMapper.updateById(entity);
    }

    @Override
    public DataBrandCategoryEntity getById(String id) {
        return brandCategoryMapper.selectById(id);
    }

    @Override
    public boolean existsByParentId(String parentId) {
        Wrapper<DataBrandCategoryEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryEntity>()
                .eq(DataBrandCategoryEntity::getParentId, parentId);
        return brandCategoryMapper.exists(wrapper);
    }

    @Override
    public DataBrandCategoryEntity getByParentIdAndName(String parentId, String name) {
        Wrapper<DataBrandCategoryEntity> wrapper = new LambdaQueryWrapper<DataBrandCategoryEntity>()
                .eq(DataBrandCategoryEntity::getParentId, parentId)
                .eq(DataBrandCategoryEntity::getName, name);
        return brandCategoryMapper.selectOne(wrapper);
    }

    @Override
    public List<DataBrandCategoryEntity> listAll() {
        return brandCategoryMapper.selectList(new LambdaQueryWrapper<>());
    }

}
