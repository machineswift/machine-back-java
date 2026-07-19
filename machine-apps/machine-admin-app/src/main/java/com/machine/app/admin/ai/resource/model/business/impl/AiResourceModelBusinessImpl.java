package com.machine.app.admin.ai.resource.model.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.ai.resource.model.business.IAiResourceModelBusiness;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelCreateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelQueryPageRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelUpdateRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.request.AiResourceModelUpdateStatusRequestVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelDetailResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelExpandListResponseVo;
import com.machine.app.admin.ai.resource.model.controller.vo.response.AiResourceModelSimpleListResponseVo;
import com.machine.client.ai.resource.model.IAiResourceModelClient;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelCreateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelQueryPageInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateInputDto;
import com.machine.client.ai.resource.model.dto.input.AiResourceModelUpdateStatusInputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelDetailOutputDto;
import com.machine.client.ai.resource.model.dto.output.AiResourceModelListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AiResourceModelBusinessImpl implements IAiResourceModelBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IAiResourceModelClient aiResourceModelClient;

    @Override
    public String create(AiResourceModelCreateRequestVo request) {
        request.setCode(request.getCode().trim());

        AiResourceModelCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceModelCreateInputDto.class);
        return aiResourceModelClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        aiResourceModelClient.delete(request);
    }

    @Override
    public void update(AiResourceModelUpdateRequestVo request) {
        AiResourceModelUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceModelUpdateInputDto.class);
        aiResourceModelClient.update(inputDto);
    }

    @Override
    public void updateStatus(AiResourceModelUpdateStatusRequestVo request) {
        AiResourceModelUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceModelUpdateStatusInputDto.class);
        aiResourceModelClient.updateStatus(inputDto);
    }

    @Override
    public AiResourceModelDetailResponseVo detail(IdRequest request) {
        AiResourceModelDetailOutputDto outputDto = aiResourceModelClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        AiResourceModelDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), AiResourceModelDetailResponseVo.class);

        { //填充修改人创建人信息
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
    public PageResponse<AiResourceModelSimpleListResponseVo> pageSimple(AiResourceModelQueryPageRequestVo request) {
        AiResourceModelQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceModelQueryPageInputDto.class);
        PageResponse<AiResourceModelListOutputDto> page = aiResourceModelClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), AiResourceModelSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<AiResourceModelExpandListResponseVo> pageExpand(AiResourceModelQueryPageRequestVo request) {
        //查询分页数据
        AiResourceModelQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), AiResourceModelQueryPageInputDto.class);
        PageResponse<AiResourceModelListOutputDto> pageOutput = aiResourceModelClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<AiResourceModelExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), AiResourceModelExpandListResponseVo.class));

        { //创建人、修改人姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(AiResourceModelExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(AiResourceModelExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (AiResourceModelExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return pageResponse;
    }
}
