package com.machine.service.data.external.service.impl;


import cn.hutool.json.JSONUtil;
import com.machine.client.data.external.dto.input.DataExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.DataExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.dao.IDataExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.DataExternalFieldDataEntity;
import com.machine.service.data.external.service.IDataExternalFieldDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class DataExternalFieldDataServiceImpl implements IDataExternalFieldDataService {

    @Autowired
    private IDataExternalFieldDataDao externalFieldDataDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataExternalFieldDataCreateInputDto inputDto) {
        DataExternalFieldDataEntity entity= JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataExternalFieldDataEntity.class);
        return externalFieldDataDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(DataExternalFieldDataDeleteInputDto inputDto) {
        DataExternalFieldDataEntity entity= JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataExternalFieldDataEntity.class);
        return externalFieldDataDao.delete(entity);
    }

    @Override
    public String getValue(DataExternalFieldDataGetValueInputDto inputDto) {
        return externalFieldDataDao.getValue(inputDto);
    }

}
