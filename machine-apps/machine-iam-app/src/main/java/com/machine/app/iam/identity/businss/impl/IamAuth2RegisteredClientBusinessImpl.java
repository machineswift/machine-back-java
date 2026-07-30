package com.machine.app.iam.identity.businss.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.identity.businss.IIamAuth2RegisteredClientBusiness;
import com.machine.app.iam.identity.controller.vo.request.*;
import com.machine.app.iam.identity.controller.vo.response.IamAuth2RegisteredClientDetailResponseVo;
import com.machine.app.iam.identity.controller.vo.response.IamAuth2RegisteredClientListResponseVo;
import com.machine.client.iam.identity.IIamOauth2RegisteredClientClient;
import com.machine.client.iam.identity.dto.input.*;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.identity.dto.output.IamOAuth2RegisteredClientListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.exception.iam.IamBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonIamConstant.User.ROOT_USER_ID;

@Slf4j
@Component
@RefreshScope
public class IamAuth2RegisteredClientBusinessImpl implements IIamAuth2RegisteredClientBusiness {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    @Override
    public String create(IamAuth2RegisteredClientCreateRequestVo request) {
        IamOAuth2RegisteredClientCreateInputDto inputDto = new IamOAuth2RegisteredClientCreateInputDto();
        inputDto.setClientName(request.getClientName());
        inputDto.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
        inputDto.setScopes(request.getScopes());
        return oauth2RegisteredClientClient.create(inputDto);
    }

    @Override
    public void update(IamAuth2RegisteredClientUpdateRequestVo request) {
        IamOAuth2RegisteredClientUpdateInputDto inputDto = new IamOAuth2RegisteredClientUpdateInputDto();
        inputDto.setId(request.getId());
        inputDto.setClientName(request.getClientName());
        if (StrUtil.isNotBlank(request.getClientSecret())) {
            inputDto.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
        }
        inputDto.setScopes(request.getScopes());
        oauth2RegisteredClientClient.update(inputDto);
    }

    @Override
    public void updateStatus(IamAuth2RegisteredClientUpdateStatusRequestVo request) {
        IamOAuth2RegisteredClientUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamOAuth2RegisteredClientUpdateStatusInputDto.class);
        oauth2RegisteredClientClient.updateStatus(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        if (!ROOT_USER_ID.equals(AppContextHolder.getContext().getUserId())) {
            throw new IamBusinessException("iam.identity.business.delete.notRootUser", "只有超级管理员才能执行删除操作");
        }
        oauth2RegisteredClientClient.delete(request);
    }

    @Override
    public IamAuth2RegisteredClientDetailResponseVo detail(IdRequest request) {
        IamOAuth2RegisteredClientDetailOutputDto outputDto = oauth2RegisteredClientClient.detail(request);
        if (Objects.isNull(outputDto)) {
            return null;
        }

        IamAuth2RegisteredClientDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamAuth2RegisteredClientDetailResponseVo.class);
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
    public PageResponse<IamAuth2RegisteredClientListResponseVo> pageExpand(IamAuth2RegisteredClientPageQueryRequestVo query) {
        IamOAuth2RegisteredClientPageQueryInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(query), IamOAuth2RegisteredClientPageQueryInputDto.class);

        PageResponse<IamOAuth2RegisteredClientListOutputDto> pageOutputDto = oauth2RegisteredClientClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        PageResponse<IamAuth2RegisteredClientListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), IamAuth2RegisteredClientListResponseVo.class));

        {  //创建人、修改文姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(IamAuth2RegisteredClientListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(IamAuth2RegisteredClientListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamAuth2RegisteredClientListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return pageResponse;
    }
}
