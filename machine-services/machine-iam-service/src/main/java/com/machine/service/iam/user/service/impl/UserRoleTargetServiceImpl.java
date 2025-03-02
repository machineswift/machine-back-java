package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoBindFranchiseeShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoUnbindFranchiseeShopInputDto;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IUserRoleTargetRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import com.machine.service.iam.user.service.IUserRoleTargetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class UserRoleTargetServiceImpl implements IUserRoleTargetService {

    @Autowired
    private IUserRoleTargetRelationDao userRoleTargetRelationDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindFranchiseeShop(IamUserRoleInfoBindFranchiseeShopInputDto inputDto) {

        String userId = inputDto.getUserId();
        String shopId = inputDto.getShopId();

        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao.listByUserAndRoleIdId(
                userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            //添加角色(加盟商)
            UserRoleTargetRelationEntity relationInsertEntity = new UserRoleTargetRelationEntity();
            relationInsertEntity.setUserId(userId);
            relationInsertEntity.setRoleId(ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            relationInsertEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
            relationInsertEntity.setTargetId(shopId);
            userRoleTargetRelationDao.insert(inputDto.getUserId(), relationInsertEntity);
            return Boolean.TRUE;
        }

        //已经包含则返回
        for (UserRoleTargetRelationEntity entity : relationEntityList) {
            if (UserRoleTargetTypeEnum.SHOP == entity.getTargetType()) {
                if (entity.getTargetId().equals(inputDto.getShopId())) {
                    return Boolean.TRUE;
                }
            }
        }

        //是否包含 targetType 为null的数据
        UserRoleTargetRelationEntity nullTargetEntity = null;
        for (UserRoleTargetRelationEntity entity : relationEntityList) {
            if (null == entity.getTargetType()) {
                nullTargetEntity = entity;
                break;
            }
        }

        //修改数据
        if (null != nullTargetEntity) {
            nullTargetEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
            nullTargetEntity.setTargetId(shopId);
            userRoleTargetRelationDao.update(nullTargetEntity);
            return Boolean.TRUE;
        }

        //添加角色(加盟商)
        UserRoleTargetRelationEntity relationInsertEntity = new UserRoleTargetRelationEntity();
        relationInsertEntity.setUserId(userId);
        relationInsertEntity.setRoleId(ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        relationInsertEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
        relationInsertEntity.setTargetId(shopId);
        userRoleTargetRelationDao.insert(inputDto.getUserId(), relationInsertEntity);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindFranchiseeShop(IamUserRoleInfoUnbindFranchiseeShopInputDto inputDto) {
        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao.listByUserAndRoleIdId(
                inputDto.getUserId(), ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            return Boolean.TRUE;
        }

        boolean contain = false;
        UserRoleTargetRelationEntity containEntity = null;
        for (UserRoleTargetRelationEntity entity : relationEntityList) {
            if (UserRoleTargetTypeEnum.SHOP == entity.getTargetType()) {
                if (entity.getTargetId().equals(inputDto.getShopId())) {
                    contain = Boolean.TRUE;
                    containEntity = entity;
                    break;
                }
            }
        }

        if (contain) {
            if (relationEntityList.size() == 1) {
                //修改
                containEntity.setTargetType(null);
                containEntity.setTargetId(null);
                userRoleTargetRelationDao.updateTargetNull(containEntity.getUserId(), containEntity.getId());
            } else {
                //删除
                userRoleTargetRelationDao.delete(containEntity);
            }
        }

        return Boolean.TRUE;
    }

    @Override
    public List<String> listUserIdByOrganizationIdSet(IdSetRequest request) {
        return userRoleTargetRelationDao.listUserIdByOrganizationIdSet(request.getIdSet());
    }

    @Override
    public List<IamUserRoleTargetListOutputDto> listByUserId(IdRequest request) {
        List<UserRoleTargetRelationEntity> entityList = userRoleTargetRelationDao.listByUserId(request.getId());
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleTargetListOutputDto.class);
    }

    @Override
    public List<IamUserRoleTargetListOutputDto> listByUserIdSet(IdSetRequest request) {
        List<UserRoleTargetRelationEntity> entityList = userRoleTargetRelationDao.listByUserIdSet(request.getIdSet());
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleTargetListOutputDto.class);
    }

    @Override
    public List<IamUserRoleTargetListOutputDto> listOrganizationShopByRoleIdSet(IdSetRequest request) {
        List<UserRoleTargetRelationEntity> entityList = userRoleTargetRelationDao.listOrganizationShopByRoleIdSet(request.getIdSet());
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleTargetListOutputDto.class);
    }

    @Override
    public List<IamUserRoleTargetListOutputDto> listByCondition(IamUserRoleTargetQueryListInputDto inputDto) {
        List<UserRoleTargetRelationEntity> entityList = userRoleTargetRelationDao.listByCondition(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserRoleTargetListOutputDto.class);
    }

}
