package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.output.IamUserRoleBusinessRelationListOutputDto;
import com.machine.sdk.common.envm.iam.UserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IUserRoleBusinessRelationDao;
import com.machine.service.iam.user.dao.IUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
import com.machine.service.iam.user.service.IUserRoleBusinessRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserRoleBusinessRelationServiceImpl implements IUserRoleBusinessRelationService {

    @Autowired
    private IUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IUserRoleBusinessRelationDao userRoleBusinessRelationDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindFranchiseeShop(IamUserRoleInfoFranchiseeBindShopInputDto inputDto) {
        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        //用户角色关系ID
        String userRoleRelationId;
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationDao.getByUk(
                userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (null != userRoleRelationEntity) {
            userRoleRelationId = userRoleRelationEntity.getId();
        } else {
            //添加角色(加盟商)
            userRoleRelationId = userRoleRelationDao.insert(userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        }

        UserRoleBusinessRelationEntity userRoleBusinessRelationEntity = userRoleBusinessRelationDao
                .getByUk(userRoleRelationId, shopId, UserRoleBusinessTypeEnum.SHOP);

        if (null != userRoleBusinessRelationEntity) {
            return Boolean.TRUE;
        }

        userRoleBusinessRelationDao.insert(userRoleRelationId, shopId, UserRoleBusinessTypeEnum.SHOP);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindFranchiseeShop(IamUserRoleInfoFranchiseeUnBindShopInputDto inputDto) {
        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        //用户角色关系ID
        String userRoleRelationId;
        UserRoleRelationEntity userRoleRelationEntity = userRoleRelationDao.getByUk(
                userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (null != userRoleRelationEntity) {
            userRoleRelationId = userRoleRelationEntity.getId();
        } else {
            return Boolean.TRUE;
        }

        userRoleBusinessRelationDao.deleteByUk(userRoleRelationId, shopId, UserRoleBusinessTypeEnum.SHOP);
        return Boolean.TRUE;
    }

    @Override
    public List<IamUserRoleBusinessRelationListOutputDto> listByShopIdSet(IdSetRequest request) {
        List<UserRoleBusinessRelationEntity> entityList = userRoleBusinessRelationDao.listByBusinessIdSet(
                request.getIdSet(), UserRoleBusinessTypeEnum.SHOP);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleBusinessRelationListOutputDto.class);
    }

    @Override
    public List<IamUserRoleBusinessRelationListOutputDto> listByUserRoleRelationIdSet(IdSetRequest request) {
        List<UserRoleBusinessRelationEntity> entityList = userRoleBusinessRelationDao
                .listByUserRoleRelationIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleBusinessRelationListOutputDto.class);
    }

}
