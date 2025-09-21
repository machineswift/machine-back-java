package com.machine.service.data.leaf.dao.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.leaf.dao.IDataLeafDao;
import com.machine.service.data.leaf.dao.mapper.DataLeafMapper;
import com.machine.service.data.leaf.dao.mapper.entity.DataLeafEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataLeafDaoImpl implements IDataLeafDao {

    @Autowired
    private DataLeafMapper dataLeafMapper;

    @Autowired
    private DataLeafTransactional dataLeafTransactional;

    @Override
    public DataLeafEntity updateMaxId(DataLeafEntity entity) {
        if (StrUtil.isEmpty(entity.getBizTag())) {
            return null;
        }

        Wrapper<DataLeafEntity> queryWrapper = new LambdaQueryWrapper<DataLeafEntity>()
                .eq(DataLeafEntity::getBizTag, entity.getBizTag());

        DataLeafEntity leafAlloc = dataLeafMapper.selectOne(queryWrapper);
        if (null == leafAlloc) {
            if (null == entity.getStep() || entity.getStep() < 1) {
                return null;
            }
            leafAlloc = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataLeafEntity.class);
            dataLeafTransactional.insert(leafAlloc);
            if (null != leafAlloc.getId()) {
                leafAlloc = dataLeafMapper.selectOne(queryWrapper);
            }
        }

        Integer result = dataLeafTransactional.updateMaxId(leafAlloc);
        while (null == result || result < 1) {
            leafAlloc = dataLeafMapper.selectOne(queryWrapper);
            result = dataLeafTransactional.updateMaxId(leafAlloc);
        }
        return leafAlloc;
    }
}
