package com.machine.app.manage.scm.property.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.scm.property.business.IScmPropertyBusiness;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyCreateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyQueryPageRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyUpdateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyDetailResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyListResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertySimpleListResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyValueListResponseVo;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.scm.property.IScmPropertyClient;
import com.machine.client.scm.property.dto.input.ScmPropertyCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
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
public class ScmPropertyBusinessImpl implements IScmPropertyBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IScmPropertyClient propertyClient;

    @Override
    public String create(ScmPropertyCreateRequestVo request) {
        log.info("创建属性，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyCreateInputDto.class);
        return propertyClient.create(inputDto);
    }

    @Override
    public void update(ScmPropertyUpdateRequestVo request) {
        log.info("修改属性，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyUpdateInputDto.class);
        propertyClient.update(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        propertyClient.deleteById(request);
    }

    @Override
    public ScmPropertyDetailResponseVo getById(IdRequest request) {
        log.info("获取属性详情，id={}", request.getId());
        ScmPropertyDetailOutputDto outputDto = propertyClient.getById(request);
        if (outputDto == null) {
            return null;
        }
        ScmPropertyDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmPropertyDetailResponseVo.class);
        if (CollectionUtil.isNotEmpty(outputDto.getValueList())) {
            responseVo.setValueList(outputDto.getValueList().stream()
                    .map(item -> JSONUtil.toBean(JSONUtil.toJsonStr(item), ScmPropertyValueListResponseVo.class))
                    .collect(Collectors.toList()));
        }
        fillUserName(responseVo);
        return responseVo;
    }

    @Override
    public PageResponse<ScmPropertySimpleListResponseVo> pageSimple(ScmPropertyQueryPageRequestVo request) {
        ScmPropertyQueryPageInputDto pageInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyQueryPageInputDto.class);
        PageResponse<ScmPropertyListOutputDto> pageOutputDto = propertyClient.selectPage(pageInputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }
        List<ScmPropertySimpleListResponseVo> records = pageOutputDto.getRecords().stream()
                .map(item -> JSONUtil.toBean(JSONUtil.toJsonStr(item), ScmPropertySimpleListResponseVo.class))
                .collect(Collectors.toList());
        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), records);
    }

    @Override
    public PageResponse<ScmPropertyListResponseVo> pageExpand(ScmPropertyQueryPageRequestVo request) {
        ScmPropertyQueryPageInputDto pageInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyQueryPageInputDto.class);
        PageResponse<ScmPropertyListOutputDto> pageOutputDto = propertyClient.selectPage(pageInputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }
        List<ScmPropertyListResponseVo> records = JSONUtil.toList(
                JSONUtil.toJsonStr(pageOutputDto.getRecords()), ScmPropertyListResponseVo.class);
        fillUserName(records);
        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), records);
    }


    private void fillUserName(ScmPropertyDetailResponseVo responseVo) {
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(responseVo.getCreateBy());
        userIdSet.add(responseVo.getUpdateBy());
        Map<String, IamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userMap.get(responseVo.getUpdateBy()).getName());
    }

    private void fillUserName(List<ScmPropertyListResponseVo> records) {
        Set<String> userIdSet = new HashSet<>();
        for (ScmPropertyListResponseVo record : records) {
            userIdSet.add(record.getCreateBy());
            userIdSet.add(record.getUpdateBy());
        }
        Map<String, IamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (ScmPropertyListResponseVo record : records) {
            record.setCreateName(userMap.get(record.getCreateBy()).getName());
            record.setUpdateName(userMap.get(record.getUpdateBy()).getName());
        }
    }
}
