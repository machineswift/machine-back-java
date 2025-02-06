package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.organization.dto.input.IamUserRoleTargetQueryListInputDto;
import com.machine.client.iam.organization.dto.output.IamUserRoleTargetListOutputDto;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoBindFranchiseeShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoUnbindFranchiseeShopInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.dao.IUserRoleTargetRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import com.machine.service.iam.user.service.IUserRoleTargetService;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class UserRoleTargetServiceImpl implements IUserRoleTargetService {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IUserRoleTargetRelationDao userRoleTargetRelationDao;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindFranchiseeShop(IamUserRoleInfoBindFranchiseeShopInputDto inputDto) {
        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao.listByUserAndRoleIdId(
                inputDto.getUserId(), ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            //添加角色(加盟商)
            UserRoleTargetRelationEntity relationInsertEntity = new UserRoleTargetRelationEntity();
            relationInsertEntity.setUserId(inputDto.getUserId());
            relationInsertEntity.setRoleId(ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            relationInsertEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
            relationInsertEntity.setTargetId(inputDto.getShopId());
            userRoleTargetRelationDao.insert(inputDto.getUserId(), relationInsertEntity);
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
            nullTargetEntity.setTargetId(inputDto.getShopId());
            userRoleTargetRelationDao.update(nullTargetEntity);
            return Boolean.TRUE;
        }

        //添加角色(加盟商)
        UserRoleTargetRelationEntity relationInsertEntity = new UserRoleTargetRelationEntity();
        relationInsertEntity.setUserId(inputDto.getUserId());
        relationInsertEntity.setRoleId(ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        relationInsertEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
        relationInsertEntity.setTargetId(inputDto.getShopId());
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
    public DataPermissionDto dataPermission4SuperApp() {
        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao
                .listByUserId(AppContext.getContext().getUserId());

        DataPermissionDto dataPermissionDto = new DataPermissionDto();
        if (CollectionUtil.isEmpty(relationEntityList)) {
            return dataPermissionDto;
        }

        Set<String> organizationIdSet = new HashSet<>();
        Set<String> shopIdSet = new HashSet<>();

        for (UserRoleTargetRelationEntity entity : relationEntityList) {
            if (UserRoleTargetTypeEnum.ORGANIZATION == entity.getTargetType()) {
                organizationIdSet.add(entity.getTargetId());
            } else if (UserRoleTargetTypeEnum.SHOP == entity.getTargetType()) {
                shopIdSet.add(entity.getTargetId());
            }
        }

        //递归查询部门Id
        organizationIdSet.addAll(organizationCache.recursionListSubIds(organizationIdSet));

        //查询部门关联的门店Id
        List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(organizationIdSet));
        if (CollectionUtil.isNotEmpty(shopIdList)) {
            shopIdSet.addAll(shopIdList);
        }

        //组装返回数据
        dataPermissionDto.setOrganizationIdSet(organizationIdSet);
        dataPermissionDto.setShopIdSet(shopIdSet);
        return dataPermissionDto;
    }

    @Override
    public DataPermissionDto dataPermission4SuperManage(DataPermission4ManageInputDto inputDto) {
        return null;
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
