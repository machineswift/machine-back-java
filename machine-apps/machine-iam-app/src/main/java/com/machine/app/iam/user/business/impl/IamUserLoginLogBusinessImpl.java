package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.IIamUserLoginLogBusiness;
import com.machine.app.iam.user.controller.vo.request.IamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogExpandListResponseVo;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.output.IamUserLoginLogDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.IamUserLoginLogListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IamUserLoginLogBusinessImpl implements IIamUserLoginLogBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserLoginLogClient userLoginLogClient;

    @Override
    public IamUserLoginLogDetailResponseVo detail(IdRequest request) {
        IamUserLoginLogDetailOutputDto outputDto = userLoginLogClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        IamUserLoginLogDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamUserLoginLogDetailResponseVo.class);

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
    public PageResponse<IamUserLoginLogExpandListResponseVo> pageExpand(IamUserLoginLogQueryPageRequestVo request) {
        IamUserLoginLogQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserLoginLogQueryPageInputDto.class);
        PageResponse<IamUserLoginLogListOutputDto> page = userLoginLogClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        PageResponse<IamUserLoginLogExpandListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserLoginLogExpandListResponseVo.class));

        {//创建人、修改人姓名
            Set<String> userIdSet = page.getRecords().stream().map(IamUserLoginLogListOutputDto::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(page.getRecords().stream().map(IamUserLoginLogListOutputDto::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamUserLoginLogExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }
        return pageResponse;
    }
}
