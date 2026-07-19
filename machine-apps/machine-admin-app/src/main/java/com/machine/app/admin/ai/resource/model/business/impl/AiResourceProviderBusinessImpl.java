package com.machine.app.admin.ai.resource.model.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.ai.resource.model.business.IAiResourceProviderBusiness;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderCreateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderListRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderUpdateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceProviderUpdateStatusRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceProviderDetailResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceProviderExpandListResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceProviderSimpleListResponseVo;
import com.machine.client.ai.resource.model.IAiResourceProviderClient;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderListInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceProviderUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceProviderListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AiResourceProviderBusinessImpl implements IAiResourceProviderBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IAiResourceProviderClient aiResourceProviderClient;

    @Override
    public String create(AiResourceProviderCreateRequestVo request) {
        AiResourceProviderCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceProviderCreateInputDto.class);
        return aiResourceProviderClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        aiResourceProviderClient.delete(request);
    }

    @Override
    public void update(AiResourceProviderUpdateRequestVo request) {
        AiResourceProviderUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceProviderUpdateInputDto.class);
        aiResourceProviderClient.update(inputDto);
    }

    @Override
    public void updateStatus(AiResourceProviderUpdateStatusRequestVo request) {
        AiResourceProviderUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceProviderUpdateStatusInputDto.class);
        aiResourceProviderClient.updateStatus(inputDto);
    }

    @Override
    public AiResourceProviderDetailResponseVo detail(IdRequest request) {
        AiResourceProviderDetailOutputDto outputDto = aiResourceProviderClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        AiResourceProviderDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), AiResourceProviderDetailResponseVo.class);

        { // 填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public List<AiResourceProviderSimpleListResponseVo> listSimple(AiResourceProviderListRequestVo request) {
        AiResourceProviderListInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceProviderListInputDto.class);
        List<AiResourceProviderListOutputDto> outputList = aiResourceProviderClient.list(inputDto);
        return JSONUtil.toList(JSONUtil.toJsonStr(outputList), AiResourceProviderSimpleListResponseVo.class);
    }

    @Override
    public List<AiResourceProviderExpandListResponseVo> listExpanded(AiResourceProviderListRequestVo request) {
        AiResourceProviderListInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceProviderListInputDto.class);
        List<AiResourceProviderListOutputDto> outputList = aiResourceProviderClient.list(inputDto);

        if (CollectionUtil.isEmpty(outputList)) {
            return new ArrayList<>();
        }

        List<AiResourceProviderExpandListResponseVo> responseList = JSONUtil.toList(JSONUtil.toJsonStr(outputList), AiResourceProviderExpandListResponseVo.class);

        { //填充修改人创建人信息
            Set<String> userIdSet = responseList.stream().map(AiResourceProviderExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(responseList.stream().map(AiResourceProviderExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (AiResourceProviderExpandListResponseVo vo : responseList) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return responseList;
    }
}
