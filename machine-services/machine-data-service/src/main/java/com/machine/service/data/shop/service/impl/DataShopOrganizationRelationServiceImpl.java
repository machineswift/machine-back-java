package com.machine.service.data.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.input.DataShopMapByShopIdSetInputDto;
import com.machine.client.data.shop.dto.input.DataShopBindOrganizationInputDto;
import com.machine.client.data.shop.dto.output.DataShopOrganizationRelationListOutputDto;
import com.machine.client.iam.organization.IIamOrganizationClient;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamOrganizationDetailOutputDto;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.shop.dao.IDataShopOrganizationRelationDao;
import com.machine.service.data.shop.dao.mapper.entity.DataShopOrganizationRelationEntity;
import com.machine.service.data.shop.service.IDataShopOrganizationRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.SEPARATOR_COLON;
import static com.machine.sdk.common.constant.CommonIamConstant.Organization.DATA_ORGANIZATION_VIRTUAL_NODE;

@Slf4j
@Service
public class DataShopOrganizationRelationServiceImpl implements IDataShopOrganizationRelationService {

    @Autowired
    private IDataShopOrganizationRelationDao shopOrganizationRelationDao;

    @Autowired
    private IDataShopClient dataShopClient;

    @Autowired
    private IIamOrganizationClient organizationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindOrganization(DataShopBindOrganizationInputDto inputDto) {
        String organizationId = inputDto.getOrganizationId();
        Set<String> shopCodeSet = inputDto.getShopCodeSet();
        if (CollectionUtil.isEmpty(shopCodeSet)) {
            return;
        }

        //查询出门店类型
        IamOrganizationTypeEnum type;
        if (organizationId.endsWith(DATA_ORGANIZATION_VIRTUAL_NODE)) {
            String typeCode = organizationId.split(String.valueOf(SEPARATOR_COLON))[0];
            type = IamOrganizationTypeEnum.valueOf(typeCode);
        } else {
            IamOrganizationDetailOutputDto outputDto = organizationClient.detail(new IdRequest(inputDto.getOrganizationId()));
            if (null == outputDto) {
                throw new DataBusinessException("data.ShopOrganizationRelation.service.bindOrganization.organizationNotExists", "组织不存在");
            }
            type = outputDto.getType();
        }

        List<String> shopIdList = dataShopClient.listShopIdByShopCodeSet(new IdSetRequest(shopCodeSet));

        for (String shopId : shopIdList) {
            DataShopOrganizationRelationEntity dbEntity = shopOrganizationRelationDao.selectOneByUk(
                    shopId, inputDto.getOrganizationId());
            if (null != dbEntity) {
                continue;
            }

            if (organizationId.endsWith(DATA_ORGANIZATION_VIRTUAL_NODE)) {
                shopOrganizationRelationDao.deleteOneByUk(shopId, type);
            } else {
                //查询门店是否绑定指定类型的组织
                dbEntity = shopOrganizationRelationDao.selectOneByUk(shopId, type);

                if (null == dbEntity) {
                    //新增数据
                    DataShopOrganizationRelationEntity insertEntity = new DataShopOrganizationRelationEntity();
                    insertEntity.setShopId(shopId);
                    insertEntity.setOrganizationId(inputDto.getOrganizationId());
                    insertEntity.setOrganizationType(type);
                    shopOrganizationRelationDao.insert(insertEntity);
                } else {
                    //修改数据
                    DataShopOrganizationRelationEntity updateEntity = new DataShopOrganizationRelationEntity();
                    updateEntity.setId(dbEntity.getId());
                    updateEntity.setShopId(dbEntity.getShopId());
                    updateEntity.setOrganizationId(inputDto.getOrganizationId());
                    shopOrganizationRelationDao.update(updateEntity);
                }
            }
        }
    }

    @Override
    public Boolean isAssociationShopByOrganizationId(IdRequest request) {
        return shopOrganizationRelationDao.isAssociationShopByOrganizationId(request.getId());
    }

    @Override
    public List<String> listShopIdByOrganizationIdSet(IdSetRequest request) {
        return shopOrganizationRelationDao.listShopIdByOrganizationIdSet(request.getIdSet());
    }

    @Override
    public Map<String, String> mapByShopIdSet(DataShopMapByShopIdSetInputDto inputDto) {
        List<DataShopOrganizationRelationEntity> entityList = shopOrganizationRelationDao.listByShopIdSet(
                inputDto.getOrganizationType(), inputDto.getShopIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }
        return entityList.stream()
                .collect(Collectors.toMap(
                        DataShopOrganizationRelationEntity::getShopId,
                        DataShopOrganizationRelationEntity::getOrganizationId
                ));
    }

    @Override
    public List<DataShopOrganizationRelationListOutputDto> listByShopId(IdRequest request) {
        List<DataShopOrganizationRelationEntity> entityList = shopOrganizationRelationDao.listByShopId(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataShopOrganizationRelationListOutputDto.class);
    }

    @Override
    public List<DataShopOrganizationRelationListOutputDto> listByShopIdSet(IdSetRequest request) {
        List<DataShopOrganizationRelationEntity> entityList = shopOrganizationRelationDao.listByShopIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataShopOrganizationRelationListOutputDto.class);
    }

    @Override
    public List<DataShopOrganizationRelationListOutputDto> listByOrganizationIdSet(IdSetRequest request) {
        List<DataShopOrganizationRelationEntity> entityList = shopOrganizationRelationDao.listByOrganizationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataShopOrganizationRelationListOutputDto.class);
    }

    @Override
    public List<DataShopOrganizationRelationListOutputDto> listByCondition(IamOrganizationShopRelationQueryListInputDto inputDto) {
        List<DataShopOrganizationRelationEntity> entityList = shopOrganizationRelationDao.listByCondition(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataShopOrganizationRelationListOutputDto.class);
    }

}
