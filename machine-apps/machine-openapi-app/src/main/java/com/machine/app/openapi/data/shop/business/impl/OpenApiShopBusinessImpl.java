package com.machine.app.openapi.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.openapi.data.shop.business.IOpenApiShopBusiness;
import com.machine.app.openapi.data.shop.controller.vo.request.*;
import com.machine.app.openapi.data.shop.controller.vo.response.*;
import com.machine.client.data.franchisee.IFranchiseeShopRelationClient;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.IShopLabelOptionRelationClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.client.iam.organization.dto.output.IamOrganizationTreeSimpleOutputDto;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OpenApiShopBusinessImpl implements IOpenApiShopBusiness {

    @Autowired
    private RedisCacheIamOrganization redisCacheIamOrganization;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IFranchiseeShopRelationClient franchiseeShopRelationClient;

    @Autowired
    private IShopLabelOptionRelationClient shopLabelOptionRelationClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public String create(OpenApiShopCreateRequestVo request) {
        DataShopCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopCreateInputDto.class);
        return shopClient.create(inputDto);
    }

    @Override
    public void updateStatus(OpenApiShopUpdateShopStatusRequestVo request) {
        ShopUpdateShopStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ShopUpdateShopStatusInputDto.class);
        shopClient.updateStatus(inputDto);
    }

    @Override
    public void updateAddress(OpenApiShopUpdateAddressRequestVo request) {
        OpenApiShopUpdateAddressInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), OpenApiShopUpdateAddressInputDto.class);
        shopClient.updateAddress(inputDto);
    }

    @Override
    public void updateShopBusinessLicense(OpenApiShopUpdateShopBusinessLicenseRequestVo request) {
        OpenApiShopUpdateShopBusinessLicenseInputDto inputDto = JSONUtil.toBean(
                JSONUtil.toJsonStr(request), OpenApiShopUpdateShopBusinessLicenseInputDto.class);
        shopClient.updateShopBusinessLicense(inputDto);
    }

    @Override
    public void updateFoodBusinessLicense(OpenApiShopUpdateFoodBusinessLicenseRequestVo request) {
        OpenApiShopUpdateFoodBusinessLicenseInputDto inputDto = JSONUtil.toBean(
                JSONUtil.toJsonStr(request), OpenApiShopUpdateFoodBusinessLicenseInputDto.class);
        shopClient.updateFoodBusinessLicense(inputDto);
    }

    @Override
    public void updateDisinfectingContract(OpenApiShopUpdateDisinfectingContractRequestVo request) {
        OpenApiShopUpdateDisinfectingContractInputDto inputDto = JSONUtil.toBean(
                JSONUtil.toJsonStr(request), OpenApiShopUpdateDisinfectingContractInputDto.class);
        shopClient.updateDisinfectingContract(inputDto);
    }

    @Override
    public void updateShopFrontPhoto(OpenApiShopUpdateShopFrontPhotoRequestVo request) {
        OpenApiShopUpdateShopFrontPhotoInputDto inputDto = JSONUtil.toBean(
                JSONUtil.toJsonStr(request), OpenApiShopUpdateShopFrontPhotoInputDto.class);
        shopClient.updateShopFrontPhoto(inputDto);
    }

    @Override
    public void updateBase(OpenApiShopUpdateRequestVo request) {
        DataShopUpdateShopBaseInfoInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopBaseInfoInputDto.class);
        shopClient.updateBaseInfo(inputDto);
    }

    @Override
    public String codeById(OpenApiShopIdRequestVo request) {
        DataShopDetailOutputDto outputDto = shopClient.getById(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return outputDto.getCode();
    }

    @Override
    public String idByCode(OpenApiShopCodeRequestVo request) {
        DataShopDetailOutputDto outputDto = shopClient.getByCode(new DataShopCodeInoutDto(request.getCode()));
        if (outputDto == null) {
            return null;
        }
        return outputDto.getId();
    }

    @Override
    public OpenApiShopDetailResponseVo detail(OpenApiShopIdRequestVo request) {
        DataShopDetailOutputDto outputDto = shopClient.getById(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return getOpenApiShopDetailResponseVo(outputDto);
    }

    @Override
    public OpenApiShopBusinessLicenseResponseVo getShopBusinessLicense(OpenApiShopIdRequestVo request) {
        ShopBusinessLicenseOutputDto outputDto = shopClient.getShopBusinessLicense(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiShopBusinessLicenseResponseVo.class);
    }

    @Override
    public OpenApiFoodBusinessLicenseResponseVo getFoodBusinessLicense(OpenApiShopIdRequestVo request) {
        FoodBusinessLicenseOutputDto outputDto = shopClient.getFoodBusinessLicense(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiFoodBusinessLicenseResponseVo.class);
    }

    @Override
    public OpenApiDisinfectingContractResponseVo getDisinfectingContract(OpenApiShopIdRequestVo request) {
        OpenApiDisinfectingContractOutputDto outputDto = shopClient.getDisinfectingContract(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiDisinfectingContractResponseVo.class);
    }

    @Override
    public OpenApiShopFrontPhotoResponseVo getShopFrontPhoto(OpenApiShopIdRequestVo request) {
        OpenApiShopFrontPhotoOutputDto outputDto = shopClient.getShopFrontPhoto(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiShopFrontPhotoResponseVo.class);
    }

    @Override
    public List<String> queryShopLabelOptionIds(OpenApiShopIdRequestVo request) {
        return shopLabelOptionRelationClient.listLabelOptionIdByShopId(new IdRequest(request.getId()));
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


    private OpenApiShopDetailResponseVo getOpenApiShopDetailResponseVo(DataShopDetailOutputDto outputDto) {
        OpenApiShopDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), OpenApiShopDetailResponseVo.class);

        {//加盟商ID
            DataFranchiseeShopRelationOutputDto relationOutputDto = franchiseeShopRelationClient.getByShopId(new IdRequest(outputDto.getId()));
            if (relationOutputDto != null) {
                responseVo.setFranchiseeId(relationOutputDto.getFranchiseeId());
            }
        }

        {//门店关联组织
            List<ShopOrganizationRelationListOutputDto> relationListOutputDtoList = shopOrganizationRelationClient
                    .listByShopId(new IdRequest(responseVo.getId()));
            if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
                List<OpenApiShopDetailResponseVo.Organization> organizationList = new ArrayList<>();
                for (ShopOrganizationRelationListOutputDto dto : relationListOutputDtoList) {
                    OpenApiShopDetailResponseVo.Organization organization = new OpenApiShopDetailResponseVo.Organization();
                    organization.setId(dto.getOrganizationId());
                    organization.setType(dto.getOrganizationType());
                    organization.setSort(dto.getSort());
                    organizationList.add(organization);
                }
                responseVo.setOrganizationList(organizationList);
            }
        }

        {//分公司、大区
            List<OpenApiShopDetailResponseVo.Organization> organizationList = responseVo.getOrganizationList();
            if (CollectionUtil.isNotEmpty(organizationList)) {
                for (OpenApiShopDetailResponseVo.Organization organization : organizationList) {
                    if (OrganizationTypeEnum.OPERATIONS == organization.getType()) {
                        //组织树
                        IamOrganizationTreeSimpleOutputDto treeOutputDto =
                                redisCacheIamOrganization.treeAllSimple(organization.getType());

                        //找到指定的节点
                        IamOrganizationTreeSimpleOutputDto treeNode = TreeUtil.findNode(treeOutputDto, organization.getId());

                        //获取指定组织的所有父组织列表（list元素第一个是当前组织，最后一个是根组织，从左至右组织层级递增）
                        List<IamOrganizationTreeSimpleOutputDto> parentList = new ArrayList<>();
                        do {
                            parentList.add(treeNode);
                            treeNode = TreeUtil.findNode(treeOutputDto, treeNode.getParentId());
                        } while (null != treeNode);

                        parentList = parentList.reversed();
                        if (parentList.size() > 2) {
                            responseVo.setRegionalName(parentList.get(1).getName());
                            responseVo.setBranchName(parentList.get(2).getName());
                        } else if (parentList.size() > 1) {
                            responseVo.setRegionalName(parentList.get(1).getName());
                        }
                    }
                }
            }
        }

        {//地址信息
            AddressInfoDto addressInfo = shopClient.getAddressInfo(new IdRequest(outputDto.getId()));
            responseVo.setAddressInfo(addressInfo);
        }

        return responseVo;
    }
}
