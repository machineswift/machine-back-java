package com.machine.app.manage.data.tag.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.machine.app.manage.data.tag.business.IDataTagOptionBusiness;
import com.machine.app.manage.data.tag.controller.vo.request.*;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagOptionDetailResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagOptionExpandListResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagOptionSimpleListResponseVo;
import com.machine.client.data.tag.IDataTagOptionClient;
import com.machine.client.data.tag.dto.input.*;
import com.machine.client.data.tag.dto.output.DataTagOptionDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagOptionListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataTagOptionBusinessImpl implements IDataTagOptionBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataTagOptionClient tagOptionClient;

    @Override
    public String create(DataTagOptionCreateRequestVo request) {
        DataTagOptionCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionCreateInputDto.class);
        return tagOptionClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        tagOptionClient.delete(request);
    }

    @Override
    public void update(DataTagOptionUpdateRequestVo request) {
        DataTagOptionUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionUpdateInputDto.class);
        tagOptionClient.update(inputDto);
    }

    @Override
    public void updateCode(DataTagOptionUpdateCodeRequestVo request) {
        DataTagOptionUpdateCodeInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionUpdateCodeInputDto.class);
        tagOptionClient.updateCode(inputDto);
    }

    @Override
    public void updateStatus(DataTagOptionUpdateStatusRequestVo request) {
        DataTagOptionUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionUpdateStatusInputDto.class);
        tagOptionClient.updateStatus(inputDto);
    }

    @Override
    public void updateSort(DataTagOptionUpdateSortRequestVo request) {
        DataTagOptionUpdateSortInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionUpdateSortInputDto.class);
        tagOptionClient.updateSort(inputDto);
    }

    @Override
    public DataTagOptionDetailResponseVo detail(IdRequest request) {
        DataTagOptionDetailOutputDto outputDto = tagOptionClient.detail(request);
        DataTagOptionDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataTagOptionDetailResponseVo.class);

        {//填充修改人创建人信息
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
    public List<DataTagOptionSimpleListResponseVo> listSimple(DataTagOptionQueryListRequestVo request) {
        DataTagOptionQueryListInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionQueryListInputDto.class);
        List<DataTagOptionListOutputDto> outputDtoList = tagOptionClient.selectList(inputDto);

        if (CollectionUtils.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), DataTagOptionSimpleListResponseVo.class);
    }

    @Override
    public List<DataTagOptionExpandListResponseVo> listExpand(DataTagOptionQueryListRequestVo request) {
        DataTagOptionQueryListInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagOptionQueryListInputDto.class);
        List<DataTagOptionListOutputDto> outputDtoList = tagOptionClient.selectList(inputDto);

        List<DataTagOptionExpandListResponseVo> responseVoList = JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), DataTagOptionExpandListResponseVo.class);
        if (CollectionUtil.isEmpty(responseVoList)) {
            return List.of();
        }

        {// 填充创建人和修改人信息
            Set<String> userIdSet = responseVoList.stream()
                    .map(DataTagOptionExpandListResponseVo::getCreateBy)
                    .collect(Collectors.toSet());
            userIdSet.addAll(responseVoList.stream()
                    .map(DataTagOptionExpandListResponseVo::getUpdateBy)
                    .collect(Collectors.toSet()));

            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (DataTagOptionExpandListResponseVo responseVo : responseVoList) {
                responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
                responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
            }
        }

        return responseVoList;
    }


}
