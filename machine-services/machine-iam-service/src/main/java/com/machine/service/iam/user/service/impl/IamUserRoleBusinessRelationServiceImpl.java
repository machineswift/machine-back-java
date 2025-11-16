package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.common.envm.iam.role.IamUserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.role.IamShopDefaultRoleEnum;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IIamUserRoleBusinessRelationDao;
import com.machine.service.iam.user.dao.IIamUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.service.iam.user.service.IIamUserRoleBusinessRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class IamUserRoleBusinessRelationServiceImpl implements IIamUserRoleBusinessRelationService {

    @Autowired
    private IIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IIamUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindFranchiseeShop(IamUserRoleInfoFranchiseeBindShopInputDto inputDto) {
        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        //用户角色关系ID
        String userRoleRelationId;
        IamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationDao.getByUk(
                userId, IamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (null != iamUserRoleRelationEntity) {
            userRoleRelationId = iamUserRoleRelationEntity.getId();
        } else {
            //添加角色(加盟商)
            userRoleRelationId = userRoleRelationDao.insert(userId, IamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        }

        IamUserRoleBusinessRelationEntity iamUserRoleBusinessRelationEntity = userRoleBusinessRelationDao
                .getByUk(userRoleRelationId, shopId, IamUserRoleBusinessTypeEnum.SHOP);

        if (null != iamUserRoleBusinessRelationEntity) {
            return Boolean.TRUE;
        }

        userRoleBusinessRelationDao.insert(userRoleRelationId, shopId, IamUserRoleBusinessTypeEnum.SHOP);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindFranchiseeShop(IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto) {
        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        //用户角色关系ID
        String userRoleRelationId;
        IamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationDao.getByUk(
                userId, IamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (null != iamUserRoleRelationEntity) {
            userRoleRelationId = iamUserRoleRelationEntity.getId();
        } else {
            return Boolean.TRUE;
        }

        userRoleBusinessRelationDao.deleteByUk(userRoleRelationId, shopId, IamUserRoleBusinessTypeEnum.SHOP);
        return Boolean.TRUE;
    }

    @Override
    public List<IamUserRoleBusinessRelationListOutputDto> listByShopIdSet(IdSetRequest request) {
        List<IamUserRoleBusinessRelationEntity> entityList = userRoleBusinessRelationDao.listByBusinessIdSet(
                IamUserRoleBusinessTypeEnum.SHOP, request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleBusinessRelationListOutputDto.class);
    }

    @Override
    public List<IamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(IdSetRequest request) {
        List<IamUserRoleBusinessRelationEntity> entityList = userRoleBusinessRelationDao
                .listByUserRoleRelationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleBusinessRelationListOutputDto.class);
    }

}
