package com.machine.service.data.tag.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.data.tag.ProfileSubjectTypeEnum;
import com.machine.service.data.tag.dao.IDataTagCategoryDao;
import com.machine.service.data.tag.dao.mapper.DataTagCategoryMapper;
import com.machine.service.data.tag.dao.mapper.entity.DataTagCategoryEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.TagCategory.DATA_TAG_CATEGORY_TREE_KEY;

@Slf4j
@Component
public class DataTagCategoryDaoImpl implements IDataTagCategoryDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private DataTagCategoryMapper tagCategoryMapper;

    @Override
    public String insert(DataTagCategoryEntity entity) {
        tagCategoryMapper.insert(entity);

        //缓存
        customerRedisCommands.del(DATA_TAG_CATEGORY_TREE_KEY + entity.getType().getName());

        return entity.getId();
    }

    @Override
    public int delete(String id) {
        DataTagCategoryEntity dbEntity = tagCategoryMapper.selectById(id);
        if (dbEntity == null) {
            return 0;
        }

        //缓存
        customerRedisCommands.del(DATA_TAG_CATEGORY_TREE_KEY + dbEntity.getType().getName());

        return tagCategoryMapper.deleteById(id);
    }

    @Override
    public int update(DataTagCategoryEntity entity) {
        //缓存
        customerRedisCommands.del(DATA_TAG_CATEGORY_TREE_KEY + entity.getType().getName());

        return tagCategoryMapper.updateById(entity);
    }

    @Override
    public DataTagCategoryEntity getById(String id) {
        return tagCategoryMapper.selectById(id);
    }

    @Override
    public DataTagCategoryEntity getByParentIdAndName(String parentId,
                                                      String name) {
        Wrapper<DataTagCategoryEntity> queryWrapper = new LambdaQueryWrapper<DataTagCategoryEntity>()
                .eq(DataTagCategoryEntity::getParentId, parentId)
                .eq(DataTagCategoryEntity::getName, name);
        return tagCategoryMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataTagCategoryEntity> listAllByType(ProfileSubjectTypeEnum type) {
        Wrapper<DataTagCategoryEntity> queryWrapper = new LambdaQueryWrapper<DataTagCategoryEntity>()
                .eq(DataTagCategoryEntity::getType, type);
        return tagCategoryMapper.selectList(queryWrapper);
    }
}

