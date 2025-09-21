package com.machine.app.manage.data.brand.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.brand.business.IDataBrandBusiness;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandCreateRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandQueryPageRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandUpdateRequestVo;
import com.machine.app.manage.data.brand.controller.vo.request.DataBrandUpdateStatusRequestVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandDetailResponseVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandExpandListResponseVo;
import com.machine.app.manage.data.brand.controller.vo.response.DataBrandSimpleListResponseVo;
import com.machine.client.data.brand.IDataBrandClient;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
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
public class DataBrandBusinessImpl implements IDataBrandBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataBrandClient brandClient;

    @Override
    public String create(DataBrandCreateRequestVo request) {
        request.setName(request.getName().trim());

        DataBrandCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandCreateInputDto.class);
        return brandClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        brandClient.delete(request);

    }

    @Override
    public void update(DataBrandUpdateRequestVo request) {
        request.setName(request.getName().trim());

        DataBrandUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandUpdateInputDto.class);
        brandClient.update(inputDto);
    }

    @Override
    public void updateStatus(DataBrandUpdateStatusRequestVo request) {
        DataBrandUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandUpdateStatusInputDto.class);
        brandClient.updateStatus(inputDto);
    }

    @Override
    public DataBrandDetailResponseVo detail(IdRequest request) {
        DataBrandDetailOutputDto outputDto = brandClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        DataBrandDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataBrandDetailResponseVo.class);

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
    public PageResponse<DataBrandSimpleListResponseVo> pageSimple(DataBrandQueryPageRequestVo request) {
        DataBrandQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandQueryPageInputDto.class);
        PageResponse<DataBrandListOutputDto> page = brandClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataBrandSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<DataBrandExpandListResponseVo> pageExpand(DataBrandQueryPageRequestVo request) {
        //查询分页数据
        DataBrandQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandQueryPageInputDto.class);
        PageResponse<DataBrandListOutputDto> pageOutput = brandClient.page(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        Set<String> materialIdSet = pageOutput.getRecords().stream()
                .map(DataBrandListOutputDto::getLogoMaterialId)
                .filter(materialId -> materialId != null && !materialId.trim().isEmpty())
                .collect(Collectors.toSet());

        //转化为返回数据
        PageResponse<DataBrandExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataBrandExpandListResponseVo.class));

        { //创建人、修改文姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(DataBrandExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(DataBrandExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (DataBrandExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }
        return pageResponse;
    }
}
