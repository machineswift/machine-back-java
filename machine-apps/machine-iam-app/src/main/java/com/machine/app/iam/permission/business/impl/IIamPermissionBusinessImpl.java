package com.machine.app.iam.permission.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.permission.business.IIamPermissionBusiness;
import com.machine.app.iam.permission.controller.vo.request.*;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionDetailResponseVo;
import com.machine.client.iam.permission.IIamPermissionClient;
import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.PermissionDetailOutputDto;

import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheIamPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class IIamPermissionBusinessImpl implements IIamPermissionBusiness {

    @Autowired
    private RedisCacheIamPermission permissionCache;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamPermissionClient permissionClient;

    @Override
    public String create(IamPermissionCreateRequestVo requestVo) {
        PermissionResourceTypeEnum resourceType = requestVo.getResourceType();
        if (PermissionResourceTypeEnum.APP == resourceType ||
                PermissionResourceTypeEnum.MODULE == resourceType) {
            throw new IamBusinessException("iam.permission.create", "暂不支持新增APP和MODULE");
        }

        IamPermissionCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(requestVo), IamPermissionCreateInputDto.class);
        return permissionClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        permissionClient.delete(request);
    }

    @Override
    public void update(IamPermissionUpdateRequestVo request) {
        IamPermissionUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamPermissionUpdateInputDto.class);
        permissionClient.update(inputDto);
    }

    @Override
    public void updateStatus(IamPermissionUpdateStatusRequestVo request) {
        IamPermissionUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamPermissionUpdateStatusInputDto.class);
        permissionClient.updateStatus(inputDto);
    }

    @Override
    public void updateParent(IamPermissionUpdateParentRequestVo request) {
        IamPermissionUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamPermissionUpdateParentInputDto.class);
        permissionClient.updateParent(inputDto);
    }

    @Override
    public IamPermissionDetailResponseVo detail(IdRequest request) {
        PermissionDetailOutputDto outputDto = permissionClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        IamPermissionDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamPermissionDetailResponseVo.class);
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        return responseVo;
    }

    @Override
    public List<PermissionTreeOutputDto> listApp(IamPermissionQueryAppListRequestVo request) {
        PermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        List<PermissionTreeOutputDto> children = treeOutputDto.getChildren();
        for (PermissionTreeOutputDto child : children) {
            child.setChildren(null);
        }
        return children;
    }

    @Override
    public List<PermissionTreeOutputDto> listSub(IamPermissionQueryListSubRequestVo request) {
        return permissionCache.listSub(request.getId());
    }

    @Override
    public PermissionTreeOutputDto tree(IamPermissionQueryTreeRequestVo request) {
        PermissionTreeOutputDto treeOutputDto = permissionCache.treeAll();
        return TreeUtil.findNode(treeOutputDto, request.getId());
    }
}
