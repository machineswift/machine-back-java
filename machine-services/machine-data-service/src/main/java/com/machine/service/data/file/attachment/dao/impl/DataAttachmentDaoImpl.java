package com.machine.service.data.file.attachment.dao.impl;

import com.machine.service.data.file.attachment.dao.IDataAttachmentDao;
import com.machine.service.data.file.attachment.dao.mapper.DataAttachmentMapper;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataAttachmentEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Attachment.DATA_ATTACHMENT_URL_KEY;

@Repository
public class DataAttachmentDaoImpl implements IDataAttachmentDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    public DataAttachmentMapper attachmentMapper;

    @Override
    public String insert(DataAttachmentEntity entity) {
        attachmentMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataAttachmentEntity entity) {

        //缓存
        customerRedisCommands.del(DATA_ATTACHMENT_URL_KEY + entity.getId());

        return attachmentMapper.updateById(entity);
    }

    @Override
    public DataAttachmentEntity getById(String id) {
        return attachmentMapper.selectById(id);
    }

    @Override
    public List<DataAttachmentEntity> selectByIdSet(Set<String> idSet) {
        return attachmentMapper.selectByIds(idSet);
    }
}
