package com.machine.service.data.filecenter.attachment.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.service.data.filecenter.attachment.dao.IDataAttachmentVersionFileDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.DataAttachmentVersionFileMapper;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentVersionFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataAttachmentVersionFileDaoImpl implements IDataAttachmentVersionFileDao {

    @Autowired
    private DataAttachmentVersionFileMapper attachmentVersionFileMapper;

    @Override
    public String insert(DataAttachmentVersionFileEntity entity) {
        attachmentVersionFileMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataAttachmentVersionFileEntity entity) {
        return attachmentVersionFileMapper.updateById(entity);
    }

    @Override
    public DataAttachmentVersionFileEntity getById(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return attachmentVersionFileMapper.selectById(id);
    }

    @Override
    public DataAttachmentVersionFileEntity getByVersionId(String versionId) {
        if (StrUtil.isBlank(versionId)) {
            return null;
        }
        Wrapper<DataAttachmentVersionFileEntity> wrapper = new LambdaQueryWrapper<DataAttachmentVersionFileEntity>()
                .eq(DataAttachmentVersionFileEntity::getAttachmentVersionId, versionId);
        return attachmentVersionFileMapper.selectOne(wrapper);
    }

    @Override
    public List<DataAttachmentVersionFileEntity> selectByIdSet(Set<String> idSet) {
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }
        return attachmentVersionFileMapper.selectByIds(idSet);
    }

    @Override
    public List<DataAttachmentVersionFileEntity> listByVersionId(String versionId) {
        if (StrUtil.isBlank(versionId)) {
            return List.of();
        }
        Wrapper<DataAttachmentVersionFileEntity> wrapper = new LambdaQueryWrapper<DataAttachmentVersionFileEntity>()
                .eq(DataAttachmentVersionFileEntity::getAttachmentVersionId, versionId)
                .orderByDesc(DataAttachmentVersionFileEntity::getSort);
        return attachmentVersionFileMapper.selectList(wrapper);
    }

    @Override
    public List<DataAttachmentVersionFileEntity> listByVersionIdSet(Set<String> versionIdSet) {
        if (CollectionUtil.isEmpty(versionIdSet)) {
            return List.of();
        }
        Wrapper<DataAttachmentVersionFileEntity> wrapper = new LambdaQueryWrapper<DataAttachmentVersionFileEntity>()
                .in(DataAttachmentVersionFileEntity::getAttachmentVersionId, versionIdSet)
                .orderByDesc(DataAttachmentVersionFileEntity::getSort);
        return attachmentVersionFileMapper.selectList(wrapper);
    }
}
