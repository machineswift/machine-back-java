package com.machine.app.openapi.data.supplier.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.supplier.business.IOpenApiSupplierCompanyBusiness;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierCompanyIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierCompanyListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierCompanyDetailResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierCompanyListSimpleResponseVo;
import com.machine.client.data.supplier.IDataSupplierCompanyClient;
import com.machine.client.data.supplier.dto.DataSupplierCompanyContractInformationDto;
import com.machine.client.data.supplier.dto.DataSupplierCompanyFinancialInformationDto;
import com.machine.client.data.supplier.dto.input.DataSupplierCompanyQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanyDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenApiSupplierCompanyBusinessImpl implements IOpenApiSupplierCompanyBusiness {

    @Autowired
    private IDataSupplierCompanyClient supplierCompanyClient;

    @Override
    public OpenApiSupplierCompanyDetailResponseVo detail(OpenApiSupplierCompanyIdRequestVo request) {
        DataSupplierCompanyDetailOutputDto outputDto = supplierCompanyClient.detail(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }

        OpenApiSupplierCompanyDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiSupplierCompanyDetailResponseVo.class, true);
        if (StrUtil.isNotEmpty(outputDto.getCorrespondenceAddress())) {
            responseVo.setCorrespondenceAddress(
                    JSONUtil.toBean(JSONUtil.toJsonStr(outputDto.getCorrespondenceAddress()), AddressInfoDto.class));
        }
        if (StrUtil.isNotEmpty(outputDto.getFinancialInformation())) {
            responseVo.setFinancialInformation(JSONUtil.toBean(
                    JSONUtil.toJsonStr(outputDto.getFinancialInformation()), DataSupplierCompanyContractInformationDto.class));
        }
        if (StrUtil.isNotEmpty(outputDto.getContractInformation())) {
            responseVo.setContractInformation(JSONUtil.toBean(
                    JSONUtil.toJsonStr(outputDto.getContractInformation()), DataSupplierCompanyFinancialInformationDto.class));
        }

        return responseVo;
    }

    @Override
    public List<OpenApiSupplierCompanyListSimpleResponseVo> listSimple(OpenApiSupplierCompanyListSimpleRequestVo request) {
        DataSupplierCompanyQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSupplierCompanyQueryListOffsetInputDto.class);
        List<DataSupplierCompanySimpleListOutputDto> outputDtoList = supplierCompanyClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiSupplierCompanyListSimpleResponseVo.class);
    }
}
