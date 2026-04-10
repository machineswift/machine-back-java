package com.machine.app.manage.data.file.material.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.material.business.IDataMaterialBusiness;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.client.data.file.material.IDataMaterialCategoryRelationClient;
import com.machine.client.data.file.material.IDataMaterialClient;
import com.machine.client.data.file.material.dto.input.DataMaterialCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateCategoryInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataMaterialBusinessImpl implements IDataMaterialBusiness {

    private final IIamUserClient userClient;
    private final IDataMaterialClient materialClient;
    private final IDataMaterialCategoryRelationClient materialCategoryRelationClient;

    @Override
    public String create(DataMaterialCreateRequestVo request) {
        DataMaterialCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialCreateInputDto.class);
        return materialClient.create(inputDto);
    }

    @Override
    public void update(DataMaterialUpdateRequestVo request) {
        DataMaterialUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialUpdateInputDto.class);
        materialClient.update(inputDto);
    }

    @Override
    public void updateCategory(DataMaterialUpdateCategoryRequestVo request) {
        DataMaterialUpdateCategoryInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialUpdateCategoryInputDto.class);
        materialClient.updateCategory(inputDto);
    }

    @Override
    public DataMaterialDetailResponseVo detail(IdRequest request) {
        DataMaterialDetailOutputDto outputDto = materialClient.getById(request);
        if (outputDto == null) {
            return null;
        }

        DataMaterialDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataMaterialDetailResponseVo.class);
        fillDetailCategoryRelations(responseVo);
        fillDetailUserNames(outputDto, responseVo);
        return responseVo;
    }

    @Override
    public PageResponse<DataMaterialExpandListResponseVo> pageExpand(DataMaterialQueryPageRequestVo request) {
        DataMaterialQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialQueryPageInputDto.class);
        if (inputDto.getContainVirtualNode() == null) {
            inputDto.setContainVirtualNode(false);
        }
        PageResponse<DataMaterialListOutputDto> pageOutput = materialClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        PageResponse<DataMaterialExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataMaterialExpandListResponseVo.class));
        fillPageCategoryRelations(pageResponse.getRecords());
        fillPageUserNames(pageResponse.getRecords());
        return pageResponse;
    }

    /** 填充详情页的素材分类 ID 集合 */
    private void fillDetailCategoryRelations(DataMaterialDetailResponseVo responseVo) {
        List<DataMaterialCategoryRelationOutputDto> relationList =
                materialCategoryRelationClient.listByMaterialId(new IdRequest(responseVo.getId()));
        if (CollectionUtil.isNotEmpty(relationList)) {
            Set<String> categoryIdSet = relationList.stream()
                    .map(DataMaterialCategoryRelationOutputDto::getCategoryId)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            responseVo.setCategoryIdSet(categoryIdSet);
        }
    }

    /** 填充详情页的创建人、修改人姓名（空安全） */
    private void fillDetailUserNames(DataMaterialDetailOutputDto outputDto, DataMaterialDetailResponseVo responseVo) {
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, IamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userNameOrNull(userMap.get(responseVo.getCreateBy())));
        responseVo.setUpdateName(userNameOrNull(userMap.get(responseVo.getUpdateBy())));
    }

    /** 填充分页列表的素材分类 ID 集合 */
    private void fillPageCategoryRelations(List<DataMaterialExpandListResponseVo> records) {
        Set<String> materialIdSet = records.stream().map(DataMaterialExpandListResponseVo::getId).collect(Collectors.toSet());
        List<DataMaterialCategoryRelationOutputDto> relationList =
                materialCategoryRelationClient.listByMaterialIdSet(new IdSetRequest(materialIdSet));
        Map<String, Set<String>> materialIdToCategoryIds = relationList.stream()
                .collect(Collectors.groupingBy(DataMaterialCategoryRelationOutputDto::getMaterialId,
                        Collectors.mapping(DataMaterialCategoryRelationOutputDto::getCategoryId, Collectors.toCollection(LinkedHashSet::new))));
        for (DataMaterialExpandListResponseVo vo : records) {
            vo.setCategoryIdSet(materialIdToCategoryIds.getOrDefault(vo.getId(), Set.of()));
        }
    }

    /** 填充分页列表的创建人、修改人姓名（空安全） */
    private void fillPageUserNames(List<DataMaterialExpandListResponseVo> records) {
        Set<String> userIdSet = new HashSet<>();
        for (DataMaterialExpandListResponseVo vo : records) {
            if (vo.getCreateBy() != null) userIdSet.add(vo.getCreateBy());
            if (vo.getUpdateBy() != null) userIdSet.add(vo.getUpdateBy());
        }
        Map<String, IamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataMaterialExpandListResponseVo vo : records) {
            vo.setCreateName(userNameOrNull(userMap.get(vo.getCreateBy())));
            vo.setUpdateName(userNameOrNull(userMap.get(vo.getUpdateBy())));
        }
    }

    private static String userNameOrNull(IamUserDetailOutputDto user) {
        return user != null ? user.getName() : null;
    }
}
