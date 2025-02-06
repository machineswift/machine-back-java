package com.machine.service.data.employee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.employee.dao.ICompanyEmployeeDao;
import com.machine.service.data.employee.dao.mapper.entity.CompanyEmployeeEntity;
import com.machine.service.data.employee.service.ICompanyEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CompanyEmployeeServiceImpl implements ICompanyEmployeeService {

    @Autowired
    private ICompanyEmployeeDao companyEmployeeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataCompanyEmployeeCreateInputDto inputDto) {
        CompanyEmployeeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), CompanyEmployeeEntity.class);
        return companyEmployeeDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataCompanyEmployeeUpdateInputDto inputDto) {
        CompanyEmployeeEntity dbEntity = companyEmployeeDao.getByUserId(inputDto.getUserId());
        if (null == dbEntity) {
            return 0;
        }

        if (inputDto.getEmployeeStatus() == dbEntity.getEmployeeStatus()) {
            return 0;
        }
        return companyEmployeeDao.updateStatus(dbEntity.getId(), inputDto.getEmployeeStatus());
    }

    @Override
    public CompanyEmployeeDetailOutputDto detail(IdRequest request) {
        CompanyEmployeeEntity dbEntity = companyEmployeeDao.getById(request.getId());
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), CompanyEmployeeDetailOutputDto.class);
    }

    @Override
    public CompanyEmployeeDetailOutputDto getByUserId(IdRequest request) {
        CompanyEmployeeEntity dbEntity = companyEmployeeDao.getByUserId(request.getId());
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), CompanyEmployeeDetailOutputDto.class);
    }

    @Override
    public List<CompanyEmployeeListOutputDto> listByOffset(DataCompanyEmployeeQueryListOffsetInputDto inputDto) {
        List<CompanyEmployeeEntity> entityList = companyEmployeeDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), CompanyEmployeeListOutputDto.class);
    }

    @Override
    public Map<String, CompanyEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<CompanyEmployeeEntity> userEntityList = companyEmployeeDao.selectByUserIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(CompanyEmployeeEntity::getUserId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), CompanyEmployeeDetailOutputDto.class)));
    }
}
