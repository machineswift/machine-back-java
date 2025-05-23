package com.machine.service.data.supplier.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4CodeClient;
import com.machine.client.data.supplier.dto.input.*;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyListOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.supplier.dao.ISupplierCompanyDao;
import com.machine.service.data.supplier.dao.mapper.entity.SupplierCompanyEntity;
import com.machine.service.data.supplier.dao.po.DataSupplierCompanySimpleListPo;
import com.machine.service.data.supplier.service.ISupplierCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierCompanyServiceImpl implements ISupplierCompanyService {

    @Autowired
    private ISupplierCompanyDao supplierCompanyDao;

    @Autowired
    private IDataLeaf4CodeClient leaf4CodeClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataSupplierCompanyCreateInputDto inputDto) {
        SupplierCompanyEntity entityByName = supplierCompanyDao.getByName(inputDto.getName());
        if (null != entityByName) {
            log.error("新增供应商公司名称已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("data.supplier.create.nameAlreadyExists", "名称已经存在");
        }

        SupplierCompanyEntity entityByContactPhone = supplierCompanyDao.getByByContactPhone(inputDto.getContactPhone());
        if (null != entityByContactPhone) {
            log.error("新增供应商公司联系人手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("data.supplier.create.contactPhoneAlreadyExists", "系人手机号已经存在");
        }

        //创建用户
        SupplierCompanyEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), SupplierCompanyEntity.class);
        //id(用户同步数据)
        if (StrUtil.isNotBlank(inputDto.getId())) {
            insertEntity.setId(inputDto.getId());
        }
        insertEntity.setStatus(StatusEnum.ENABLE);
        //编码
        insertEntity.setCode(leaf4CodeClient.dataSupplierCompanyGysgs());
        return supplierCompanyDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(DataSupplierCompanyUpdateStatusInputDto inputDto) {
        return supplierCompanyDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataSupplierCompanyUpdateInputDto inputDto) {
        SupplierCompanyEntity dbEntity = supplierCompanyDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        //验证名称是否存在
        SupplierCompanyEntity nameEntity = supplierCompanyDao.getByName(inputDto.getName());
        if (null != nameEntity && !nameEntity.getId().equals(inputDto.getId())) {
            log.error(" 修改供应商公司名称已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("data.supplier.update.nameAlreadyExists", "名称已经存在");
        }

        //验证手机号是否存在
        SupplierCompanyEntity entityByContactPhone = supplierCompanyDao.getByByContactPhone(inputDto.getContactPhone());
        if (null != entityByContactPhone && !entityByContactPhone.getId().equals(inputDto.getId())) {
            log.error("修改供应商公司联系人手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("data.supplier.update.contactPhoneAlreadyExists", "系人手机号已经存在");
        }

        return supplierCompanyDao.update(JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), SupplierCompanyEntity.class));
    }

    @Override
    public DataSupplierCompanyDetailOutputDto detail(IdRequest request) {
        SupplierCompanyEntity entity = supplierCompanyDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataSupplierCompanyDetailOutputDto.class);
    }

    @Override
    public List<DataSupplierCompanySimpleListOutputDto> listByIdSet(IdSetRequest request) {
        List<SupplierCompanyEntity> entityList = supplierCompanyDao.selectByIds(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataSupplierCompanySimpleListOutputDto.class);
    }

    @Override
    public List<DataSupplierCompanySimpleListOutputDto> listBySupplierId(IdRequest request) {
        List<SupplierCompanyEntity> entityList = supplierCompanyDao.listBySupplierId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataSupplierCompanySimpleListOutputDto.class);
    }

    @Override
    public List<DataSupplierCompanySimpleListOutputDto> listByOffset(DataSupplierCompanyQueryListOffsetInputDto inputDto) {
        List<SupplierCompanyEntity> entityList = supplierCompanyDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataSupplierCompanySimpleListOutputDto.class);
    }

    @Override
    public Map<String, DataSupplierCompanySimpleListOutputDto> mapByIdSet(IdSetRequest request) {
        List<SupplierCompanyEntity> entityList = supplierCompanyDao.selectByIds(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }
        return entityList.stream()
                .collect(Collectors.toMap(SupplierCompanyEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataSupplierCompanySimpleListOutputDto.class)));
    }

    @Override
    public Page<DataSupplierCompanySimpleListOutputDto> pageSample(DataSupplierCompanySimplePageInputDto inputDto) {
        Page<DataSupplierCompanySimpleListPo> page = supplierCompanyDao.pageSample(inputDto);
        Page<DataSupplierCompanySimpleListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataSupplierCompanySimpleListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<DataSupplierCompanyListOutputDto> pageExpand(DataSupplierCompanyPageInputDto inputDto) {
        Page<SupplierCompanyEntity> page = supplierCompanyDao.pageExpand(inputDto);
        Page<DataSupplierCompanyListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataSupplierCompanyListOutputDto.class));
        return pageResult;
    }
}
