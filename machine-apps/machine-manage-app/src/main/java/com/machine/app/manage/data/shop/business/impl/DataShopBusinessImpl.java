package com.machine.app.manage.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.shop.business.IDataShopBusiness;
import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.*;
import com.machine.client.data.franchisee.IFranchiseeShopRelationClient;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.client.data.label.ILabelOptionClient;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.IShopLabelOptionRelationClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.client.iam.organization.dto.output.IamOrganizationSimpleOutputDto;
import com.machine.sdk.common.envm.iam.OrganizationTypeEnum;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.FoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.ShopFrontPhotoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.Data.ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonConstant.Data.ORGANIZATION_VIRTUAL_NODE;
import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;

@Slf4j
@Component
public class DataShopBusinessImpl implements IDataShopBusiness {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private ILabelOptionClient labelOptionClient;

    @Autowired
    private IFranchiseeShopRelationClient franchiseeShopRelationClient;

    @Autowired
    private IShopLabelOptionRelationClient shopLabelOptionRelationClient;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public void updateStatus(ShopUpdateShopStatusRequestVo request) {
        ShopUpdateShopStatusInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ShopUpdateShopStatusInputDto.class);
        shopClient.updateStatus(inputDto);
    }

    @Override
    public void updateAddress(DataShopUpdateAddressRequestVo request) {
        DataShopUpdateShopBaseInfoInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopBaseInfoInputDto.class);
        shopClient.updateBaseInfo(inputDto);
    }

    @Override
    public void updateCertificate(ShopUpdateCertificateRequestVo request) {
        DataShopDetailOutputDto shopDetailOutputDto = shopClient.getById(new IdRequest(request.getId()));
        if (shopDetailOutputDto == null) {
            return;
        }

        //门店营业执照
        ShopBusinessLicenseDto businessLicense = request.getBusinessLicense();
        if (businessLicense != null) {
            OpenApiShopUpdateShopBusinessLicenseInputDto businessLicenseInputDto = new OpenApiShopUpdateShopBusinessLicenseInputDto();
            businessLicenseInputDto.setId(shopDetailOutputDto.getId());
            businessLicenseInputDto.setPersistenceStatus(DataPersistenceStatusEnum.PERMANENT);
            businessLicenseInputDto.setBusinessLicense(businessLicense);
            shopClient.updateShopBusinessLicense(businessLicenseInputDto);
        }

        //食品经营许可证
        FoodBusinessLicenseDto foodBusinessLicense = request.getFoodBusinessLicense();
        if (foodBusinessLicense != null) {
            OpenApiShopUpdateFoodBusinessLicenseInputDto foodLicenseInputDto = new OpenApiShopUpdateFoodBusinessLicenseInputDto();
            foodLicenseInputDto.setId(shopDetailOutputDto.getId());
            foodLicenseInputDto.setPersistenceStatus(DataPersistenceStatusEnum.PERMANENT);
            foodLicenseInputDto.setBusinessLicense(foodBusinessLicense);
            shopClient.updateFoodBusinessLicense(foodLicenseInputDto);
        }

        //消杀合同
        DisinfectingContractDto disinfectingContractDto = request.getDisinfectingContractDto();
        if (disinfectingContractDto != null) {
            OpenApiShopUpdateDisinfectingContractInputDto disinfectingContractInputDto = new OpenApiShopUpdateDisinfectingContractInputDto();
            disinfectingContractInputDto.setId(shopDetailOutputDto.getId());
            disinfectingContractInputDto.setPersistenceStatus(DataPersistenceStatusEnum.PERMANENT);
            disinfectingContractInputDto.setDisinfectingContractDto(disinfectingContractDto);
            shopClient.updateDisinfectingContract(disinfectingContractInputDto);
        }

        //门头照
        ShopFrontPhotoDto shopFrontPhoto = request.getShopFrontPhoto();
        if (shopFrontPhoto != null) {
            OpenApiShopUpdateShopFrontPhotoInputDto frontPhotoInputDto = new OpenApiShopUpdateShopFrontPhotoInputDto();
            frontPhotoInputDto.setId(shopDetailOutputDto.getId());
            frontPhotoInputDto.setPersistenceStatus(DataPersistenceStatusEnum.PERMANENT);
            frontPhotoInputDto.setShopFrontPhoto(shopFrontPhoto);
            shopClient.updateShopFrontPhoto(frontPhotoInputDto);
        }

    }

    @Override
    public void updateBase(ShopUpdateShopBaseInfoRequestVo request) {
        DataShopUpdateShopBaseInfoInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopBaseInfoInputDto.class);
        shopClient.updateBaseInfo(inputDto);
    }

    @Override
    public void updateLabelOption(ShopUpdateShopLabelOptionRequestVo request) {
        ShopUpdateShopLabelOptionInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ShopUpdateShopLabelOptionInputDto.class);
        shopClient.updateLabelOption(inputDto);
    }

    @Override
    public void batchUpdateLabelOption(ShopBatchUpdateShopLabelOptionRequestVo request) {
        ShopBatchUpdateShopLabelOptionInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ShopBatchUpdateShopLabelOptionInputDto.class);
        shopClient.batchUpdateLabelOption(inputDto);
    }

    @Override
    public void bindOrganization(ShopBindOrganizationRequestVo request) {
        Set<String> shopIdSet = request.getShopIdSet();
        if (CollectionUtil.isEmpty(shopIdSet)) {
            return;
        }

        List<DataShopDetailOutputDto> outputDtoList = shopClient.listByIdSet(new IdSetRequest(shopIdSet));
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return;
        }

        ShopBindOrganizationInputDto inputDto = new ShopBindOrganizationInputDto();
        inputDto.setOrganizationId(request.getOrganizationId());
        inputDto.setShopCodeSet(outputDtoList.stream().map(DataShopDetailOutputDto::getCode).collect(Collectors.toSet()));
        shopOrganizationRelationClient.bindOrganization(inputDto);
    }

    @Override
    public ShopDetailResponseVo detail(ShopIdRequestVo request) {
        DataShopDetailOutputDto outputDto = shopClient.getById(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        return assembleDetailResponse(outputDto);
    }

    @Override
    public PageResponse<ShopSimpleListResponseVo> pageSimpled(ShopQueryPageSimpleRequestVo request) {
        //组装ShopId集合
        Set<String> finallyQueryShopIdSet = new HashSet<>();
        boolean compute = assembleFinallyQueryShopIdSet(finallyQueryShopIdSet, request.getOrganizationIdSet(),
                request.getLabelOptionIdSet());

        if (compute && CollectionUtil.isEmpty(finallyQueryShopIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        //分页查询用户信息
        DataShopQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryPageInputDto.class);
        inputDto.setShopIdSet(finallyQueryShopIdSet);
        PageResponse<DataShopListOutputDto> pageOutputDto = shopClient.page(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }

        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), ShopSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<ShopExpandListResponseVo> pageExpand(ShopQueryPageExpandRequestVo request) {
        DataShopQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryPageInputDto.class);

        //组装ShopId集合
        Set<String> finallyQueryShopIdSet = new HashSet<>();
        boolean compute = assembleFinallyQueryShopIdSet(finallyQueryShopIdSet, request.getOrganizationIdSet(),
                request.getLabelOptionIdSet());

        if (compute && CollectionUtil.isEmpty(finallyQueryShopIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        //分页查询门店信息
        inputDto.setShopIdSet(finallyQueryShopIdSet);
        PageResponse<DataShopListOutputDto> pageOutputDto = shopClient.page(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }

        PageResponse<ShopExpandListResponseVo> pageResponse =
                new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(),
                        JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), ShopExpandListResponseVo.class));

        Set<String> shopIdSet = pageResponse.getRecords().stream().map(ShopExpandListResponseVo::getId).collect(Collectors.toSet());

        {//关联组织信息
            List<ShopOrganizationRelationListOutputDto> outputDtoList = shopOrganizationRelationClient.listByShopIdSet(new IdSetRequest(shopIdSet));
            if (CollectionUtil.isNotEmpty(outputDtoList)) {
                Map<String, List<String>> shopIdOrganizationIdMap =
                        outputDtoList.stream().collect(Collectors.groupingBy(ShopOrganizationRelationListOutputDto::getShopId,
                                Collectors.mapping(ShopOrganizationRelationListOutputDto::getOrganizationId, Collectors.toList())));

                Set<String> organizationIdSet = outputDtoList.stream().map(
                        ShopOrganizationRelationListOutputDto::getOrganizationId).collect(Collectors.toSet());
                Map<String, IamOrganizationSimpleOutputDto> organizationMap = organizationCache.mapByIdSet(organizationIdSet);
                for (ShopExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    String shopId = responseVo.getId();
                    List<String> organizationIdList = shopIdOrganizationIdMap.get(shopId);
                    if (CollectionUtil.isNotEmpty(organizationIdList)) {
                        List<ShopExpandListResponseVo.OrganizationDto> bindOrganizationList = new ArrayList<>();
                        for (String organizationId : organizationIdList) {
                            ShopExpandListResponseVo.OrganizationDto organizationDto =
                                    JSONUtil.toBean(JSONUtil.toJsonStr(organizationMap.get(organizationId)),
                                            ShopExpandListResponseVo.OrganizationDto.class);
                            bindOrganizationList.add(organizationDto);
                        }
                        responseVo.setBindOrganizationList(bindOrganizationList);
                    }
                }
            }
        }

        {//门店标签
            Map<String, List<String>> shopIdLabelOptionIdMap = shopLabelOptionRelationClient.mapLabelOptionIdByShopIds(new IdSetRequest(shopIdSet));
            if (CollectionUtil.isNotEmpty(shopIdLabelOptionIdMap)) {
                Set<String> labelOptionIdSet = shopIdLabelOptionIdMap.values().stream()
                        .flatMap(List::stream).collect(Collectors.toSet());
                Map<String, DataLabelOptionListOutputDto> outputDtoMap = labelOptionClient.mapByIdSet(new IdSetRequest(labelOptionIdSet));

                for (ShopExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    List<String> labelOptionIdList = shopIdLabelOptionIdMap.get(responseVo.getId());
                    if (CollectionUtil.isNotEmpty(labelOptionIdList)) {
                        List<ShopDetailResponseVo.LabelOption> labelOptionList = new ArrayList<>();
                        for (String labelOptionId : labelOptionIdList) {
                            DataLabelOptionListOutputDto dto = outputDtoMap.get(labelOptionId);
                            ShopDetailResponseVo.LabelOption labelOption = new ShopDetailResponseVo.LabelOption();
                            labelOption.setId(dto.getId());
                            labelOption.setLabelId(dto.getLabelId());
                            labelOption.setCode(dto.getCode());
                            labelOption.setName(dto.getName());
                            labelOptionList.add(labelOption);
                        }
                        responseVo.setLabelOptionList(labelOptionList);
                    }
                }
            }
        }

        {//地址信息
            Map<String, AddressInfoDto> addressInfoDtoMap = shopClient.mapAddressInfo(new IdSetRequest(shopIdSet));
            if (CollectionUtil.isNotEmpty(addressInfoDtoMap)) {
                for (ShopExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    responseVo.setAddressInfo(addressInfoDtoMap.get(responseVo.getId()));
                }
            }
        }
        return pageResponse;
    }

    private void extractedShopIdByOrganizationIdSet(Set<String> organizationIdSet, Set<String> finallyqueryShopIdSet) {
        for (OrganizationTypeEnum type : OrganizationTypeEnum.values()) {
            if (organizationIdSet.contains(type.getName() + SEPARATOR_COLON + ORGANIZATION_VIRTUAL_NODE)) {
                //未分配节点
                List<String> shopIdList = shopClient.listNotBindOrganization(new DataShopNotBindOrganizationInputDto(type));
                if (CollectionUtil.isNotEmpty(shopIdList)) {
                    finallyqueryShopIdSet.addAll(shopIdList);
                }
                break;
            }
        }

        //递归查询组织Id
        Set<String> recursionOrganizationIdSet = organizationCache.recursionListSubIds(organizationIdSet);
        if (CollectionUtil.isNotEmpty(recursionOrganizationIdSet)) {
            List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(recursionOrganizationIdSet));
            finallyqueryShopIdSet.addAll(shopIdList);
        }
    }

    private ShopDetailResponseVo assembleDetailResponse(DataShopDetailOutputDto outputDto) {
        ShopDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ShopDetailResponseVo.class);

        //加盟商ID
        DataFranchiseeShopRelationOutputDto relationOutputDto =
                franchiseeShopRelationClient.getByShopId(new IdRequest(outputDto.getId()));
        if (relationOutputDto != null) {
            responseVo.setFranchiseeId(relationOutputDto.getFranchiseeId());
        }

        //门店关联组织
        List<ShopOrganizationRelationListOutputDto> relationListOutputDtoList =
                shopOrganizationRelationClient.listByShopId(new IdRequest(responseVo.getId()));
        if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
            List<ShopDetailResponseVo.Organization> organizationList = new ArrayList<>();
            for (ShopOrganizationRelationListOutputDto dto : relationListOutputDtoList) {
                ShopDetailResponseVo.Organization organization = new ShopDetailResponseVo.Organization();
                organization.setId(dto.getOrganizationId());
                organization.setType(dto.getOrganizationType());
                organization.setSort(dto.getSort());
                organizationList.add(organization);
            }
            responseVo.setOrganizationList(organizationList);
        }

        {//地址信息
            AddressInfoDto addressInfo = shopClient.getAddressInfo(new IdRequest(outputDto.getId()));
            responseVo.setAddressInfo(addressInfo);
        }

        {//门店标签
            List<String> labelOptionIdList = shopLabelOptionRelationClient.listLabelOptionIdByShopId(new IdRequest(outputDto.getId()));
            if (CollectionUtil.isNotEmpty(labelOptionIdList)) {
                List<DataLabelOptionListOutputDto> outputDtoList = labelOptionClient.listByIdSet(new IdSetRequest(new HashSet<>(labelOptionIdList)));
                List<ShopDetailResponseVo.LabelOption> labelOptionList = new ArrayList<>();
                for (DataLabelOptionListOutputDto dto : outputDtoList) {
                    ShopDetailResponseVo.LabelOption labelOption = new ShopDetailResponseVo.LabelOption();
                    labelOption.setId(dto.getId());
                    labelOption.setLabelId(dto.getLabelId());
                    labelOption.setCode(dto.getCode());
                    labelOption.setName(dto.getName());
                    labelOptionList.add(labelOption);
                }
                responseVo.setLabelOptionList(labelOptionList);
            }
        }

        {
            //门店营业执照
            ShopBusinessLicenseOutputDto businessLicenseOutputDto = shopClient.getShopBusinessLicense(new IdRequest(responseVo.getId()));
            responseVo.setBusinessLicense(JSONUtil.toBean(JSONUtil.toJsonStr(businessLicenseOutputDto), ShopBusinessLicenseResponseVo.class));

            //食品经营许可证
            FoodBusinessLicenseOutputDto foodBusinessLicenseOutputDto = shopClient.getFoodBusinessLicense(new IdRequest(responseVo.getId()));
            responseVo.setFoodBusinessLicense(JSONUtil.toBean(JSONUtil.toJsonStr(foodBusinessLicenseOutputDto), ShopFoodBusinessLicenseResponseVo.class));

            //消杀合同
            OpenApiDisinfectingContractOutputDto disinfectingContractOutputDto = shopClient.getDisinfectingContract(new IdRequest(responseVo.getId()));
            responseVo.setDisinfectingContract(JSONUtil.toBean(JSONUtil.toJsonStr(disinfectingContractOutputDto), ShopDisinfectingContractResponseVo.class));

            //门头照
            OpenApiShopFrontPhotoOutputDto frontPhotoOutputDto = shopClient.getShopFrontPhoto(new IdRequest(responseVo.getId()));
            responseVo.setFrontPhoto(JSONUtil.toBean(JSONUtil.toJsonStr(frontPhotoOutputDto), ShopFrontPhotoResponseVo.class));
        }
        return responseVo;
    }

    private boolean assembleFinallyQueryShopIdSet(Set<String> finallyQueryShopIdSet,
                                                  Set<String> organizationIdSet,
                                                  Set<String> labelOptionIdSet) {
        boolean compute = false;

        //组装ShopId集合(组织)
        if (CollectionUtil.isNotEmpty(organizationIdSet)) {
            boolean containRootId = false;
            for (OrganizationTypeEnum type : OrganizationTypeEnum.values()) {
                if (organizationIdSet.contains(type.getName().toLowerCase())) {
                    //包含根组织直接返回
                    containRootId = true;
                    break;
                }
            }

            if (organizationIdSet.contains(ORGANIZATION_ROOT_PARENT_ID)) {
                containRootId = true;
            }

            if (!containRootId) {
                compute = true;
                extractedShopIdByOrganizationIdSet(organizationIdSet, finallyQueryShopIdSet);
            }
        }

        //组装ShopId集合(门店标签)
        if ((!compute || CollectionUtil.isNotEmpty(finallyQueryShopIdSet))
                && CollectionUtil.isNotEmpty(labelOptionIdSet)) {
            compute = true;
            List<String> shopIdList = shopLabelOptionRelationClient.listShopIdByLabelOptionIds(new IdSetRequest(labelOptionIdSet));
            if (CollectionUtil.isEmpty(shopIdList)) {
                finallyQueryShopIdSet.clear();
            } else {
                //取交集
                if (CollectionUtil.isEmpty(finallyQueryShopIdSet)) {
                    finallyQueryShopIdSet.addAll(shopIdList);
                } else {
                    finallyQueryShopIdSet.retainAll(shopIdList);
                }
            }
        }
        return compute;
    }

}
