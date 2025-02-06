package com.machine.app.openapi.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.shop.business.IOpenApiShopLabelBusiness;
import com.machine.app.openapi.data.shop.controller.vo.request.OpenApiShopLabelOptionIdRequestVo;
import com.machine.app.openapi.data.shop.controller.vo.request.OpenApiShopLabelOptionListSimpleRequestVo;
import com.machine.app.openapi.data.shop.controller.vo.response.OpenApiShopLabelOptionDetailResponseVo;
import com.machine.app.openapi.data.shop.controller.vo.response.OpenApiShopLabelOptionListSimpleResponseVo;
import com.machine.client.data.label.ILabelOptionClient;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryListOffsetInputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.shop.IShopLabelOptionRelationClient;
import com.machine.sdk.common.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.machine.sdk.common.constant.CommonConstant.Shop.SHOP_LABEL_ID;

@Slf4j
@Component
public class OpenApiShopLabelBusinessImpl implements IOpenApiShopLabelBusiness {

    @Autowired
    private ILabelOptionClient labelOptionClient;

    @Autowired
    private IShopLabelOptionRelationClient shopLabelOptionRelationClient;

    @Override
    public OpenApiShopLabelOptionDetailResponseVo detail(OpenApiShopLabelOptionIdRequestVo request) {
        DataLabelOptionDetailOutputDto outputDto = labelOptionClient.detail(new IdRequest(request.getId()));
        if (null == outputDto) {
            return null;
        }

        OpenApiShopLabelOptionDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiShopLabelOptionDetailResponseVo.class);
        responseVo.setId(outputDto.getId());
        return responseVo;
    }

    @Override
    public List<String> listShopIdByLabelOptionId(OpenApiShopLabelOptionIdRequestVo request) {
        return shopLabelOptionRelationClient.listShopIdByLabelOptionId(new IdRequest(request.getId()));
    }

    @Override
    public List<OpenApiShopLabelOptionListSimpleResponseVo> listSimple(OpenApiShopLabelOptionListSimpleRequestVo request) {
        DataLabelOptionQueryListOffsetInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataLabelOptionQueryListOffsetInputDto.class);
        inputDto.setLabelId(SHOP_LABEL_ID);
        List<DataLabelOptionListOutputDto> outputDtoList = labelOptionClient.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }

        List<OpenApiShopLabelOptionListSimpleResponseVo> responseVoList = new ArrayList<>();
        for (DataLabelOptionListOutputDto outputDto : outputDtoList) {
            OpenApiShopLabelOptionListSimpleResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiShopLabelOptionListSimpleResponseVo.class);
            responseVo.setId(outputDto.getId());
            responseVoList.add(responseVo);
        }
        return responseVoList;
    }
}
