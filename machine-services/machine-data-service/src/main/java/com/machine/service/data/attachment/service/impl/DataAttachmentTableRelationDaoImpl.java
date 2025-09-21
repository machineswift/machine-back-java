package com.machine.service.data.attachment.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.system.SystemTableNameEnum;
import com.machine.service.data.attachment.dao.IDataAttachmentTableRelationDao;
import com.machine.service.data.attachment.dao.mapper.DataAttachmentTableRelationMapper;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentTableRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DataAttachmentTableRelationDaoImpl implements IDataAttachmentTableRelationDao {

    @Autowired
    private DataAttachmentTableRelationMapper attachmentTableRelationMapper;

    @Override
    public void insert(DataAttachmentTableRelationEntity entity) {
        attachmentTableRelationMapper.insert(entity);
    }

    @Override
    public DataAttachmentTableRelationEntity selectByUk(String attachmentId,
                                                        SystemTableNameEnum tableName,
                                                        String dataId) {
        Wrapper<DataAttachmentTableRelationEntity> queryWrapper = new LambdaQueryWrapper<DataAttachmentTableRelationEntity>()
                .eq(DataAttachmentTableRelationEntity::getAttachmentId, attachmentId)
                .eq(DataAttachmentTableRelationEntity::getTableName, tableName)
                .eq(DataAttachmentTableRelationEntity::getDataId, dataId);
        return attachmentTableRelationMapper.selectOne(queryWrapper);
    }
}
