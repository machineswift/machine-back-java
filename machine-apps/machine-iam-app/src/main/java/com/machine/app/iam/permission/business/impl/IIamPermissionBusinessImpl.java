package com.machine.app.iam.permission.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.permission.business.IIamPermissionBusiness;
import com.machine.app.iam.permission.controller.vo.request.*;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionDetailResponseVo;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionTreeExpandResponseVo;
import com.machine.app.iam.permission.controller.vo.response.IamPermissionTreeSimpleResponseVo;
import com.machine.client.iam.permission.IIamPermissionClient;
import com.machine.client.iam.permission.dto.input.*;
import com.machine.client.iam.permission.dto.output.IamPermissionDetailOutputDto;

import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
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
import java.util.stream.Collectors;

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
    public String create(IamPermissionCreateRequestVo request) {
        request.setName(request.getName().trim());
        request.setCode(request.getCode().trim());

        IamPermissionResourceTypeEnum resourceType = request.getResourceType();
        if (IamPermissionResourceTypeEnum.APP == resourceType ||
                IamPermissionResourceTypeEnum.MODULE == resourceType) {
            throw new IamBusinessException("iam.permission.business.create", "暂不支持新增APP和MODULE");
        }

        IamPermissionCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamPermissionCreateInputDto.class);
        return permissionClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        permissionClient.delete(request);
    }

    @Override
    public void update(IamPermissionUpdateRequestVo request) {
        request.setName(request.getName().trim());
        request.setCode(request.getCode().trim());

        IamPermissionUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamPermissionUpdateInputDto.class);
        permissionClient.update(inputDto);
    }

    @Override
    public void updateParent(IamPermissionUpdateParentRequestVo request) {
        IamPermissionUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamPermissionUpdateParentInputDto.class);
        permissionClient.updateParent(inputDto);
    }

    @Override
    public IamPermissionDetailResponseVo detail(IdRequest request) {
        IamPermissionDetailOutputDto outputDto = permissionClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        IamPermissionDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamPermissionDetailResponseVo.class);
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        return responseVo;
    }

    @Override
    public IamPermissionTreeSimpleResponseVo treeSimple(IdRequest request) {
        IamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();

        IamPermissionTreeOutputDto targetTreeOutputDto = TreeUtil.findNode(allTreeOutputDto, request.getId());
        if (null == targetTreeOutputDto) {
            return null;
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(targetTreeOutputDto), IamPermissionTreeSimpleResponseVo.class);
    }

    @Override
    public IamPermissionTreeExpandResponseVo treeExpand(IdRequest request) {
        IamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();

        IamPermissionTreeOutputDto targetTreeOutputDto = TreeUtil.findNode(allTreeOutputDto, request.getId());
        if (null == targetTreeOutputDto) {
            return null;
        }

        IamPermissionTreeExpandResponseVo targetTreeResponseVo = JSONUtil.toBean(JSONUtil.toJsonStr(targetTreeOutputDto), IamPermissionTreeExpandResponseVo.class);

        List<IamPermissionTreeExpandResponseVo> expandResponseVoList = TreeUtil.collectAllNodes(targetTreeResponseVo);

        {//创建人、修改人姓名
            Set<String> userIdSet = expandResponseVoList.stream().map(IamPermissionTreeExpandResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(expandResponseVoList.stream().map(IamPermissionTreeExpandResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamPermissionTreeExpandResponseVo vo : expandResponseVoList) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return targetTreeResponseVo;
    }
}
