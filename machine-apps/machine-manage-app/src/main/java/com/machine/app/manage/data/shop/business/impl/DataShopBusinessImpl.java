package com.machine.app.manage.data.shop.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.shop.business.IDataShopBusiness;
import com.machine.app.manage.data.shop.controller.vo.request.*;
import com.machine.app.manage.data.shop.controller.vo.response.*;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.client.data.label.IDataLabelOptionClient;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.data.shop.IDataShopLabelOptionRelationClient;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.*;
import com.machine.client.iam.organization.dto.output.IamOrganizationSimpleOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.envm.data.DataCountryEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.model.dto.data.AddressInfoDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopDisinfectingContractDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFoodBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopBusinessLicenseDto;
import com.machine.sdk.common.model.dto.data.certificate.shop.DataShopFrontPhotoDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheDataArea;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_ROOT_PARENT_ID;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.ORGANIZATION_VIRTUAL_NODE;

@Slf4j
@Component
public class DataShopBusinessImpl implements IDataShopBusiness {

    @Autowired
    private RedisCacheDataArea areaCache;

    @Autowired
    private RedisCacheIamOrganization organizationCache;

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
    public String create(DataShopCreateRequestVo request) {
        //去除前后空格
        request.setName(request.getName().trim());

        DataShopCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopCreateInputDto.class);
        return shopClient.create(inputDto);
    }

    @Override
    public void update(DataShopUpdateRequestVo request) {
        //去除前后空格
        request.setName(request.getName().trim());

        DataShopUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateInputDto.class);
        shopClient.update(inputDto);
    }

    @Override
    public void updateBusinessStatus(DataShopUpdateShopBusinessStatusRequestVo request) {
        DataShopUpdateShopBusinessStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopBusinessStatusInputDto.class);
        shopClient.updateBusinessStatus(inputDto);
    }

    @Override
    public void updateOperationStatus(DataShopUpdateShopOperationStatusRequestVo request) {
        DataShopUpdateShopOperationStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopOperationStatusInputDto.class);
        shopClient.updateOperationStatus(inputDto);
    }

    @Override
    public void updatePhysicalStatus(DataShopUpdateShopPhysicalStatusRequestVo request) {
        DataShopUpdateShopPhysicalStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopPhysicalStatusInputDto.class);
        shopClient.updatePhysicalStatus(inputDto);
    }

    @Override
    public void updateCertificate(DataShopUpdateCertificateRequestVo request) {
        DataShopDetailOutputDto shopDetailOutputDto = shopClient.getById(new IdRequest(request.getId()));
        if (shopDetailOutputDto == null) {
            return;
        }

        //门店营业执照
        DataShopBusinessLicenseDto businessLicense = request.getBusinessLicense();
        if (businessLicense != null) {
            DataShopUpdateShopBusinessLicenseInputDto businessLicenseInputDto = new DataShopUpdateShopBusinessLicenseInputDto();
            businessLicenseInputDto.setId(shopDetailOutputDto.getId());
            businessLicenseInputDto.setPersistenceStatus(SystemStorageTypeEnum.PERMANENT);
            businessLicenseInputDto.setBusinessLicense(businessLicense);
            shopClient.updateShopBusinessLicense(businessLicenseInputDto);
        }

        //食品经营许可证
        DataShopFoodBusinessLicenseDto foodBusinessLicense = request.getFoodBusinessLicense();
        if (foodBusinessLicense != null) {
            DataShopUpdateFoodBusinessLicenseInputDto foodLicenseInputDto = new DataShopUpdateFoodBusinessLicenseInputDto();
            foodLicenseInputDto.setId(shopDetailOutputDto.getId());
            foodLicenseInputDto.setPersistenceStatus(SystemStorageTypeEnum.PERMANENT);
            foodLicenseInputDto.setFoodBusinessLicense(foodBusinessLicense);
            shopClient.updateFoodBusinessLicense(foodLicenseInputDto);
        }

        //消杀合同
        DataShopDisinfectingContractDto disinfectingContractDto = request.getDisinfectingContractDto();
        if (disinfectingContractDto != null) {
            DataShopUpdateDisinfectingContractInputDto disinfectingContractInputDto = new DataShopUpdateDisinfectingContractInputDto();
            disinfectingContractInputDto.setId(shopDetailOutputDto.getId());
            disinfectingContractInputDto.setPersistenceStatus(SystemStorageTypeEnum.PERMANENT);
            disinfectingContractInputDto.setDisinfectingContractDto(disinfectingContractDto);
            shopClient.updateDisinfectingContract(disinfectingContractInputDto);
        }

        //门头照
        DataShopFrontPhotoDto shopFrontPhoto = request.getShopFrontPhoto();
        if (shopFrontPhoto != null) {
            DataShopUpdateShopFrontPhotoInputDto frontPhotoInputDto = new DataShopUpdateShopFrontPhotoInputDto();
            frontPhotoInputDto.setId(shopDetailOutputDto.getId());
            frontPhotoInputDto.setPersistenceStatus(SystemStorageTypeEnum.PERMANENT);
            frontPhotoInputDto.setShopFrontPhoto(shopFrontPhoto);
            shopClient.updateShopFrontPhoto(frontPhotoInputDto);
        }
    }

    @Override
    public void updateLabelOption(DataShopUpdateShopLabelOptionRequestVo request) {
        DataShopUpdateShopLabelOptionInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopUpdateShopLabelOptionInputDto.class);
        shopClient.updateLabelOption(inputDto);
    }

    @Override
    public void batchUpdateLabelOption(DataShopBatchUpdateShopLabelOptionRequestVo request) {
        DataShopBatchUpdateShopLabelOptionInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopBatchUpdateShopLabelOptionInputDto.class);
        shopClient.batchUpdateLabelOption(inputDto);
    }

    @Override
    public void bindOrganization(DataShopBindOrganizationRequestVo request) {
        Set<String> shopIdSet = request.getShopIdSet();
        if (CollectionUtil.isEmpty(shopIdSet)) {
            return;
        }

        List<DataShopDetailOutputDto> outputDtoList = shopClient.listByIdSet(new IdSetRequest(shopIdSet));
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return;
        }

        DataShopBindOrganizationInputDto inputDto = new DataShopBindOrganizationInputDto();
        inputDto.setOrganizationId(request.getOrganizationId());
        inputDto.setShopCodeSet(outputDtoList.stream().map(DataShopDetailOutputDto::getCode).collect(Collectors.toSet()));
        shopOrganizationRelationClient.bindOrganization(inputDto);
    }

    @Override
    public DataShopDetailResponseVo detail(IdRequest request) {
        DataShopDetailOutputDto outputDto = shopClient.getById(new IdRequest(request.getId()));
        if (outputDto == null) {
            return null;
        }
        DataShopDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataShopDetailResponseVo.class);

        //门店关联组织
        List<DataShopOrganizationRelationListOutputDto> relationListOutputDtoList =
                shopOrganizationRelationClient.listByShopId(new IdRequest(responseVo.getId()));
        if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
            Set<String> organizationIdSet = relationListOutputDtoList.stream()
                    .map(DataShopOrganizationRelationListOutputDto::getOrganizationId).collect(Collectors.toSet());

            Map<String, IamOrganizationSimpleOutputDto> organizationMap = organizationCache.mapByIdSet(organizationIdSet);

            List<DataShopDetailResponseVo.Organization> organizationList = new ArrayList<>();
            for (IamOrganizationSimpleOutputDto dto : organizationMap.values()) {
                DataShopDetailResponseVo.Organization organization = new DataShopDetailResponseVo.Organization();
                organization.setId(dto.getId());
                organization.setCode(dto.getCode());
                organization.setName(dto.getName());
                organization.setType(dto.getType());
                organizationList.add(organization);
            }
            responseVo.setOrganizationList(organizationList);
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
                List<DataShopDetailResponseVo.LabelOption> labelOptionList = new ArrayList<>();
                for (DataLabelOptionListOutputDto dto : outputDtoList) {
                    DataShopDetailResponseVo.LabelOption labelOption = new DataShopDetailResponseVo.LabelOption();
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
    public DataShopCertificateResponseVo getCertificate(IdRequest request) {
        DataShopCertificateResponseVo responseVo = new DataShopCertificateResponseVo();

        { //门店营业执照
            DataShopBusinessLicenseOutputDto outputDto = shopClient.getShopBusinessLicense(new IdRequest(responseVo.getId()));
            responseVo.setBusinessLicense(JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataShopBusinessLicenseResponseVo.class));
        }
        { //食品经营许可证
            DataFoodBusinessLicenseOutputDto outputDto = shopClient.getFoodBusinessLicense(new IdRequest(responseVo.getId()));
            responseVo.setFoodBusinessLicense(JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataShopFoodBusinessLicenseResponseVo.class));
        }
        { //消杀合同
            DataOpenApiDisinfectingContractOutputDto outputDto = shopClient.getDisinfectingContract(new IdRequest(responseVo.getId()));
            responseVo.setDisinfectingContract(JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataShopDisinfectingContractResponseVo.class));
        }
        { //门头照
            DataOpenApiShopFrontPhotoOutputDto outputDto = shopClient.getShopFrontPhoto(new IdRequest(responseVo.getId()));
            responseVo.setFrontPhoto(JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataShopFrontPhotoResponseVo.class));
        }

        return responseVo;
    }

    @Override
    public PageResponse<DataShopSimpleListResponseVo> pageSimple(DataShopQueryPageRequestVo request) {
        //组装ShopId集合
        Set<String> finallyQueryShopIdSet = new HashSet<>();
        boolean compute = assembleFinallyQueryShopIdSet(finallyQueryShopIdSet, request.getOrganizationType(),
                request.getOrganizationIdSet(), request.getLabelOptionIdSet());

        if (compute && CollectionUtil.isEmpty(finallyQueryShopIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        DataShopQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryPageInputDto.class);
        {//区域编码处理
            if (StrUtil.isNotBlank(request.getCountryCode()) && CollectionUtil.isNotEmpty(request.getAreaCodeSet())) {
                DataAreaTreeOutputDto allAreaTree = areaCache.tree(request.getCountryCode());
                Set<String> recursionIdSet = areaCache.recursionListSubId(request.getAreaCodeSet(), allAreaTree);
                inputDto.setAreaCodeSet(recursionIdSet);
            }
        }
        //分页查询门店信息
        inputDto.setShopIdSet(finallyQueryShopIdSet);
        PageResponse<DataShopListOutputDto> pageOutputDto = shopClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }

        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), DataShopSimpleListResponseVo.class));
    }

    @Override
    public PageResponse<DataShopExpandListResponseVo> pageExpand(DataShopQueryPageRequestVo request) {
        //组装ShopId集合
        Set<String> finallyQueryShopIdSet = new HashSet<>();
        boolean compute = assembleFinallyQueryShopIdSet(finallyQueryShopIdSet, request.getOrganizationType(),
                request.getOrganizationIdSet(), request.getLabelOptionIdSet());

        if (compute && CollectionUtil.isEmpty(finallyQueryShopIdSet)) {
            return new PageResponse<>(request.getCurrent(), request.getSize(), 0);
        }

        DataShopQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataShopQueryPageInputDto.class);
        {//区域编码处理
            if (StrUtil.isNotBlank(request.getCountryCode()) && CollectionUtil.isNotEmpty(request.getAreaCodeSet())) {
                DataAreaTreeOutputDto allAreaTree = areaCache.tree(request.getCountryCode());
                Set<String> recursionIdSet = areaCache.recursionListSubId(request.getAreaCodeSet(), allAreaTree);
                inputDto.setAreaCodeSet(recursionIdSet);
            }
        }
        //分页查询门店信息
        inputDto.setShopIdSet(finallyQueryShopIdSet);
        PageResponse<DataShopListOutputDto> pageOutputDto = shopClient.selectPage(inputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }

        PageResponse<DataShopExpandListResponseVo> pageResponse =
                new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(),
                        JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), DataShopExpandListResponseVo.class));

        Set<String> shopIdSet = pageResponse.getRecords().stream().map(DataShopExpandListResponseVo::getId).collect(Collectors.toSet());

        {//门店标签
            Map<String, List<String>> shopIdLabelOptionIdMap = shopLabelOptionRelationClient.mapLabelOptionIdByShopIds(new IdSetRequest(shopIdSet));
            if (CollectionUtil.isNotEmpty(shopIdLabelOptionIdMap)) {
                Set<String> labelOptionIdSet = shopIdLabelOptionIdMap.values().stream()
                        .flatMap(List::stream).collect(Collectors.toSet());
                Map<String, DataLabelOptionListOutputDto> outputDtoMap = labelOptionClient.mapByIdSet(new IdSetRequest(labelOptionIdSet));

                for (DataShopExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    List<String> labelOptionIdList = shopIdLabelOptionIdMap.get(responseVo.getId());
                    if (CollectionUtil.isNotEmpty(labelOptionIdList)) {
                        List<DataShopDetailResponseVo.LabelOption> labelOptionList = new ArrayList<>();
                        for (String labelOptionId : labelOptionIdList) {
                            DataLabelOptionListOutputDto dto = outputDtoMap.get(labelOptionId);
                            DataShopDetailResponseVo.LabelOption labelOption = new DataShopDetailResponseVo.LabelOption();
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

            Map<String, DataAreaTreeOutputDto> areaTreeMap = new HashMap<>();

            Map<String, AddressInfoDto> addressInfoDtoMap = new HashMap<>();
            for (DataShopListOutputDto outputDto : pageOutputDto.getRecords()) {
                String countryCode = outputDto.getCountryCode();
                String provinceCode = outputDto.getProvinceCode();
                if (StrUtil.isNotBlank(countryCode) || StrUtil.isNotBlank(provinceCode)) {
                    DataAreaTreeOutputDto areaTree = areaTreeMap.get(countryCode);
                    if (areaTree == null) {
                        areaTree = areaCache.tree(countryCode);
                        areaTreeMap.put(countryCode, areaTree);
                    }

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
                    addressInfoDtoMap.put(outputDto.getId(), addressInfo);
                }
            }
            if (CollectionUtil.isNotEmpty(addressInfoDtoMap)) {
                for (DataShopExpandListResponseVo responseVo : pageResponse.getRecords()) {
                    responseVo.setAddressInfo(addressInfoDtoMap.get(responseVo.getId()));
                }
            }
        }

        {//创建人、修改人姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(DataShopExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(DataShopExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (DataShopExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }
        return pageResponse;
    }

    private void extractedShopIdByOrganizationIdSet(IamOrganizationTypeEnum organizationType, Set<String> organizationIdSet, Set<String> finallyqueryShopIdSet) {
        for (IamOrganizationTypeEnum type : IamOrganizationTypeEnum.values()) {
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
        Set<String> recursionOrganizationIdSet = organizationCache.recursionListSubIds(organizationType, organizationIdSet);
        if (CollectionUtil.isNotEmpty(recursionOrganizationIdSet)) {
            List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(recursionOrganizationIdSet));
            finallyqueryShopIdSet.addAll(shopIdList);
        }
    }

    private boolean assembleFinallyQueryShopIdSet(Set<String> finallyQueryShopIdSet,
                                                  IamOrganizationTypeEnum organizationType,
                                                  Set<String> organizationIdSet,
                                                  Set<String> labelOptionIdSet) {
        boolean compute = false;

        //组装ShopId集合(组织)
        if (CollectionUtil.isNotEmpty(organizationIdSet)) {
            boolean containRootId = false;
            for (IamOrganizationTypeEnum type : IamOrganizationTypeEnum.values()) {
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
                extractedShopIdByOrganizationIdSet(organizationType, organizationIdSet, finallyQueryShopIdSet);
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
