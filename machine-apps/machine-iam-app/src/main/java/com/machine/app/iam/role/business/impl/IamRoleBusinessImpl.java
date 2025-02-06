package com.machine.app.iam.role.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.iam.role.business.IIamRoleBusiness;
import com.machine.app.iam.role.controller.vo.request.*;
import com.machine.app.iam.role.controller.vo.response.IamJobPostListResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleDetailResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleExpandListResponseVo;
import com.machine.app.iam.role.controller.vo.response.IamRoleSimpleListResponseVo;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.role.IIamRoleClient;
import com.machine.client.iam.role.IIamRolePermissionClient;
import com.machine.client.iam.role.dto.input.IamRoleCreateInputDto;
import com.machine.client.iam.role.dto.input.IamRoleQueryPageInputDto;
import com.machine.client.iam.role.dto.input.IamRoleUpdateInputDto;
import com.machine.client.iam.role.dto.input.IamRoleUpdateStatusInputDto;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserRoleTargetClient;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.sdk.common.envm.iam.role.CompanyDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.SupplierDefaultRoleEnum;
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
    private IHrmJobPostClient jobPostClient;

    @Autowired
    private IIamRolePermissionClient rolePermissionClient;

    @Autowired
    private IIamUserRoleTargetClient userRoleTargetClient;


    @Override
    public String create(IamRoleCreateRequestVo requestVo) {
        IamRoleCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(requestVo), IamRoleCreateInputDto.class);
        return roleClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        roleClient.delete(request);
    }

    @Override
    public void update(IamRoleUpdateRequestVo request) {
        IamRoleUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleUpdateInputDto.class);
        roleClient.update(inputDto);
    }

    @Override
    public void updateStatus(IamRoleUpdateStatusRequestVo request) {
        IamRoleUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleUpdateStatusInputDto.class);
        roleClient.updateStatus(inputDto);
    }

    @Override
    public IamRoleDetailResponseVo detail(IdRequest request) {
        IamRoleDetailOutputDto outputDto = roleClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        IamRoleDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), IamRoleDetailResponseVo.class);

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());

        //填充权限信息
        List<IamRolePermissionListOutputDto> outputDtoList = rolePermissionClient.listByRoleId(request);
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

        //职务列表
        List<HrmJobPostListSimpleOutputDto> jobPostListSimpleOutputDtoList = jobPostClient.listByRoleId(new IdRequest(request.getId()));
        if (CollectionUtil.isNotEmpty(jobPostListSimpleOutputDtoList)) {
            List<IamJobPostListResponseVo> jobPostList = JSONUtil.toList(JSONUtil.toJsonStr(jobPostListSimpleOutputDtoList), IamJobPostListResponseVo.class);
            responseVo.setJobPostList(jobPostList);
        }

        //默认角色
        Set<String> defaultRoleCodeSet = getDefaultRoleCodeList(responseVo.getType());
        if (defaultRoleCodeSet.contains(responseVo.getCode())) {
            responseVo.setDefaultRole(Boolean.TRUE);
        } else {
            responseVo.setDefaultRole(Boolean.FALSE);
        }

        return responseVo;
    }

    @Override
    public PageResponse<IamRoleSimpleListResponseVo> pageSimple(IamRoleQueryPageSimpleRequestVo request) {
        IamRoleQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleQueryPageInputDto.class);
        PageResponse<IamRoleListOutputDto> page = roleClient.page(inputDto);

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
    public PageResponse<IamRoleExpandListResponseVo> pageExpand(IamRoleQueryPageExpandRequestVo request) {
        //查询分页数据
        IamRoleQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), IamRoleQueryPageInputDto.class);
        PageResponse<IamRoleListOutputDto> pageOutput = roleClient.page(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<IamRoleExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), IamRoleExpandListResponseVo.class));

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(IamRoleExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(IamRoleExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (IamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        //关联人数
        Set<String> roleIdSet = pageResponse.getRecords().stream().map(IamRoleExpandListResponseVo::getId).collect(Collectors.toSet());
        List<IamUserRoleTargetListOutputDto> outputDtoList = userRoleTargetClient.listByRoleIdSet(new IdSetRequest(roleIdSet));
        Map<String, Set<String>> roleUserIdSetMap = new HashMap<>();
        for (IamUserRoleTargetListOutputDto dto : outputDtoList) {
            Set<String> userIdSetItem = roleUserIdSetMap.computeIfAbsent(dto.getRoleId(), k -> new HashSet<>());
            userIdSetItem.add(dto.getUserId());
        }

        for (IamRoleExpandListResponseVo vo : pageResponse.getRecords()) {
            Set<String> userIdSetItem = roleUserIdSetMap.get(vo.getId());
            if (null == userIdSetItem) {
                vo.setUserNumber(0);
            } else {
                vo.setUserNumber(userIdSetItem.size());
            }
        }

        //存在默认角色，修改排序
        Set<String> defaultRoleCodeSet = getDefaultRoleCodeList(inputDto.getType());
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
        return pageResponse;
    }

    private Set<String> getDefaultRoleCodeList(RoleTypeEnum type) {
        if (RoleTypeEnum.COMPANY == type) {
            return Arrays.stream(CompanyDefaultRoleEnum.values())
                    .map(CompanyDefaultRoleEnum::getCode).collect(Collectors.toSet());
        } else if (RoleTypeEnum.SHOP == type) {
            return Arrays.stream(ShopDefaultRoleEnum.values())
                    .map(ShopDefaultRoleEnum::getCode).collect(Collectors.toSet());
        } else if (RoleTypeEnum.SUPPLIER == type) {
            return Arrays.stream(SupplierDefaultRoleEnum.values())
                    .map(SupplierDefaultRoleEnum::getCode).collect(Collectors.toSet());
        }
        return Set.of();
    }
}
