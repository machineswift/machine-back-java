package com.machine.app.iam.biam.log.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.log.business.IBIamOperationLogBusiness;
import com.machine.app.iam.biam.log.controller.vo.request.BIamOperationLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamOperationLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamOperationLogExpandListResponseVo;
import com.machine.client.iam.biam.log.IBIamOperationLogClient;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamOperationLogListOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
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
public class BIamOperationLogBusinessImpl implements IBIamOperationLogBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamOperationLogClient operationLogClient;

    @Override
    public BIamOperationLogDetailResponseVo detail(IdRequest request) {
        BIamOperationLogDetailOutputDto outputDto = operationLogClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        BIamOperationLogDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamOperationLogDetailResponseVo.class);

        {//填充创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            if (userSimpleDetailMap.containsKey(responseVo.getCreateBy())) {
                responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            }
        }

        return responseVo;
    }

    @Override
    public PageResponse<BIamOperationLogExpandListResponseVo> pageExpand(BIamOperationLogQueryPageRequestVo request) {
        BIamOperationLogQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamOperationLogQueryPageInputDto.class);
        PageResponse<BIamOperationLogListOutputDto> page = operationLogClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        PageResponse<BIamOperationLogExpandListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamOperationLogExpandListResponseVo.class));

        {//创建人姓名
            Set<String> userIdSet = page.getRecords().stream().map(BIamOperationLogListOutputDto::getCreateBy).collect(Collectors.toSet());
            userIdSet.remove(null);
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
                for (BIamOperationLogExpandListResponseVo vo : pageResponse.getRecords()) {
                    if (userSimpleDetailMap.containsKey(vo.getCreateBy())) {
                        vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                    }
                }
            }
        }
        return pageResponse;
    }
}
