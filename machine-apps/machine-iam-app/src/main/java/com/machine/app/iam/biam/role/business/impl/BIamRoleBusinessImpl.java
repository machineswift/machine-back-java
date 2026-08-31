package com.machine.app.iam.biam.role.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.biam.role.business.IBIamRoleBusiness;
import com.machine.app.iam.biam.role.controller.vo.request.*;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleDetailResponseVo;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleExpandListResponseVo;
import com.machine.app.iam.biam.role.controller.vo.response.BIamRoleSimpleListResponseVo;
import com.machine.client.iam.biam.role.IBIamRoleClient;
import com.machine.client.iam.biam.role.IBIamRolePermissionClient;
import com.machine.client.iam.biam.role.dto.input.*;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRoleListOutputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRolePermissionListOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.IBIamUserRoleRelationClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.envm.biam.role.BIamCompanyDefaultRoleEnum;
import com.machine.sdk.base.envm.biam.role.BIamOpenApiDefaultRoleEnum;
import com.machine.sdk.base.envm.biam.role.BIamShopDefaultRoleEnum;
import com.machine.sdk.base.envm.biam.role.BIamSupplierDefaultRoleEnum;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BIamRoleBusinessImpl implements IBIamRoleBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IBIamRoleClient roleClient;

    @Autowired
    private IBIamRolePermissionClient rolePermissionClient;

    @Autowired
    private IBIamUserRoleRelationClient userRoleRelationClient;

    @Override
    public String create(BIamRoleCreateRequestVo request) {
        request.setName(request.getName().trim());
        BIamRoleCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamRoleCreateInputDto.class);
        return roleClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        roleClient.delete(request);
    }

    @Override
    public void update(BIamRoleUpdateRequestVo request) {
        request.setName(request.getName().trim());
        BIamRoleUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamRoleUpdateInputDto.class);
        roleClient.update(inputDto);
    }

    @Override
    public void updateStatus(BIamRoleUpdateStatusRequestVo request) {
        BIamRoleUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamRoleUpdateStatusInputDto.class);
        roleClient.updateStatus(inputDto);
    }

    @Override
    public void updatePermission(BIamRoleUpdatePermissionRequestVo request) {
        BIamRoleUpdatePermissionInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamRoleUpdatePermissionInputDto.class);
        roleClient.updatePermission(inputDto);
    }

    @Override
    public BIamRoleDetailResponseVo detail(IdRequest request) {
        BIamRoleDetailOutputDto outputDto = roleClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        BIamRoleDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamRoleDetailResponseVo.class);

        {//填充权限信息
            List<BIamRolePermissionListOutputDto> outputDtoList = rolePermissionClient.listByRoleId(request);
            Set<String> permissionIdSet = new HashSet<>();
            Map<String, List<BIamDataPermissionRuleDto>> dataPermissionRuleMap = new HashMap<>();
            for (BIamRolePermissionListOutputDto dto : outputDtoList) {
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
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public PageResponse<BIamRoleSimpleListResponseVo> pageSimple(BIamRoleQueryPageRequestVo request) {
        BIamRoleQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamRoleQueryPageInputDto.class);
        PageResponse<BIamRoleListOutputDto> page = roleClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamRoleSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<BIamRoleExpandListResponseVo> pageExpand(BIamRoleQueryPageRequestVo request) {
        //查询分页数据
        BIamRoleQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), BIamRoleQueryPageInputDto.class);
        PageResponse<BIamRoleListOutputDto> pageOutput = roleClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<BIamRoleExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), BIamRoleExpandListResponseVo.class));

        { //关联人数
            Set<String> roleIdSet = pageResponse.getRecords().stream().map(BIamRoleExpandListResponseVo::getId).collect(Collectors.toSet());
            Map<String, Integer> roleIdCountMap = userRoleRelationClient.countUserByRoleIdSet(new IdSetRequest(roleIdSet));

            for (BIamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
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
            List<BIamRoleExpandListResponseVo> defaultList = new ArrayList<>();
            List<BIamRoleExpandListResponseVo> otherList = new ArrayList<>();
            for (BIamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
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
            Set<String> userIdSet = pageResponse.getRecords().stream().map(BIamRoleExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(BIamRoleExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (BIamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
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
        synchronized (BIamRoleBusinessImpl.class) {
            if (CollectionUtil.isNotEmpty(DEFAULT_ROLE_CODE_SET)) {
                return DEFAULT_ROLE_CODE_SET;
            }
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamCompanyDefaultRoleEnum.values())
                    .map(BIamCompanyDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamShopDefaultRoleEnum.values())
                    .map(BIamShopDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamSupplierDefaultRoleEnum.values())
                    .map(BIamSupplierDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamOpenApiDefaultRoleEnum.values())
                    .map(BIamOpenApiDefaultRoleEnum::getCode).collect(Collectors.toSet()));
        }
        return DEFAULT_ROLE_CODE_SET;
    }

    private static final Set<String> DEFAULT_ROLE_CODE_SET = new HashSet<>();
}
