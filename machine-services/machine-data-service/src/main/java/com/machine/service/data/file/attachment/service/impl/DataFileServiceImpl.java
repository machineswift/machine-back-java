package com.machine.service.data.file.attachment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.attachment.dto.output.DataFileDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.file.attachment.dao.IDataFileDao;
import com.machine.service.data.file.attachment.dao.mapper.entity.DataFileEntity;
import com.machine.service.data.file.attachment.service.IDataFileService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DataFileServiceImpl implements IDataFileService {

    @Autowired
    private IDataFileDao fileDao;

    @Override
    public DataFileDetailOutputDto getById(IdRequest request) {
        DataFileEntity dbEntity = fileDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataFileDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataFileDetailOutputDto.class, true);
        if (StrUtil.isNotBlank(dbEntity.getFileInfo())) {
            outputDto.setFileInfo(JSONUtil.toBean(dbEntity.getFileInfo(), FileInfo.class));
        }
        return outputDto;
    }

    @Override
    public List<DataFileDetailOutputDto> listByIdSet(IdSetRequest request) {
        Set<String> idSet = request.getIdSet();
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }

        List<DataFileEntity> entityList = fileDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        List<DataFileDetailOutputDto> outputDtoList = new ArrayList<>();
        for (DataFileEntity entity : entityList) {
            DataFileDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFileDetailOutputDto.class, true);
            if (StrUtil.isNotBlank(entity.getFileInfo())) {
                outputDto.setFileInfo(JSONUtil.toBean(entity.getFileInfo(), FileInfo.class));
            }
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

}
