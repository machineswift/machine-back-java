package com.machine.service.scm.category.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.scm.category.dao.IScmBackCategoryDao;
import com.machine.service.scm.category.dao.mapper.ScmBackCategoryMapper;
import com.machine.service.scm.category.dao.mapper.entity.ScmBackCategoryEntity;
import com.machine.starter.redis.command.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4ScmConstant.BackCategory.SCM_BACK_CATEGORY_TREE_KEY;

@Repository
public class ScmBackCategoryDaoImpl implements IScmBackCategoryDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private ScmBackCategoryMapper backCategoryMapper;

    @Override
    public String insert(ScmBackCategoryEntity entity) {
        backCategoryMapper.insert(entity);

        //缓存
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_DATA);

        return entity.getId();
    }

    @Override
    public int deleteById(String id) {
        //缓存
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_DATA);

        return backCategoryMapper.deleteById(id);
    }


    @Override
    public int update(ScmBackCategoryEntity entity) {
        //缓存
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_DATA);

        return backCategoryMapper.updateById(entity);
    }

    @Override
    public int updateParentId(String id,
                              String parentId) {
        //缓存
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_KEY);
        customerRedisCommands.del(SCM_BACK_CATEGORY_TREE_DATA);

        ScmBackCategoryEntity entity = new ScmBackCategoryEntity();
        entity.setId(id);
        entity.setParentId(parentId);
        return backCategoryMapper.updateById(entity);
    }

    @Override
    public long countByIdSet(Collection<String> idCollection) {
        if (CollectionUtil.isEmpty(idCollection)) {
            return 0;
        }

        Wrapper<ScmBackCategoryEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryEntity>()
                .in(ScmBackCategoryEntity::getId, idCollection);
        return backCategoryMapper.selectCount(wrapper);
    }

    @Override
    public long countByNameAndParentId(String parentId,
                                       String name) {

        if (StrUtil.isBlank(parentId) || StrUtil.isBlank(name)) {
            return 0L;
        }

        Wrapper<ScmBackCategoryEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryEntity>()
                .eq(ScmBackCategoryEntity::getParentId, parentId)
                .eq(ScmBackCategoryEntity::getName, name);
        return backCategoryMapper.selectCount(wrapper);
    }

    @Override
    public ScmBackCategoryEntity getById(String id) {
        return backCategoryMapper.selectById(id);
    }

    @Override
    public ScmBackCategoryEntity getByNameAndParentId(String parentId,
                                                      String name) {
        Wrapper<ScmBackCategoryEntity> wrapper = new LambdaQueryWrapper<ScmBackCategoryEntity>()
                .eq(ScmBackCategoryEntity::getParentId, parentId)
                .eq(ScmBackCategoryEntity::getName, name);
        return backCategoryMapper.selectOne(wrapper);
    }

    @Override
    public List<ScmBackCategoryEntity> listAll() {
        return backCategoryMapper.selectList(new LambdaQueryWrapper<>());
    }

}