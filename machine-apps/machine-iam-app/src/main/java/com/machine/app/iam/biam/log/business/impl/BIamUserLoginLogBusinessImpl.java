package com.machine.app.iam.biam.log.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.log.business.IBIamUserLoginLogBusiness;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserLoginLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserLoginLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserLoginLogExpandListResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.log.IBIamUserLoginLogClient;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserLoginLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserLoginLogListOutputDto;
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
public class BIamUserLoginLogBusinessImpl implements IBIamUserLoginLogBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserLoginLogClient userLoginLogClient;

    @Override
    public BIamUserLoginLogDetailResponseVo detail(IdRequest request) {
        BIamUserLoginLogDetailOutputDto outputDto = userLoginLogClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        BIamUserLoginLogDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamUserLoginLogDetailResponseVo.class);

        { //填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public PageResponse<BIamUserLoginLogExpandListResponseVo> pageExpand(BIamUserLoginLogQueryPageRequestVo request) {
        BIamUserLoginLogQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserLoginLogQueryPageInputDto.class);
        PageResponse<BIamUserLoginLogListOutputDto> page = userLoginLogClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        PageResponse<BIamUserLoginLogExpandListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamUserLoginLogExpandListResponseVo.class));

        {//创建人、修改人姓名
            Set<String> userIdSet = page.getRecords().stream().map(BIamUserLoginLogListOutputDto::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(page.getRecords().stream().map(BIamUserLoginLogListOutputDto::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamUserLoginLogExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }
        return pageResponse;
    }
}
