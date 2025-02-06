package com.machine.app.manage.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.shop.business.IDataShopLabelBusiness;
import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelDetailResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelExpandListResponseVo;
import com.machine.app.manage.data.shop.controller.vo.response.ShopLabelSimpleListResponseVo;
import com.machine.client.data.label.ILabelOptionClient;
import com.machine.client.data.label.dto.input.DataLabelOptionCreateInputDto;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryPageInputDto;
import com.machine.client.data.label.dto.input.DataLabelOptionUpdateInputDto;
import com.machine.client.data.label.dto.input.DataLabelOptionUpdateStatusInputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.shop.IShopLabelOptionRelationClient;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.Shop.SHOP_LABEL_ID;

@Slf4j
@Component
public class DataShopLabelBusinessImpl implements IDataShopLabelBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private ILabelOptionClient labelOptionClient;

    @Autowired
    private IShopLabelOptionRelationClient shopLabelOptionRelationClient;

    @Override
    public String create(ShopLabelCreateRequestVo request) {
        DataLabelOptionCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataLabelOptionCreateInputDto.class);
        inputDto.setLabelId(SHOP_LABEL_ID);
        return labelOptionClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        DataLabelOptionUpdateStatusInputDto inputDto = new DataLabelOptionUpdateStatusInputDto();
        inputDto.setId(request.getId());
        inputDto.setStatus(StatusEnum.DISABLE);
        labelOptionClient.updateStatus(inputDto);
        labelOptionClient.delete(request);
    }

    @Override
    public void update(ShopLabelUpdateRequestVo request) {
        DataLabelOptionUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataLabelOptionUpdateInputDto.class);
        labelOptionClient.update(inputDto);
    }

    @Override
    public void updateStatus(ShopLabelUpdateStatusRequestVo request) {
        DataLabelOptionUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataLabelOptionUpdateStatusInputDto.class);
        labelOptionClient.updateStatus(inputDto);
    }

    @Override
    public ShopLabelDetailResponseVo detail(IdRequest request) {
        DataLabelOptionDetailOutputDto outputDto = labelOptionClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        //填充修改人创建人信息
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

        ShopLabelDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ShopLabelDetailResponseVo.class);
        responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        return responseVo;
    }

    @Override
    public PageResponse<ShopLabelSimpleListResponseVo> pageSimple(ShopLabelQueryPageSimpleRequestVo request) {
        DataLabelOptionQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataLabelOptionQueryPageInputDto.class);
        inputDto.setLabelId(SHOP_LABEL_ID);
        PageResponse<DataLabelOptionListOutputDto> page = labelOptionClient.page(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), ShopLabelSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<ShopLabelExpandListResponseVo> pageExpand(ShopLabelQueryPageExpandRequestVo request) {
        DataLabelOptionQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataLabelOptionQueryPageInputDto.class);
        inputDto.setLabelId(SHOP_LABEL_ID);
        PageResponse<DataLabelOptionListOutputDto> pageOutput = labelOptionClient.page(inputDto);
        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //序列化
        PageResponse<ShopLabelExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), ShopLabelExpandListResponseVo.class));

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(ShopLabelExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(ShopLabelExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, UserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (ShopLabelExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        //门店数量
        Set<String> labelOptionIdSet = pageResponse.getRecords().stream().map(ShopLabelExpandListResponseVo::getId).collect(Collectors.toSet());
        Map<String, Long> countMap = shopLabelOptionRelationClient.countShopByLabelOptionIds(new IdSetRequest(labelOptionIdSet));
        if (CollectionUtil.isNotEmpty(countMap)) {
            for (ShopLabelExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setShopNumber(countMap.get(vo.getId()));
            }
        }

        return pageResponse;
    }
}
