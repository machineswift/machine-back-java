package com.machine.service.data.leaf.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.leaf.dao.ILeafDao;
import com.machine.service.data.leaf.dao.mapper.LeafMapper;
import com.machine.service.data.leaf.dao.mapper.entity.LeafEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LeafDaoImpl implements ILeafDao {

    @Autowired
    private LeafMapper leafMapper;

    @Autowired
    private LeafTransactional leafTransactional;

    @Override
    public LeafEntity updateMaxId(LeafEntity entity) {
        if (StrUtil.isEmpty(entity.getBizTag())) {
            return null;
        }

        Wrapper<LeafEntity> queryWrapper = new LambdaQueryWrapper<LeafEntity>()
                .eq(LeafEntity::getBizTag, entity.getBizTag());

        LeafEntity leafAlloc = leafMapper.selectOne(queryWrapper);
        if (null == leafAlloc) {
            if (null == entity.getStep() || entity.getStep() < 1) {
                return null;
            }
            leafAlloc = JSONUtil.toBean(JSONUtil.toJsonStr(entity), LeafEntity.class);
            leafTransactional.insert(leafAlloc);
            if (null != leafAlloc.getId()) {
                leafAlloc = leafMapper.selectOne(queryWrapper);
            }
        }

        Integer result = leafTransactional.updateMaxId(leafAlloc);
        while (null == result || result < 1) {
            leafAlloc = leafMapper.selectOne(queryWrapper);
            result = leafTransactional.updateMaxId(leafAlloc);
        }
        return leafAlloc;
    }
}
