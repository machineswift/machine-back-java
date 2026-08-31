package com.machine.app.iam.biam.log.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.log.business.IBIamUserAccessLogBusiness;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserAccessLogDeleteRequestVo;
import com.machine.app.iam.biam.log.controller.vo.request.BIamUserAccessLogQueryPageRequestVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserAccessLogDetailResponseVo;
import com.machine.app.iam.biam.log.controller.vo.response.BIamUserAccessLogExpandListResponseVo;
import com.machine.client.iam.biam.log.IBIamUserAccessLogClient;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogDeleteInputDto;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogDetailOutputDto;
import com.machine.client.iam.biam.log.dto.output.BIamUserAccessLogListOutputDto;
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
public class BIamUserAccessLogBusinessImpl implements IBIamUserAccessLogBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamUserAccessLogClient userAccessLogClient;

    @Override
    public BIamUserAccessLogDetailResponseVo detail(IdRequest request) {
        BIamUserAccessLogDetailOutputDto outputDto = userAccessLogClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        BIamUserAccessLogDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamUserAccessLogDetailResponseVo.class);

        {//填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            if (userSimpleDetailMap.containsKey(responseVo.getCreateBy())) {
                responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            }
            if (userSimpleDetailMap.containsKey(responseVo.getUpdateBy())) {
                responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
            }
        }

        return responseVo;
    }

    @Override
    public PageResponse<BIamUserAccessLogExpandListResponseVo> pageExpand(BIamUserAccessLogQueryPageRequestVo request) {
        BIamUserAccessLogQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserAccessLogQueryPageInputDto.class);
        PageResponse<BIamUserAccessLogListOutputDto> page = userAccessLogClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        PageResponse<BIamUserAccessLogExpandListResponseVo> pageResponse = new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamUserAccessLogExpandListResponseVo.class));

        {//创建人、修改人姓名
            Set<String> userIdSet = page.getRecords().stream().map(BIamUserAccessLogListOutputDto::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(page.getRecords().stream().map(BIamUserAccessLogListOutputDto::getUpdateBy).collect(Collectors.toSet()));
            userIdSet.remove(null);
            if (CollectionUtil.isNotEmpty(userIdSet)) {
                Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
                for (BIamUserAccessLogExpandListResponseVo vo : pageResponse.getRecords()) {
                    if (userSimpleDetailMap.containsKey(vo.getCreateBy())) {
                        vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                    }
                    if (userSimpleDetailMap.containsKey(vo.getUpdateBy())) {
                        vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
                    }
                }
            }
        }
        return pageResponse;
    }

    @Override
    public int delete(BIamUserAccessLogDeleteRequestVo request) {
        BIamUserAccessLogDeleteInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamUserAccessLogDeleteInputDto.class);
        return userAccessLogClient.deleteByCreateTimeBefore(inputDto);
    }
}
