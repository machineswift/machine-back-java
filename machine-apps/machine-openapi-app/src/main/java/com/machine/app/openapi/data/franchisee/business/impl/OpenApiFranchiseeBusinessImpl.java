package com.machine.app.openapi.data.franchisee.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.franchisee.business.IOpenApiFranchiseeBusiness;
import com.machine.app.openapi.data.franchisee.controller.vo.request.*;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeDetailResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeListSampleResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeHealthCertificateResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeIdentityCardResponseVo;
import com.machine.client.data.franchisee.IFranchiseeClient;
import com.machine.client.data.franchisee.dto.input.*;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeListOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OpenApiFranchiseeBusinessImpl implements IOpenApiFranchiseeBusiness {

    @Autowired
    private IFranchiseeClient franchiseeClient;

    @Override
    public String create(OpenApiFranchiseeCreateRequestVo request) {
        DataFranchiseeCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataFranchiseeCreateInputDto.class);
        return franchiseeClient.create(inputDto);
    }

    @Override
    public void bindShop(OpenApiFranchiseeBindShopRequestVo request) {
        DataFranchiseeBindShopInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataFranchiseeBindShopInputDto.class);
        franchiseeClient.bindShop(inputDto);
    }

    @Override
    public void unbindShop(OpenApiFranchiseeUnbindShopRequestVo request) {
        DataFranchiseeUnbindShopInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataFranchiseeUnbindShopInputDto.class);
        franchiseeClient.unbindShop(inputDto);
    }

    @Override
    public void updatePhone(OpenApiFranchiseeUpdatePhoneRequestVo request) {
        OpenApiFranchiseeUpdatePhoneInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), OpenApiFranchiseeUpdatePhoneInputDto.class);
        franchiseeClient.updatePhone(inputDto);
    }

    @Override
    public void update(OpenApiFranchiseeUpdateRequestVo request) {
        DataFranchiseeUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataFranchiseeUpdateInputDto.class);
        franchiseeClient.update(inputDto);
    }

    @Override
    public void updateIdentityCard(OpenApiFranchiseeUpdateIdentityCardRequestVo request) {
        OpenApiFranchiseeUpdateIdentityCardInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request),
                OpenApiFranchiseeUpdateIdentityCardInputDto.class);
        franchiseeClient.updateIdentityCard(inputDto);
    }

    @Override
    public void updateHealthCertificate(OpenApiFranchiseeUpdateHealthCertificateRequestVo request) {
        OpenApiFranchiseeUpdateHealthCertificateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request),
                OpenApiFranchiseeUpdateHealthCertificateInputDto.class);
        franchiseeClient.updateHealthCertificate(inputDto);
    }

    @Override
    public OpenApiFranchiseeDetailResponseVo detail(OpenApiFranchiseeIdRequestVo request) {
        DataFranchiseeDetailOutputDto outputDto = franchiseeClient.detail(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiFranchiseeDetailResponseVo.class);
    }

    @Override
    public OpenapiFranchiseeIdentityCardResponseVo identityCard(OpenApiFranchiseeIdRequestVo request) {
        OpenapiFranchiseeIdentityCardOutputDto outputDto = franchiseeClient.identityCard(new IdRequest(request.getId()));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenapiFranchiseeIdentityCardResponseVo.class);
    }

    @Override
    public OpenapiFranchiseeHealthCertificateResponseVo healthCertificate(OpenApiFranchiseeIdRequestVo request) {
        OpenapiFranchiseeHealthCertificateOutputDto outputDto = franchiseeClient.healthCertificate(new IdRequest(request.getId()));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenapiFranchiseeHealthCertificateResponseVo.class);
    }

    @Override
    public List<OpenApiFranchiseeListSampleResponseVo> listSample(OpenApiFranchiseeListSampleRequestVo request) {
        DataFranchiseeQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataFranchiseeQueryListOffsetInputDto.class);
        List<DataFranchiseeListOutputDto> outputDtoList = franchiseeClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiFranchiseeListSampleResponseVo.class);
    }
}
