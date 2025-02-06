package com.machine.app.openapi.iam.role.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.iam.role.business.IOpenApiRoleBusiness;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleIdRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleListSubRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.request.OpenApiRoleRootRequestVo;
import com.machine.app.openapi.iam.role.controller.vo.response.OpenApiRoleDetailResponseVo;
import com.machine.app.openapi.iam.role.controller.vo.response.OpenApiRolePermissionResponseVo;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.IIamRolePermissionClient;
import com.machine.client.iam.role.dto.input.IamRoleListSubInputDto;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.sdk.common.envm.iam.role.CompanyDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class OpenApiRoleBusinessImpl implements IOpenApiRoleBusiness {

    @Autowired
    private IIamRoleClient roleClient;

    @Autowired
    private IIamRolePermissionClient rolePermissionClient;

    @Override
    public String rootId(OpenApiRoleRootRequestVo request) {
        return request.getType().getCode().toLowerCase();
    }

    @Override
    public OpenApiRoleDetailResponseVo detail(OpenApiRoleIdRequestVo request) {
        IdRequest roleIdRequest = new IdRequest(request.getId());
        IamRoleDetailOutputDto outputDto = roleClient.detail(roleIdRequest);
        if (outputDto == null) {
            return null;
        }

        OpenApiRoleDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiRoleDetailResponseVo.class);

        //填充权限信息
        List<IamRolePermissionListOutputDto> outputDtoList = rolePermissionClient.listByRoleId(roleIdRequest);
        Set<String> permissionIdList = new HashSet<>();
        Map<String, String> dataPermissionMap = new HashMap<>();
        for (IamRolePermissionListOutputDto dto : outputDtoList) {
            permissionIdList.add(dto.getPermissionId());
            if (null != dto.getDataInto() && !dto.getDataInto().isEmpty()) {
                dataPermissionMap.put(dto.getPermissionId(), dto.getDataInto());
            }
        }
        responseVo.setPermissionIdList(permissionIdList);
        responseVo.setDataPermissionMap(dataPermissionMap);

        //默认角色
        List<String> defaultRoleCodeList = getDefaultRoleCodeList(responseVo.getType());
        if (defaultRoleCodeList.contains(responseVo.getCode())) {
            responseVo.setDefaultRole(Boolean.TRUE);
        } else {
            responseVo.setDefaultRole(Boolean.FALSE);
        }

        return responseVo;
    }

    @Override
    public List<String> listSubId(OpenApiRoleListSubRequestVo request) {
        return roleClient.listSubId(
                new IamRoleListSubInputDto(request.getId(), request.getStatus()));
    }

    @Override
    public List<String> listParentByTarget(OpenApiRoleIdRequestVo request) {
        return roleClient.listParentByTarget(new IdRequest(request.getId()));
    }

    @Override
    public OpenApiRolePermissionResponseVo listPermissionByTarget(OpenApiRoleIdRequestVo request) {
        IdRequest roleIdRequest = new IdRequest(request.getId());
        IamRoleDetailOutputDto outputDto = roleClient.detail(roleIdRequest);
        if (null == outputDto) {
            return null;
        }

        //填充权限信息
        List<IamRolePermissionListOutputDto> outputDtoList = rolePermissionClient.listByRoleId(roleIdRequest);
        Set<String> permissionIdList = new HashSet<>();
        Map<String, String> dataPermissionMap = new HashMap<>();
        for (IamRolePermissionListOutputDto dto : outputDtoList) {
            permissionIdList.add(dto.getPermissionId());
            if (null != dto.getDataInto() && !dto.getDataInto().isEmpty()) {
                dataPermissionMap.put(dto.getPermissionId(), dto.getDataInto());
            }
        }

        OpenApiRolePermissionResponseVo responseVo = new OpenApiRolePermissionResponseVo();
        responseVo.setPermissionIdList(permissionIdList);
        responseVo.setDataPermissionMap(dataPermissionMap);
        return responseVo;
    }

    private List<String> getDefaultRoleCodeList(RoleTypeEnum type) {
        if (RoleTypeEnum.COMPANY == type) {
            return Arrays.stream(CompanyDefaultRoleEnum.values())
                    .map(CompanyDefaultRoleEnum::getCode)
                    .collect(Collectors.toList());
        } else if (RoleTypeEnum.SHOP == type) {
            return Arrays.stream(ShopDefaultRoleEnum.values())
                    .map(ShopDefaultRoleEnum::getCode)
                    .collect(Collectors.toList());
        } else if (RoleTypeEnum.SUPPLIER == type) {
            return Arrays.stream(ShopDefaultRoleEnum.values())
                    .map(ShopDefaultRoleEnum::getCode)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
