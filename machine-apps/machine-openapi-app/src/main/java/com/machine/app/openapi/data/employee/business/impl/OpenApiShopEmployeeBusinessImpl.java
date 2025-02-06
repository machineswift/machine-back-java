package com.machine.app.openapi.data.employee.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.employee.business.IOpenApiShopEmployeeBusiness;
import com.machine.app.openapi.data.employee.controller.vo.request.*;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenApiShopEmployeeListSimpleResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeDetailResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeHealthCertificateResponseVo;
import com.machine.app.openapi.data.employee.controller.vo.response.OpenapiShopEmployeeIdentityCardResponseVo;
import com.machine.client.data.employee.IShopEmployeeClient;
import com.machine.client.data.employee.dto.input.*;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeHealthCertificateOutputDto;
import com.machine.client.data.employee.dto.output.OpenapiShopEmployeeIdentityCardOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.input.IamShopUserCreate4OpenapiInputDto;
import com.machine.client.iam.user.dto.input.IamShopUserUpdateRoleInputDto;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenApiShopEmployeeBusinessImpl implements IOpenApiShopEmployeeBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IShopEmployeeClient shopEmployeeClient;

    @Override
    public String create(OpenapiShopEmployeeCreateRequestVo request) {
        IamShopUserCreate4OpenapiInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserCreate4OpenapiInputDto.class);
        return userClient.createShopUser4Openapi(inputDto);
    }

    @Override
    public void updateStatus(OpenapiShopEmployeeUpdateStatusRequestVo request) {
        DataShopEmployeeUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopEmployeeUpdateStatusInputDto.class);
        shopEmployeeClient.updateStatus(inputDto);
    }

    @Override
    public void updatePhone(OpenapiShopEmployeeUpdatePhoneRequestVo request) {
        OpenapiShopEmployeeUpdatePhoneInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), OpenapiShopEmployeeUpdatePhoneInputDto.class);
        shopEmployeeClient.updatePhone(inputDto);
    }

    @Override
    public void updateRole(OpenApiShopEmployeeUpdateRoleRequestVo request) {
        ShopEmployeeDetailOutputDto outputDto = shopEmployeeClient.detail(new IdRequest(request.getId()));
        if (outputDto == null) {
            throw new IamBusinessException("openapi.data.shopEmployee.updateRole.shopEmployeeNotExists", "门店员工不存在");
        }

        IamShopUserUpdateRoleInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamShopUserUpdateRoleInputDto.class);
        inputDto.setId(outputDto.getUserId());
        userClient.updateShopUserRole(inputDto);
    }

    @Override
    public void updateIdentityCard(OpenApiShopEmployeeUpdateIdentityCardRequestVo request) {
        OpenApiShopEmployeeUpdateIdentityCardInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request),
                OpenApiShopEmployeeUpdateIdentityCardInputDto.class);
        shopEmployeeClient.updateIdentityCard(inputDto);
    }

    @Override
    public void updateHealthCertificate(OpenApiShopEmployeeUpdateHealthCertificateRequestVo request) {
        OpenApiShopEmployeeUpdateHealthCertificateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request),
                OpenApiShopEmployeeUpdateHealthCertificateInputDto.class);
        shopEmployeeClient.updateHealthCertificate(inputDto);
    }

    @Override
    public OpenapiShopEmployeeDetailResponseVo detail(OpenApiShopEmployeeIdRequestVo request) {
        ShopEmployeeDetailOutputDto outputDto = shopEmployeeClient.detail(new IdRequest(request.getId()));
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
        List<ShopEmployeeListOutputDto> outputDtoList = shopEmployeeClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiShopEmployeeListSimpleResponseVo.class);
    }
}
