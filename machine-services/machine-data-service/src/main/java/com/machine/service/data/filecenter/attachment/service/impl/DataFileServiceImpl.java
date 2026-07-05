package com.machine.service.data.filecenter.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileDetailOutputDto;
import com.machine.service.data.filecenter.attachment.dao.IDataFileDao;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataFileEntity;
import com.machine.service.data.filecenter.attachment.service.IDataFileService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataFileServiceImpl implements IDataFileService {

    @Autowired
    private IDataFileDao fileDao;

    @Override
    public DataFileDetailOutputDto getById(String id) {
        DataFileEntity dbEntity = fileDao.getById(id);
        if (dbEntity == null) {
            return null;
        }

        return convertToDetail(dbEntity);
    }

    @Override
    public List<DataFileDetailOutputDto> listByIdSet(Set<String> idSet) {
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }

        List<DataFileEntity> entityList = fileDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        return entityList.stream()
                .map(this::convertToDetail)
                .collect(Collectors.toList());
    }

    private DataFileDetailOutputDto convertToDetail(DataFileEntity entity) {
        DataFileDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFileDetailOutputDto.class, true);
        if (StrUtil.isNotBlank(entity.getFileInfo())) {
            outputDto.setFileInfo(JSONUtil.toBean(entity.getFileInfo(), FileInfo.class));
        }
        return outputDto;
    }

}
