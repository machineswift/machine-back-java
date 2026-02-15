
package com.machine.service.data.file.attachment.dao.impl;

import com.machine.service.data.file.attachment.dao.IDataFileDao;
import com.machine.service.data.file.attachment.dao.mapper.DataFileMapper;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataFileEntity;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.File.DATA_DOCUMENT_FILE_THUMBNAIL_URL_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.File.DATA_DOCUMENT_FILE_URL_KEY;

@Repository
public class DataFileDaoImpl implements IDataFileDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    public DataFileMapper fileMapper;

    @Override
    public String insert(DataFileEntity entity) {
        fileMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int update(DataFileEntity entity) {

        //缓存
        customerRedisCommands.del(DATA_DOCUMENT_FILE_URL_KEY + entity.getId());
        customerRedisCommands.del(DATA_DOCUMENT_FILE_THUMBNAIL_URL_KEY + entity.getId());

        return fileMapper.updateById(entity);
    }

    @Override
    public DataFileEntity getById(String id) {
        return fileMapper.selectById(id);
    }

    @Override
    public List<DataFileEntity> selectByIdSet(Set<String> idSet) {
        return fileMapper.selectByIds(idSet);
    }

}
