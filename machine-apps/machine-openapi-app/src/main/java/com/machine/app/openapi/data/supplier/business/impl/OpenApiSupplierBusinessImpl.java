package com.machine.app.openapi.data.supplier.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierIdRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.request.OpenApiSupplierListSimpleRequestVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierDetailResponseVo;
import com.machine.app.openapi.data.supplier.controller.vo.response.OpenApiSupplierListSimpleResponseVo;
import com.machine.app.openapi.data.supplier.business.IOpenApiSupplierBusiness;
import com.machine.app.openapi.iam.user.controller.vo.request.OpenApiUserIdRequestVo;
import com.machine.client.data.supplier.IDataSupplierUserClient;
import com.machine.client.data.supplier.dto.input.DataSupplierQueryListOffsetInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierDetailOutputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenApiSupplierBusinessImpl implements IOpenApiSupplierBusiness {

    @Autowired
    private IDataSupplierUserClient supplierUserClient;

    @Override
    public OpenApiSupplierDetailResponseVo detail(OpenApiSupplierIdRequestVo request) {
        DataSupplierDetailOutputDto outputDto = supplierUserClient.detail(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiSupplierDetailResponseVo.class);
    }

    @Override
    public OpenApiSupplierDetailResponseVo detailByUserId(OpenApiUserIdRequestVo request) {
        DataSupplierDetailOutputDto outputDto = supplierUserClient.getByUserId(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiSupplierDetailResponseVo.class);
    }

    @Override
    public List<OpenApiSupplierListSimpleResponseVo> listSimple(OpenApiSupplierListSimpleRequestVo request) {
        DataSupplierQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataSupplierQueryListOffsetInputDto.class);
        List<DataSupplierListOutputDto> outputDtoList = supplierUserClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiSupplierListSimpleResponseVo.class);
    }
}
