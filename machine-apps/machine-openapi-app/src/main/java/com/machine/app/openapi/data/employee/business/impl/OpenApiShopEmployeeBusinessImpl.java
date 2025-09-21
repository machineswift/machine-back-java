package com.machine.app.openapi.data.employee.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.employee.business.IOpenApiShopEmployeeBusiness;
import com.machine.app.openapi.data.employee.controller.vo.request.*;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiShopEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeDetailResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeHealthCertificateResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeIdentityCardResponseVo;
import com.machine.client.data.employee.IDataShopEmployeeClient;
import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.DataShopEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenApiShopEmployeeBusinessImpl implements IOpenApiShopEmployeeBusiness {

    @Autowired
    private IDataShopEmployeeClient shopEmployeeClient;

    @Override
    public OpenapiShopEmployeeDetailResponseVo detail(OpenApiShopEmployeeIdRequestVo request) {
        DataShopEmployeeDetailOutputDto outputDto = shopEmployeeClient.detail(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenapiShopEmployeeDetailResponseVo.class);
    }

    @Override
    public OpenapiShopEmployeeIdentityCardResponseVo identityCard(OpenApiShopEmployeeIdRequestVo request) {
        OpenapiShopEmployeeIdentityCardOutputDto outputDto = shopEmployeeClient.identityCard(new IdRequest(request.getId()));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenapiShopEmployeeIdentityCardResponseVo.class);
    }

    @Override
    public OpenapiShopEmployeeHealthCertificateResponseVo healthCertificate(OpenApiShopEmployeeIdRequestVo request) {
        OpenapiShopEmployeeHealthCertificateOutputDto outputDto = shopEmployeeClient.healthCertificate(new IdRequest(request.getId()));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenapiShopEmployeeHealthCertificateResponseVo.class);
    }

    @Override
    public List<OpenApiShopEmployeeListSimpleResponseVo> listSimple(OpenApiShopEmployeeListSimpleRequestVo request) {
        DataShopEmployeeQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopEmployeeQueryListOffsetInputDto.class);
        List<DataShopEmployeeListOutputDto> outputDtoList = shopEmployeeClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiShopEmployeeListSimpleResponseVo.class);
    }
}
