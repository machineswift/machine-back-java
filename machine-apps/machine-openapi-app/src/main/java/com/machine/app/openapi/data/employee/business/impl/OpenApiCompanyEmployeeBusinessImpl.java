package com.machine.app.openapi.data.employee.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.employee.business.IOpenApiCompanyEmployeeBusiness;
import com.machine.app.openapi.data.employee.controller.vo.request.OpenApiCompanyEmployeeIdRequestVo;
import com.machine.app.openapi.data.employee.controller.vo.request.OpenApiCompanyEmployeeListSimpleRequestVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiCompanyEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiCompanyEmployeeDetailResponseVo;
import com.machine.client.data.employee.ICompanyEmployeeClient;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeQueryListOffsetInputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.CompanyEmployeeListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenApiCompanyEmployeeBusinessImpl implements IOpenApiCompanyEmployeeBusiness {

    @Autowired
    private ICompanyEmployeeClient companyEmployeeClient;

    @Override
    public OpenapiCompanyEmployeeDetailResponseVo detail(OpenApiCompanyEmployeeIdRequestVo request) {
        CompanyEmployeeDetailOutputDto outputDto = companyEmployeeClient.detail(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenapiCompanyEmployeeDetailResponseVo.class);
    }

    @Override
    public List<OpenApiCompanyEmployeeListSimpleResponseVo> listSimple(OpenApiCompanyEmployeeListSimpleRequestVo request) {
        DataCompanyEmployeeQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataCompanyEmployeeQueryListOffsetInputDto.class);
        List<CompanyEmployeeListOutputDto> outputDtoList = companyEmployeeClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)){
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiCompanyEmployeeListSimpleResponseVo.class);
    }
}
