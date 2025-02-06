package com.machine.app.iam.user.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.business.IIamUserLoginLogBusiness;
import com.machine.app.iam.user.controller.vo.request.IamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.user.controller.vo.response.IamUserLoginLogPageResponseVo;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserLoginLogClient;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.input.IamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.user.dto.output.UserLoginLogPageOutputDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public PageResponse<IamUserLoginLogPageResponseVo> page(IamUserLoginLogQueryPageRequestVo request) {
        IamUserLoginLogQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamUserLoginLogQueryPageInputDto.class);
        PageResponse<UserLoginLogPageOutputDto> page = userLoginLogClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        //根据用户id获取用户信息
        Set<String> userIdSet = page.getRecords().stream().map(UserLoginLogPageOutputDto::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(page.getRecords().stream().map(UserLoginLogPageOutputDto::getUpdateBy).collect(Collectors.toSet()));
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        PageResponse<IamUserLoginLogPageResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserLoginLogPageResponseVo.class));

        for (IamUserLoginLogPageResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }
        return pageResponse;
    }
}
