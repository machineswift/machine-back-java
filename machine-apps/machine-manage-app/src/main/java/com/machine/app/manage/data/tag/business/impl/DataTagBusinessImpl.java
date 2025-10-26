package com.machine.app.manage.data.tag.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.tag.business.IDataTagBusiness;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagCreateRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagQueryPageRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagUpdateCategoryRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagUpdateRequestVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagDetailResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagExpandListResponseVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagSimpleListResponseVo;
import com.machine.client.data.tag.IDataTagClient;
import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.dto.input.DataTagCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateCategoryDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateInputDto;
import com.machine.client.data.tag.dto.output.DataTagDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagListOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
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
public class DataTagBusinessImpl implements IDataTagBusiness {

    @Autowired
    private IDataTagClient tagClient;

    @Autowired
    private IDataTagCategoryClient tagCategoryClient;

    @Autowired
    private IIamUserClient userClient;

    @Override
    public String create(DataTagCreateRequestVo request) {
        DataTagCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagCreateInputDto.class);
        return tagClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        tagClient.delete(request);
    }

    @Override
    public void update(DataTagUpdateRequestVo request) {
        DataTagUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagUpdateInputDto.class);
        tagClient.update(inputDto);
    }

    @Override
    public void updateCategory(DataTagUpdateCategoryRequestVo request) {
        DataTagUpdateCategoryDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagUpdateCategoryDto.class);
        tagClient.updateCategory(inputDto);
    }

    @Override
    public DataTagDetailResponseVo detail(IdRequest request) {
        DataTagDetailOutputDto outputDto = tagClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        DataTagDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataTagDetailResponseVo.class);
        
        // 填充创建人和修改人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());

        return responseVo;
    }

    @Override
    public PageResponse<DataTagSimpleListResponseVo> pageSimple(DataTagQueryPageRequestVo request) {
        DataTagQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagQueryPageInputDto.class);
        
        PageResponse<DataTagListOutputDto> pageOutputDto = tagClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }

        List<DataTagSimpleListResponseVo> responseVoList = JSONUtil.toList(
                JSONUtil.toJsonStr(pageOutputDto.getRecords()), DataTagSimpleListResponseVo.class);

        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), responseVoList);
    }

    @Override
    public PageResponse<DataTagExpandListResponseVo> pageExpand(DataTagQueryPageRequestVo request) {
        DataTagQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagQueryPageInputDto.class);
        
        PageResponse<DataTagListOutputDto> pageOutputDto = tagClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }

        List<DataTagExpandListResponseVo> responseVoList = JSONUtil.toList(
                JSONUtil.toJsonStr(pageOutputDto.getRecords()), DataTagExpandListResponseVo.class);

        // 填充分类名称
        Set<String> categoryIdSet = responseVoList.stream()
                .map(DataTagExpandListResponseVo::getCategoryId)
                .collect(Collectors.toSet());
        
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            Map<String, String> categoryNameMap = categoryIdSet.stream()
                    .collect(Collectors.toMap(
                            categoryId -> categoryId,
                            categoryId -> {
                                try {
                                    DataTagCategoryDetailOutputDto categoryDetail = tagCategoryClient.detail(new IdRequest(categoryId));
                                    return categoryDetail != null ? categoryDetail.getName() : "";
                                } catch (Exception e) {
                                    log.warn("获取分类名称失败，categoryId={}", categoryId, e);
                                    return "";
                                }
                            }
                    ));
            
            for (DataTagExpandListResponseVo responseVo : responseVoList) {
                responseVo.setCategoryName(categoryNameMap.get(responseVo.getCategoryId()));
            }
        }

        // 填充创建人和修改人信息
        Set<String> userIdSet = responseVoList.stream()
                .map(DataTagExpandListResponseVo::getCreateBy)
                .collect(Collectors.toSet());
        userIdSet.addAll(responseVoList.stream()
                .map(DataTagExpandListResponseVo::getUpdateBy)
                .collect(Collectors.toSet()));
        
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataTagExpandListResponseVo responseVo : responseVoList) {
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), responseVoList);
    }
}
