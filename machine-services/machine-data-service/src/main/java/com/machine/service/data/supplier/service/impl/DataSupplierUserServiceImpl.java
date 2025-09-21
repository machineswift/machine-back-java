package com.machine.service.data.supplier.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierListUserIdInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.supplier.dao.IDataSupplierCompanyDao;
import com.machine.service.data.supplier.dao.IDataSupplierCompanyRelationDao;
import com.machine.service.data.supplier.dao.IDataSupplierUserDao;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyEntity;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierCompanyRelationEntity;
import com.machine.service.data.supplier.dao.mapper.entity.DataSupplierUserEntity;
import com.machine.service.data.supplier.service.IDataSupplierUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DataSupplierUserServiceImpl implements IDataSupplierUserService {

    @Autowired
    private IDataSupplierUserDao supplierUserDao;

    @Autowired
    private IDataSupplierCompanyDao supplierCompanyDao;

    @Autowired
    private IDataSupplierCompanyRelationDao supplierCompanyRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataSupplierCreateInputDto inputDto) {
        DataSupplierUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataSupplierUserEntity.class);
        String supplierId = supplierUserDao.insert(insertEntity);

        //添加供应商与公司关系
        if (CollectionUtil.isNotEmpty(inputDto.getCompanyIdSet())) {

            List<DataSupplierCompanyEntity> entityList = supplierCompanyDao.selectByIds(inputDto.getCompanyIdSet());
            if (CollectionUtil.isEmpty(entityList) || entityList.size() != inputDto.getCompanyIdSet().size()) {
                throw new DataBusinessException("data.supplierCompany.service.create.idNotExists", "供应商公司不存在");
            }

            for (String companyId : inputDto.getCompanyIdSet()) {
                DataSupplierCompanyRelationEntity insertRelationEntity = new DataSupplierCompanyRelationEntity();
                insertRelationEntity.setSupplierId(supplierId);
                insertRelationEntity.setCompanyId(companyId);
                supplierCompanyRelationDao.insert(insertRelationEntity);
            }
        }
        return supplierId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataSupplierUpdateInputDto inputDto) {
        DataSupplierUserEntity dbEntity = supplierUserDao.getByUserId(inputDto.getUserId());
        if (dbEntity == null) {
            return 0;
        }
        //删除供应商与公司关系
        supplierCompanyRelationDao.deleteBySupplierId(dbEntity.getId());

        //添加供应商与公司关系
        if (CollectionUtil.isNotEmpty(inputDto.getCompanyIdSet())) {

            List<DataSupplierCompanyEntity> entityList = supplierCompanyDao.selectByIds(inputDto.getCompanyIdSet());
            if (CollectionUtil.isEmpty(entityList) || entityList.size() != inputDto.getCompanyIdSet().size()) {
                throw new DataBusinessException("data.supplierCompany.service.create.idNotExists", "供应商公司不存在");
            }

            for (String companyId : inputDto.getCompanyIdSet()) {
                DataSupplierCompanyRelationEntity insertRelationEntity = new DataSupplierCompanyRelationEntity();
                insertRelationEntity.setSupplierId(dbEntity.getId());
                insertRelationEntity.setCompanyId(companyId);
                supplierCompanyRelationDao.insert(insertRelationEntity);
            }
        }
        return 1;
    }

    @Override
    public DataSupplierDetailOutputDto detail(IdRequest request) {
        DataSupplierUserEntity dbEntity = supplierUserDao.getById(request.getId());
        if (null == dbEntity) {
            return null;
        }

        DataSupplierDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataSupplierDetailOutputDto.class);
        List<DataSupplierCompanyRelationEntity> relationEntityList = supplierCompanyRelationDao.selectBySupplierId(dbEntity.getId());
        if (CollectionUtil.isNotEmpty(relationEntityList)) {
            outputDto.setCompanyIdList(relationEntityList.stream().map(DataSupplierCompanyRelationEntity::getCompanyId).toList());
        }
        return outputDto;
    }

    @Override
    public DataSupplierDetailOutputDto getByUserId(IdRequest request) {
        DataSupplierUserEntity dbEntity = supplierUserDao.getByUserId(request.getId());
        if (null == dbEntity) {
            return null;
        }

        DataSupplierDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataSupplierDetailOutputDto.class);
        List<DataSupplierCompanyRelationEntity> relationEntityList = supplierCompanyRelationDao.selectBySupplierId(dbEntity.getId());
        if (CollectionUtil.isNotEmpty(relationEntityList)) {
            outputDto.setCompanyIdList(relationEntityList.stream().map(DataSupplierCompanyRelationEntity::getCompanyId).toList());
        }

        return outputDto;
    }

    @Override
    public Set<String> listUserIdByCondition(DataSupplierListUserIdInputDto inputDto) {
        return supplierUserDao.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataSupplierListOutputDto> listByOffset(DataSupplierQueryListOffsetInputDto inputDto) {
        List<DataSupplierUserEntity> entityList = supplierUserDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataSupplierListOutputDto.class);
    }
}
