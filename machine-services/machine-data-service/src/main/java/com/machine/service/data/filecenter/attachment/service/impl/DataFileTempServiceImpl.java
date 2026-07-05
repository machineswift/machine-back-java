package com.machine.service.data.filecenter.attachment.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;
import com.machine.service.data.filecenter.attachment.dao.IDataFileTempDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataFileTempEntity;
import com.machine.service.data.filecenter.attachment.service.IDataFileTempService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataFileTempServiceImpl implements IDataFileTempService {

    @Autowired
    private IDataFileTempDao dataFileTempDao;

    @Override
    public String create(DataFileTempCreateInputDto inputDto) {
        DataFileTempEntity entity = new DataFileTempEntity();
        entity.setFileType(inputDto.getFileType());
        entity.setOriginalName(inputDto.getOriginalName());
        entity.setStorageName(inputDto.getStorageName());
        entity.setStoragePath(inputDto.getStoragePath());
        entity.setFileInfo(inputDto.getFileInfo());
        entity.setSize(inputDto.getSize());
        entity.setExpireTime(inputDto.getExpireTime());
        return dataFileTempDao.insert(entity);
    }

    @Override
    public DataFileTempDetailOutputDto getById(String id) {
        DataFileTempEntity entity = dataFileTempDao.getById(id);
        if (entity == null) {
            return null;
        }

        DataFileTempDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFileTempDetailOutputDto.class, true);
        outputDto.setFileInfo(JSONUtil.toBean(entity.getFileInfo(), FileInfo.class));
        return outputDto;
    }
}
