package com.machine.service.scm.category.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.category.dao.IScmFrontCategoryDao;
import com.machine.service.scm.category.dao.mapper.ScmFrontCategoryMapper;
import com.machine.service.scm.category.dao.mapper.entity.ScmFrontCategoryEntity;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.FrontCategory.SCM_FRONT_CATEGORY_TREE_KEY;

@Repository
public class ScmFrontCategoryDaoImpl implements IScmFrontCategoryDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private ScmFrontCategoryMapper frontCategoryMapper;

    @Override
    public String insert(ScmFrontCategoryEntity entity) {
        frontCategoryMapper.insert(entity);

        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_DATA);

        return entity.getId();
    }

    @Override
    public int update(ScmFrontCategoryEntity entity) {
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_DATA);

        return frontCategoryMapper.updateById(entity);
    }

    @Override
    public int updateParentId(String id,
                              String parentId) {
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_DATA);

        ScmFrontCategoryEntity entity = new ScmFrontCategoryEntity();
        entity.setId(id);
        entity.setParentId(parentId);
        return frontCategoryMapper.updateById(entity);
    }

    @Override
    public int deleteById(String id) {
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_FRONT_CATEGORY_TREE_DATA);

        return frontCategoryMapper.deleteById(id);
    }

    @Override
    public long countByParentIdAndName(String parentId,
                                       String name) {
        if(StrUtil.isBlank(name) || StrUtil.isBlank(parentId)){
            return 0L;
        }

        Wrapper<ScmFrontCategoryEntity> wrapper = new LambdaQueryWrapper<ScmFrontCategoryEntity>()
                .eq(ScmFrontCategoryEntity::getParentId, parentId)
                .eq(ScmFrontCategoryEntity::getName, name);
        return frontCategoryMapper.selectCount(wrapper);
    }

    @Override
    public ScmFrontCategoryEntity getById(String id) {
        return frontCategoryMapper.selectById(id);
    }

    @Override
    public ScmFrontCategoryEntity getByParentIdAndName(String parentId,
                                                       String name) {
        Wrapper<ScmFrontCategoryEntity> wrapper = new LambdaQueryWrapper<ScmFrontCategoryEntity>()
                .eq(ScmFrontCategoryEntity::getParentId, parentId)
                .eq(ScmFrontCategoryEntity::getName, name);
        return frontCategoryMapper.selectOne(wrapper);
    }

    @Override
    public List<ScmFrontCategoryEntity> listAll() {
        return frontCategoryMapper.selectList(new LambdaQueryWrapper<>());
    }
}