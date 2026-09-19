package com.machine.app.iam.biam.permission.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.permission.business.IBIamPermissionBusiness;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionCreateRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionUpdateParentRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.request.BIamPermissionUpdateRequestVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionDetailResponseVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionTreeExpandResponseVo;
import com.machine.app.iam.biam.permission.controller.vo.response.BIamPermissionTreeSimpleResponseVo;
import com.machine.client.iam.biam.permission.IBIamPermissionClient;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionCreateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateInputDto;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateParentInputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionDetailOutputDto;

import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.envm.biam.permission.BIamPermissionResourceTypeEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.biam.RedisBIamPermissionCache;
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
public class BIamPermissionBusinessImpl implements IBIamPermissionBusiness {

    @Autowired
    private RedisBIamPermissionCache permissionCache;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamPermissionClient permissionClient;

    @Override
    public String create(BIamPermissionCreateRequestVo request) {
        request.setName(request.getName().trim());
        request.setCode(request.getCode().trim());

        BIamPermissionResourceTypeEnum resourceType = request.getResourceType();
        if (BIamPermissionResourceTypeEnum.APP == resourceType ||
                BIamPermissionResourceTypeEnum.MODULE == resourceType) {
            throw new BIamBusinessException("biam.permission.business.create", "暂不支持新增APP和MODULE");
        }

        BIamPermissionCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamPermissionCreateInputDto.class);
        return permissionClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        permissionClient.delete(request);
    }

    @Override
    public void update(BIamPermissionUpdateRequestVo request) {
        request.setName(request.getName().trim());
        request.setCode(request.getCode().trim());

        BIamPermissionUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamPermissionUpdateInputDto.class);
        permissionClient.update(inputDto);
    }

    @Override
    public void updateParent(BIamPermissionUpdateParentRequestVo request) {
        BIamPermissionUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamPermissionUpdateParentInputDto.class);
        permissionClient.updateParent(inputDto);
    }

    @Override
    public BIamPermissionDetailResponseVo detail(IdRequest request) {
        BIamPermissionDetailOutputDto outputDto = permissionClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        BIamPermissionDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamPermissionDetailResponseVo.class);
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        return responseVo;
    }

    @Override
    public BIamPermissionTreeSimpleResponseVo treeSimple(IdRequest request) {
        BIamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();

        BIamPermissionTreeOutputDto targetTreeOutputDto = TreeUtil.findNode(allTreeOutputDto, request.getId());
        if (null == targetTreeOutputDto) {
            return null;
        }

        return JSONUtil.toBean(JSONUtil.toJsonStr(targetTreeOutputDto), BIamPermissionTreeSimpleResponseVo.class);
    }

    @Override
    public BIamPermissionTreeExpandResponseVo treeExpand(IdRequest request) {
        BIamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();

        BIamPermissionTreeOutputDto targetTreeOutputDto = TreeUtil.findNode(allTreeOutputDto, request.getId());
        if (null == targetTreeOutputDto) {
            return null;
        }

        BIamPermissionTreeExpandResponseVo targetTreeResponseVo = JSONUtil.toBean(JSONUtil.toJsonStr(targetTreeOutputDto), BIamPermissionTreeExpandResponseVo.class);

        List<BIamPermissionTreeExpandResponseVo> expandResponseVoList = TreeUtil.collectAllNodes(targetTreeResponseVo);

        {//创建人、修改人姓名
            Set<String> userIdSet = expandResponseVoList.stream().map(BIamPermissionTreeExpandResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(expandResponseVoList.stream().map(BIamPermissionTreeExpandResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamPermissionTreeExpandResponseVo vo : expandResponseVoList) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return targetTreeResponseVo;
    }
}
