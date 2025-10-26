package com.machine.app.openapi.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.shop.business.IOpenApiShopBusiness;
import com.machine.app.openapi.data.shop.controller.vo.request.*;
import com.machine.app.openapi.data.shop.controller.vo.response.*;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.client.data.label.IDataLabelOptionClient;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.IDataShopLabelOptionRelationClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.envm.data.DataCountryEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.data.RedisCacheDataArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class OpenApiShopBusinessImpl implements IOpenApiShopBusiness {

    @Autowired
    private RedisCacheDataArea areaCache;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IDataLabelOptionClient labelOptionClient;

    @Autowired
    private IDataShopLabelOptionRelationClient shopLabelOptionRelationClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public String idByCode(OpenApiShopCodeRequestVo request) {
       return shopClient.getIdByCode(new DataShopCodeInoutDto(request.getCode()));
    }

    @Override
    public Map<String, String> idByCodeSet(OpenApiShopCodeSetRequestVo request) {
        return shopClient.idByCodeSet(new OpenApiShopCodeSetInputDto(request.getCodeSet()));
    }

    @Override
    public OpenApiShopDetailResponseVo detail(OpenApiShopIdRequestVo request) {
        DataShopDetailOutputDto outputDto = shopClient.getById(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        OpenApiShopDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiShopDetailResponseVo.class);


        {//门店关联组织
            List<DataShopOrganizationRelationListOutputDto> relationListOutputDtoList = shopOrganizationRelationClient
                    .listByShopId(new IdRequest(responseVo.getId()));
            if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
                List<OpenApiShopDetailResponseVo.Organization> organizationList = new ArrayList<>();
                for (DataShopOrganizationRelationListOutputDto dto : relationListOutputDtoList) {
                    OpenApiShopDetailResponseVo.Organization organization = new OpenApiShopDetailResponseVo.Organization();
                    organization.setId(dto.getOrganizationId());
                    organization.setType(dto.getOrganizationType());
                    organization.setSort(dto.getSort());
                    organizationList.add(organization);
                }
                responseVo.setOrganizationList(organizationList);
            }
        }

        {//地址信息
            String countryCode = outputDto.getCountryCode();
            String provinceCode = outputDto.getProvinceCode();
            if (StrUtil.isNotBlank(countryCode) || StrUtil.isNotBlank(provinceCode)) {
                DataAreaTreeOutputDto areaTree = areaCache.tree(countryCode);

                AddressInfoDto addressInfo = new AddressInfoDto();
                addressInfo.setCountry(DataCountryEnum.valueOf(countryCode).getMessage());
                addressInfo.setCountryCode(countryCode);
                addressInfo.setCity(TreeUtil.findNode(areaTree, countryCode).getName());

                addressInfo.setProvinceCode(provinceCode);
                addressInfo.setProvince(TreeUtil.findNode(areaTree, provinceCode).getName());

                addressInfo.setCityCode(outputDto.getCityCode());
                addressInfo.setCity(TreeUtil.findNode(areaTree, outputDto.getCityCode()).getName());

                addressInfo.setAreaCode(outputDto.getAreaCode());
                addressInfo.setArea(TreeUtil.findNode(areaTree, outputDto.getAreaCode()).getName());

                addressInfo.setAddress(outputDto.getAddress());
                responseVo.setAddressInfo(addressInfo);
            }
        }

        {//门店标签
            List<String> labelOptionIdList = shopLabelOptionRelationClient.listLabelOptionIdByShopId(new IdRequest(outputDto.getId()));
            if (CollectionUtil.isNotEmpty(labelOptionIdList)) {
                List<DataLabelOptionListOutputDto> outputDtoList = labelOptionClient.listByIdSet(new IdSetRequest(new HashSet<>(labelOptionIdList)));
                List<OpenApiShopDetailResponseVo.LabelOption> labelOptionList = new ArrayList<>();
                for (DataLabelOptionListOutputDto dto : outputDtoList) {
                    OpenApiShopDetailResponseVo.LabelOption labelOption = new OpenApiShopDetailResponseVo.LabelOption();
                    labelOption.setId(dto.getId());
                    labelOption.setLabelId(dto.getLabelId());
                    labelOption.setCode(dto.getCode());
                    labelOption.setName(dto.getName());
                    labelOptionList.add(labelOption);
                }
                responseVo.setLabelOptionList(labelOptionList);
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
    public List<OpenApiShopListSimpleResponseVo> listSimple(OpenApiShopListSimpleRequestVo request) {
        DataShopQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryListOffsetInputDto.class);
        List<DataShopListOutputDto> outputDtoList = shopClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), OpenApiShopListSimpleResponseVo.class);
    }

}
