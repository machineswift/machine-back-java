package com.machine.app.iam.role.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.role.business.IIamRoleBusiness;
import com.machine.app.iam.role.controller.vo.request.*;
import com.machine.app.iam.role.controller.vo.response.IamRoleDetailResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleExpandListResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleSimpleListResponseVo;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.IIamRolePermissionClient;
import com.machine.client.iam.role.dto.input.*;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserRoleRelationClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.envm.iam.role.IamCompanyDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.IamShopDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.IamSupplierDefaultRoleEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class IamRoleBusinessImpl implements IIamRoleBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamRoleClient roleClient;

    @Autowired
    private IIamRolePermissionClient rolePermissionClient;

    @Autowired
    private IIamUserRoleRelationClient userRoleRelationClient;

    @Override
    public String create(IamRoleCreateRequestVo request) {
        request.setName(request.getName().trim());
        IamRoleCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleCreateInputDto.class);
        return roleClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        roleClient.delete(request);
    }

    @Override
    public void update(IamRoleUpdateRequestVo request) {
        request.setName(request.getName().trim());
        IamRoleUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleUpdateInputDto.class);
        roleClient.update(inputDto);
    }

    @Override
    public void updateStatus(IamRoleUpdateStatusRequestVo request) {
        IamRoleUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleUpdateStatusInputDto.class);
        roleClient.updateStatus(inputDto);
    }

    @Override
    public void updatePermission(IamRoleUpdatePermissionRequestVo request) {
        IamRoleUpdatePermissionInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleUpdatePermissionInputDto.class);
        roleClient.updatePermission(inputDto);
    }

    @Override
    public IamRoleDetailResponseVo detail(IdRequest request) {
        IamRoleDetailOutputDto outputDto = roleClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        IamRoleDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamRoleDetailResponseVo.class);

        {//填充权限信息
            List<IamRolePermissionListOutputDto> outputDtoList = rolePermissionClient.listByRoleId(request);
            Set<String> permissionIdSet = new HashSet<>();
            Map<String, List<DataPermissionRuleDto>> dataPermissionRuleMap = new HashMap<>();
            for (IamRolePermissionListOutputDto dto : outputDtoList) {
                permissionIdSet.add(dto.getPermissionId());
                if (CollectionUtil.isNotEmpty(dto.getDataPermissionRuleList())) {
                    dataPermissionRuleMap.put(dto.getPermissionId(), dto.getDataPermissionRuleList());
                }
            }
            responseVo.setPermissionIdSet(permissionIdSet);
            responseVo.setDataPermissionRuleMap(dataPermissionRuleMap);
        }

        { //默认角色
        Set<String> defaultRoleCodeSet = getDefaultRoleCodeSet();
        if (defaultRoleCodeSet.contains(responseVo.getCode())) {
            responseVo.setDefaultRole(Boolean.TRUE);
        } else {
            responseVo.setDefaultRole(Boolean.FALSE);
        }
        }

        { //填充修改人创建人信息
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
    public PageResponse<IamRoleSimpleListResponseVo> pageSimple(IamRoleQueryPageRequestVo request) {
        IamRoleQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleQueryPageInputDto.class);
        PageResponse<IamRoleListOutputDto> page = roleClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamRoleSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<IamRoleExpandListResponseVo> pageExpand(IamRoleQueryPageRequestVo request) {
        //查询分页数据
        IamRoleQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleQueryPageInputDto.class);
        PageResponse<IamRoleListOutputDto> pageOutput = roleClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<IamRoleExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), IamRoleExpandListResponseVo.class));

        { //关联人数
            Set<String> roleIdSet = pageResponse.getRecords().stream().map(IamRoleExpandListResponseVo::getId).collect(Collectors.toSet());
            Map<String, Integer> roleIdCountMap = userRoleRelationClient.countUserByRoleIdSet(new IdSetRequest(roleIdSet));

            for (IamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
                Integer count = roleIdCountMap.get(vo.getId());
                if (null == count) {
                    vo.setUserNumber(0);
                } else {
                    vo.setUserNumber(count);
                }
            }
        }

        {  //存在默认角色，修改排序
            Set<String> defaultRoleCodeSet = getDefaultRoleCodeSet();
            List<IamRoleExpandListResponseVo> defaultList = new ArrayList<>();
            List<IamRoleExpandListResponseVo> otherList = new ArrayList<>();
            for (IamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
                if (defaultRoleCodeSet.contains(vo.getCode())) {
                    vo.setDefaultRole(Boolean.TRUE);
                    defaultList.add(vo);
                } else {
                    vo.setDefaultRole(Boolean.FALSE);
                    otherList.add(vo);
                }
            }
            defaultList.addAll(otherList);
            pageResponse.setRecords(defaultList);
        }

        {//创建人、修改人姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(IamRoleExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(IamRoleExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (IamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }
        return pageResponse;
    }

    private Set<String> getDefaultRoleCodeSet() {
        if (CollectionUtil.isNotEmpty(DEFAULT_ROLE_CODE_SET)) {
            return DEFAULT_ROLE_CODE_SET;
        }
        synchronized (IamRoleBusinessImpl.class) {
            if (CollectionUtil.isNotEmpty(DEFAULT_ROLE_CODE_SET)) {
                return DEFAULT_ROLE_CODE_SET;
            }
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(IamCompanyDefaultRoleEnum.values())
                    .map(IamCompanyDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(IamShopDefaultRoleEnum.values())
                    .map(IamShopDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(IamSupplierDefaultRoleEnum.values())
                    .map(IamSupplierDefaultRoleEnum::getCode).collect(Collectors.toSet()));
        }
        return DEFAULT_ROLE_CODE_SET;
    }

    private static final Set<String> DEFAULT_ROLE_CODE_SET = new HashSet<>();
}
