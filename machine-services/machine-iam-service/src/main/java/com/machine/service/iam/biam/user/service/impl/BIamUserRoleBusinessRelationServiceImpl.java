package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.biam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.envm.biam.role.BIamShopDefaultRoleEnum;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleBusinessRelationDao;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleBusinessRelationEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.service.iam.biam.user.service.IBIamUserRoleBusinessRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class BIamUserRoleBusinessRelationServiceImpl implements IBIamUserRoleBusinessRelationService {

    @Autowired
    private IBIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IBIamUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindFranchiseeShop(IamUserRoleInfoFranchiseeBindShopInputDto inputDto) {
        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        //用户角色关系ID
        String userRoleRelationId;
        BIamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationDao.getByUk(
                userId, BIamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (null != iamUserRoleRelationEntity) {
            userRoleRelationId = iamUserRoleRelationEntity.getId();
        } else {
            //添加角色(加盟商)
            userRoleRelationId = userRoleRelationDao.insert(userId, BIamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        }

        BIamUserRoleBusinessRelationEntity iamUserRoleBusinessRelationEntity = userRoleBusinessRelationDao
                .getByUk(userRoleRelationId, shopId, BIamUserRoleBusinessTypeEnum.SHOP);

        if (null != iamUserRoleBusinessRelationEntity) {
            return Boolean.TRUE;
        }

        userRoleBusinessRelationDao.insert(userRoleRelationId, shopId, BIamUserRoleBusinessTypeEnum.SHOP);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindFranchiseeShop(IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto) {
        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        //用户角色关系ID
        String userRoleRelationId;
        BIamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationDao.getByUk(
                userId, BIamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (null != iamUserRoleRelationEntity) {
            userRoleRelationId = iamUserRoleRelationEntity.getId();
        } else {
            return Boolean.TRUE;
        }

        userRoleBusinessRelationDao.deleteByUk(userRoleRelationId, shopId, BIamUserRoleBusinessTypeEnum.SHOP);
        return Boolean.TRUE;
    }

    @Override
    public List<BIamUserRoleBusinessRelationListOutputDto> listByShopIdSet(IdSetRequest request) {
        List<BIamUserRoleBusinessRelationEntity> entityList = userRoleBusinessRelationDao.listByBusinessIdSet(
                BIamUserRoleBusinessTypeEnum.SHOP, request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserRoleBusinessRelationListOutputDto.class);
    }

    @Override
    public List<BIamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(IdSetRequest request) {
        List<BIamUserRoleBusinessRelationEntity> entityList = userRoleBusinessRelationDao
                .listByUserRoleRelationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserRoleBusinessRelationListOutputDto.class);
    }

}
