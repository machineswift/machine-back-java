package com.machine.service.data.external.service.impl;


import cn.hutool.json.JSONUtil;
import com.machine.client.data.external.dto.input.ExternalFieldDataCreateInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataDeleteInputDto;
import com.machine.client.data.external.dto.input.ExternalFieldDataGetValueInputDto;
import com.machine.service.data.external.dao.IExternalFieldDataDao;
import com.machine.service.data.external.dao.mapper.entity.ExternalFieldDataEntity;
import com.machine.service.data.external.service.IExternalFieldDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ExternalFieldDataServiceImpl implements IExternalFieldDataService {

    @Autowired
    private IExternalFieldDataDao externalFieldDataDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(ExternalFieldDataCreateInputDto inputDto) {
        ExternalFieldDataEntity entity= JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ExternalFieldDataEntity.class);
        return externalFieldDataDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(ExternalFieldDataDeleteInputDto inputDto) {
        ExternalFieldDataEntity entity= JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), ExternalFieldDataEntity.class);
        return externalFieldDataDao.delete(entity);
    }

    @Override
    public String getValue(ExternalFieldDataGetValueInputDto inputDto) {
        return externalFieldDataDao.getValue(inputDto);
    }

}
