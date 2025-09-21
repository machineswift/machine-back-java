package com.machine.service.data.employee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeListUserIdInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataCompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.employee.dao.IDataCompanyEmployeeDao;
import com.machine.service.data.employee.dao.mapper.entity.DataCompanyEmployeeEntity;
import com.machine.service.data.employee.service.IDataCompanyEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DataCompanyEmployeeServiceImpl implements IDataCompanyEmployeeService {

    @Autowired
    private IDataCompanyEmployeeDao companyEmployeeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataCompanyEmployeeCreateInputDto inputDto) {
        DataCompanyEmployeeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataCompanyEmployeeEntity.class);
        return companyEmployeeDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataCompanyEmployeeUpdateInputDto inputDto) {
        DataCompanyEmployeeEntity dbEntity = companyEmployeeDao.getByUserId(inputDto.getUserId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getEmployeeStatus() == dbEntity.getEmployeeStatus()) {
            return 0;
        }
        return companyEmployeeDao.updateStatus(dbEntity.getId(), inputDto.getEmployeeStatus());
    }

    @Override
    public DataCompanyEmployeeDetailOutputDto detail(IdRequest request) {
        DataCompanyEmployeeEntity dbEntity = companyEmployeeDao.getById(request.getId());
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataCompanyEmployeeDetailOutputDto.class);
    }

    @Override
    public DataCompanyEmployeeDetailOutputDto getByUserId(IdRequest request) {
        DataCompanyEmployeeEntity dbEntity = companyEmployeeDao.getByUserId(request.getId());
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataCompanyEmployeeDetailOutputDto.class);
    }

    @Override
    public Set<String> listUserIdByCondition(DataCompanyEmployeeListUserIdInputDto inputDto) {
        return companyEmployeeDao.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataCompanyEmployeeListOutputDto> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto) {
        List<DataCompanyEmployeeEntity> entityList = companyEmployeeDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataCompanyEmployeeListOutputDto.class);
    }

    @Override
    public Map<String, DataCompanyEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<DataCompanyEmployeeEntity> userEntityList = companyEmployeeDao.selectByUserIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(DataCompanyEmployeeEntity::getUserId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataCompanyEmployeeDetailOutputDto.class)));
    }
}
