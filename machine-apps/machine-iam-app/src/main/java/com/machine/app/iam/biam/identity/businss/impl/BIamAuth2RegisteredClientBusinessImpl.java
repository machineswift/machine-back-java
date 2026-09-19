package com.machine.app.iam.biam.identity.businss.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.identity.businss.IBIamAuth2RegisteredClientBusiness;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientCreateRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientPageQueryRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientUpdateRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.request.BIamAuth2RegisteredClientUpdateStatusRequestVo;
import com.machine.app.iam.biam.identity.controller.vo.response.BIamAuth2RegisteredClientDetailResponseVo;
import com.machine.app.iam.biam.identity.controller.vo.response.BIamAuth2RegisteredClientListResponseVo;
import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientCreateInputDto;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientUpdateInputDto;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientUpdateStatusInputDto;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientDetailOutputDto;
import com.machine.client.iam.biam.identity.dto.output.BIamOAuth2RegisteredClientListOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.redis.command.CustomerRedisCommands;
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

import static com.machine.sdk.base.constant.CommonBIamConstant.User.ROOT_USER_ID;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Auth2RegisteredClient.BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY;

@Slf4j
@Component
@RefreshScope
public class BIamAuth2RegisteredClientBusinessImpl implements IBIamAuth2RegisteredClientBusiness {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamOauth2RegisteredClientClient oauth2RegisteredClientClient;

    @Override
    public void cleanCache() {
        customerRedisCommands.del(BIAM_IDENTITY_AUTH2_REGISTERED_CLIENT_VERSION_KEY);
    }

    @Override
    public String create(BIamAuth2RegisteredClientCreateRequestVo request) {
        BIamOAuth2RegisteredClientCreateInputDto inputDto = new BIamOAuth2RegisteredClientCreateInputDto();
        inputDto.setAuthorizationGrantType(request.getAuthorizationGrantType());
        inputDto.setClientName(request.getClientName());
        inputDto.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
        inputDto.setScopes(request.getScopes());
        inputDto.setAllowedIps(request.getAllowedIps());
        inputDto.setRedirectUris(request.getRedirectUris());
        inputDto.setPostLogoutRedirectUris(request.getPostLogoutRedirectUris());
        return oauth2RegisteredClientClient.create(inputDto);
    }

    @Override
    public void update(BIamAuth2RegisteredClientUpdateRequestVo request) {
        BIamOAuth2RegisteredClientUpdateInputDto inputDto = new BIamOAuth2RegisteredClientUpdateInputDto();
        inputDto.setId(request.getId());
        inputDto.setClientName(request.getClientName());
        if (StrUtil.isNotBlank(request.getClientSecret())) {
            inputDto.setClientSecret(passwordEncoder.encode(request.getClientSecret()));
        }
        inputDto.setScopes(request.getScopes());
        inputDto.setAllowedIps(request.getAllowedIps());
        inputDto.setRedirectUris(request.getRedirectUris());
        inputDto.setPostLogoutRedirectUris(request.getPostLogoutRedirectUris());
        oauth2RegisteredClientClient.update(inputDto);
    }

    @Override
    public void updateStatus(BIamAuth2RegisteredClientUpdateStatusRequestVo request) {
        BIamOAuth2RegisteredClientUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamOAuth2RegisteredClientUpdateStatusInputDto.class);
        oauth2RegisteredClientClient.updateStatus(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        if (!ROOT_USER_ID.equals(AppContextHolder.getContext().getUserId())) {
            throw new BIamBusinessException("biam.identity.business.oauth2RegisteredClient.delete.notRootUser", "只有超级管理员才能执行删除操作");
        }
        oauth2RegisteredClientClient.delete(request);
    }

    @Override
    public BIamAuth2RegisteredClientDetailResponseVo detail(IdRequest request) {
        BIamOAuth2RegisteredClientDetailOutputDto outputDto = oauth2RegisteredClientClient.detail(request);
        if (Objects.isNull(outputDto)) {
            return null;
        }

        BIamAuth2RegisteredClientDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamAuth2RegisteredClientDetailResponseVo.class);
        {//填充修改人创建人信息
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
    public PageResponse<BIamAuth2RegisteredClientListResponseVo> pageExpand(BIamAuth2RegisteredClientPageQueryRequestVo query) {
        BIamOAuth2RegisteredClientPageQueryInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(query), BIamOAuth2RegisteredClientPageQueryInputDto.class);

        PageResponse<BIamOAuth2RegisteredClientListOutputDto> pageOutputDto = oauth2RegisteredClientClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(
                    pageOutputDto.getCurrent(),
                    pageOutputDto.getSize(),
                    pageOutputDto.getTotal());
        }

        PageResponse<BIamAuth2RegisteredClientListResponseVo> pageResponse = new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), BIamAuth2RegisteredClientListResponseVo.class));

        {  //创建人、修改文姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(BIamAuth2RegisteredClientListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(BIamAuth2RegisteredClientListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamAuth2RegisteredClientListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return pageResponse;
    }
}
