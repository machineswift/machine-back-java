package com.machine.service.data.filecenter.attachment.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.service.data.filecenter.attachment.dao.IDataAttachmentVersionDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.DataAttachmentVersionMapper;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentVersionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class DataAttachmentVersionDaoImpl implements IDataAttachmentVersionDao {

    @Autowired
    private DataAttachmentVersionMapper attachmentVersionMapper;

    @Override
    public String insert(DataAttachmentVersionEntity entity) {
        attachmentVersionMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataAttachmentVersionEntity entity) {
        return attachmentVersionMapper.updateById(entity);
    }

    @Override
    public DataAttachmentVersionEntity getById(String id) {
        if (StrUtil.isBlank(id)) {
            return null;
        }
        return attachmentVersionMapper.selectById(id);
    }

    @Override
    public DataAttachmentVersionEntity getCurrentVersion(String attachmentId) {
        if (StrUtil.isBlank(attachmentId)) {
            return null;
        }
        Wrapper<DataAttachmentVersionEntity> wrapper = new LambdaQueryWrapper<DataAttachmentVersionEntity>()
                .eq(DataAttachmentVersionEntity::getAttachmentId, attachmentId)
                .eq(DataAttachmentVersionEntity::getIsCurrent, 1);
        return attachmentVersionMapper.selectOne(wrapper);
    }

    @Override
    public DataAttachmentVersionEntity getCurrentVersion(ModuleEntityEnum entityEnum,
                                                         String entityId,
                                                         String attachmentGroup) {
        if (null == entityEnum || StrUtil.isBlank(entityId) || StrUtil.isBlank(attachmentGroup)) {
            return null;
        }
        Wrapper<DataAttachmentVersionEntity> wrapper = new LambdaQueryWrapper<DataAttachmentVersionEntity>()
                .eq(DataAttachmentVersionEntity::getEntity, entityEnum)
                .eq(DataAttachmentVersionEntity::getEntityId, entityId)
                .eq(DataAttachmentVersionEntity::getAttachmentGroup, attachmentGroup)
                .eq(DataAttachmentVersionEntity::getIsCurrent, 1);
        return attachmentVersionMapper.selectOne(wrapper);
    }

    @Override
    public DataAttachmentVersionEntity getTargetVersionNo(String attachmentId,
                                                          Integer targetVersionNo) {
        if (StrUtil.isBlank(attachmentId) || null == targetVersionNo) {
            return null;
        }
        Wrapper<DataAttachmentVersionEntity> wrapper = new LambdaQueryWrapper<DataAttachmentVersionEntity>()
                .eq(DataAttachmentVersionEntity::getAttachmentId, attachmentId)
                .eq(DataAttachmentVersionEntity::getIsCurrent, targetVersionNo);
        return attachmentVersionMapper.selectOne(wrapper);
    }

    @Override
    public List<DataAttachmentVersionEntity> selectByIds(Collection<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return List.of();
        }
        return attachmentVersionMapper.selectByIds(ids);
    }

}
