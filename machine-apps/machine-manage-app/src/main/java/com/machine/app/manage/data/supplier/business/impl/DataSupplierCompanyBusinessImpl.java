package com.machine.app.manage.data.supplier.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.supplier.business.IDataSupplierCompanyBusiness;
import com.machine.app.manage.data.supplier.controller.vo.request.*;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanyDetailResponseVo;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanyExpandListResponseVo;
import com.machine.app.manage.data.supplier.controller.vo.response.DataSupplierCompanySimpleListResponseVo;
import com.machine.client.data.supplier.IDataSupplierCompanyClient;
import com.machine.client.data.supplier.dto.DataSupplierCompanyContractInformationDto;
import com.machine.client.data.supplier.dto.DataSupplierCompanyFinancialInformationDto;
import com.machine.client.data.supplier.dto.input.*;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyListOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataSupplierCompanyBusinessImpl implements IDataSupplierCompanyBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataSupplierCompanyClient supplierCompanyClient;

    @Override
    public String create(DataSupplierCompanyCreateRequestVo request) {
        DataSupplierCompanyCreateInputDto inputDto = new DataSupplierCompanyCreateInputDto();
        inputDto.setName(request.getName());
        inputDto.setBusinessCategory(request.getBusinessCategory());
        inputDto.setContactName(request.getContactName());
        inputDto.setContactPhone(request.getContactPhone());
        if (null != request.getCorrespondenceAddress()) {
            inputDto.setCorrespondenceAddress(JSONUtil.toJsonStr(request.getCorrespondenceAddress()));
        }
        if (null != request.getFinancialInformation()) {
            inputDto.setFinancialInformation(JSONUtil.toJsonStr(request.getFinancialInformation()));
        }
        if (null != request.getContractInformation()) {
            inputDto.setContractInformation(JSONUtil.toJsonStr(request.getContractInformation()));
        }
        return supplierCompanyClient.create(inputDto);
    }

    @Override
    public void updateStatus(DataSupplierCompanyUpdateStatusRequestVo request) {
        DataSupplierCompanyUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSupplierCompanyUpdateStatusInputDto.class);
        supplierCompanyClient.updateStatus(inputDto);
    }

    @Override
    public void update(DataSupplierCompanyUpdateRequestVo request) {
        DataSupplierCompanyUpdateInputDto inputDto = new DataSupplierCompanyUpdateInputDto();
        inputDto.setId(request.getId());
        inputDto.setName(request.getName());
        inputDto.setBusinessCategory(request.getBusinessCategory());
        inputDto.setContactName(request.getContactName());
        inputDto.setContactPhone(request.getContactPhone());
        if (null != request.getCorrespondenceAddress()) {
            inputDto.setCorrespondenceAddress(JSONUtil.toJsonStr(request.getCorrespondenceAddress()));
        }
        if (null != request.getFinancialInformation()) {
            inputDto.setFinancialInformation(JSONUtil.toJsonStr(request.getFinancialInformation()));
        }
        if (null != request.getContractInformation()) {
            inputDto.setContractInformation(JSONUtil.toJsonStr(request.getContractInformation()));
        }

        supplierCompanyClient.update(inputDto);
    }

    @Override
    public DataSupplierCompanyDetailResponseVo detail(IdRequest request) {
        DataSupplierCompanyDetailOutputDto outputDto = supplierCompanyClient.detail(request);
        if (outputDto == null) {
            return null;
        }
        DataSupplierCompanyDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataSupplierCompanyDetailResponseVo.class, true);
        if (StrUtil.isNotEmpty(outputDto.getCorrespondenceAddress())) {
            responseVo.setCorrespondenceAddress(
                    JSONUtil.toBean(JSONUtil.toJsonStr(outputDto.getCorrespondenceAddress()), AddressInfoDto.class));
        }
        if (StrUtil.isNotEmpty(outputDto.getContractInformation())) {
            responseVo.setContractInformation(JSONUtil.toBean(
                    JSONUtil.toJsonStr(outputDto.getContractInformation()), DataSupplierCompanyContractInformationDto.class));
        }
        if (StrUtil.isNotEmpty(outputDto.getFinancialInformation())) {
            responseVo.setFinancialInformation(JSONUtil.toBean(
                    JSONUtil.toJsonStr(outputDto.getFinancialInformation()), DataSupplierCompanyFinancialInformationDto.class));
        }

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(responseVo.getCreateBy());
        userIdSet.add(responseVo.getUpdateBy());
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());

        return responseVo;
    }

    @Override
    public PageResponse<DataSupplierCompanySimpleListResponseVo> pageSimple(DataSupplierCompanyQueryPageSimpleRequestVo request) {
        DataSupplierCompanySimplePageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSupplierCompanySimplePageInputDto.class);
        PageResponse<DataSupplierCompanySimpleListOutputDto> page = supplierCompanyClient.pageSample(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataSupplierCompanySimpleListResponseVo.class));
    }

    @Override
    public PageResponse<DataSupplierCompanyExpandListResponseVo> pageExpand(DataSupplierCompanyQueryPageExpandRequestVo request) {
        DataSupplierCompanyPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSupplierCompanyPageInputDto.class);
        PageResponse<DataSupplierCompanyListOutputDto> page = supplierCompanyClient.pageExpand(inputDto);

        PageResponse<DataSupplierCompanyExpandListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal());

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResponse;
        }

        //序列化
        List<DataSupplierCompanyExpandListResponseVo> responseVoList = new ArrayList<>();
        for (DataSupplierCompanyListOutputDto outputDto : page.getRecords()) {
            DataSupplierCompanyExpandListResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataSupplierCompanyExpandListResponseVo.class, true);
            if (StrUtil.isNotEmpty(outputDto.getCorrespondenceAddress())) {
                responseVo.setCorrespondenceAddress(
                        JSONUtil.toBean(JSONUtil.toJsonStr(outputDto.getCorrespondenceAddress()), AddressInfoDto.class));
            }
            responseVoList.add(responseVo);
        }
        pageResponse.setRecords(responseVoList);

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(DataSupplierCompanyExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(DataSupplierCompanyExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataSupplierCompanyExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        return pageResponse;
    }
}
