package com.machine.service.data.filecenter.attachment.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.service.data.filecenter.attachment.dao.IDataAttachmentDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.DataAttachmentMapper;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class DataAttachmentDaoImpl implements IDataAttachmentDao {

    @Autowired
    public DataAttachmentMapper attachmentMapper;

    @Override
    public String insert(DataAttachmentEntity entity) {
        attachmentMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataAttachmentEntity entity) {
        return attachmentMapper.updateById(entity);
    }

    @Override
    public DataAttachmentEntity getById(String id) {
        return attachmentMapper.selectById(id);
    }

    @Override
    public DataAttachmentEntity getByUk(ModuleEntityEnum entityEnum,
                                        String entityId,
                                        String attachmentGroup) {
        if (null == entityEnum || StrUtil.isBlank(entityId) || StrUtil.isBlank(attachmentGroup)) {
            return null;
        }

        Wrapper<DataAttachmentEntity> wrapper = new LambdaQueryWrapper<DataAttachmentEntity>()
                .eq(DataAttachmentEntity::getEntity, entityEnum)
                .eq(DataAttachmentEntity::getEntityId, entityId)
                .eq(DataAttachmentEntity::getAttachmentGroup, attachmentGroup);
        return attachmentMapper.selectOne(wrapper);
    }

    @Override
    public List<DataAttachmentEntity> selectByIdSet(Set<String> idSet) {
        return attachmentMapper.selectByIds(idSet);
    }
}
