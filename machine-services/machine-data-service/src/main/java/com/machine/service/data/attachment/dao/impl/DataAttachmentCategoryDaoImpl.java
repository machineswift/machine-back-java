package com.machine.service.data.attachment.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.attachment.dao.IDataAttachmentCategoryDao;
import com.machine.service.data.attachment.dao.mapper.DataAttachmentCategoryMapper;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataAttachmentCategoryDaoImpl implements IDataAttachmentCategoryDao {

    @Autowired
    private DataAttachmentCategoryMapper attachmentCategoryMapper;

    @Override
    public String insert(DataAttachmentCategoryEntity entity) {
        attachmentCategoryMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        return attachmentCategoryMapper.deleteById(id);
    }

    @Override
    public int update(DataAttachmentCategoryEntity entity) {
        return attachmentCategoryMapper.updateById(entity);
    }

    @Override
    public DataAttachmentCategoryEntity getById(String id) {
        return attachmentCategoryMapper.selectById(id);
    }

    @Override
    public DataAttachmentCategoryEntity getByParentIdAndName(String parentId,
                                                             String name) {
        Wrapper<DataAttachmentCategoryEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentCategoryEntity>()
                .eq(DataAttachmentCategoryEntity::getParentId, parentId)
                .eq(DataAttachmentCategoryEntity::getName, name);
        return attachmentCategoryMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataAttachmentCategoryEntity> selectByIdSet(Set<String> idSet) {
        return attachmentCategoryMapper.selectByIds(idSet);
    }

    @Override
    public List<DataAttachmentCategoryEntity> listAll() {
        Wrapper<DataAttachmentCategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        return attachmentCategoryMapper.selectList(queryWrapper);
    }
}
