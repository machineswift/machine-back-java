package com.machine.app.openapi.data.franchisee.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.franchisee.business.IOpenApiFranchiseeBusiness;
import com.machine.app.openapi.data.franchisee.controller.vo.request.*;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeDetailResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeListSampleResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeHealthCertificateResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeIdentityCardResponseVo;
import com.machine.client.data.franchisee.IDataFranchiseeClient;
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
    private IDataFranchiseeClient franchiseeClient;

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
