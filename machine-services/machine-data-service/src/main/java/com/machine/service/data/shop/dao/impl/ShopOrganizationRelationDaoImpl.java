package com.machine.service.data.shop.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.envm.iam.organization.OrganizationTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.domain.data.shop.DataShopUnBindOrganizationDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.shop.dao.IShopOrganizationRelationDao;
import com.machine.service.data.shop.dao.mapper.IShopOrganizationRelationMapper;
import com.machine.service.data.shop.dao.mapper.entity.ShopOrganizationRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class ShopOrganizationRelationDaoImpl implements IShopOrganizationRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private IShopOrganizationRelationMapper shopOrganizationRelationMapper;

    @Override
    public int insert(ShopOrganizationRelationEntity entity) {
        //缓存
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(
                EventTypeEnum.DATA_SHOP_UPDATE_UPDATE_SHOP_ORGANIZATION, new IdDto(entity.getShopId()));

        return shopOrganizationRelationMapper.insert(entity);
    }

    @Override
    public int deleteOneByUk(String shopId,
                             OrganizationTypeEnum organizationType) {
        ShopOrganizationRelationEntity entity = selectOneByUk(shopId, organizationType);
        if (entity == null) {
            return 0;
        } else {
            //缓存
            customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
            customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

            //事件
            customerStreamBridge.sendWebHookEvent(
                    EventTypeEnum.DATA_SHOP_UPDATE_UNBIND_SHOP_ORGANIZATION, new DataShopUnBindOrganizationDto(shopId, entity.getOrganizationId()));
            return shopOrganizationRelationMapper.deleteById(entity.getId());
        }
    }

    @Override
    public int update(ShopOrganizationRelationEntity entity) {
        //缓存
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(
                EventTypeEnum.DATA_SHOP_UPDATE_UPDATE_SHOP_ORGANIZATION, new IdDto(entity.getShopId()));

        return shopOrganizationRelationMapper.updateById(entity);
    }

    @Override
    public Boolean isAssociationShopByOrganizationIdSet(Set<String> organizationIdSet) {
        return shopOrganizationRelationMapper.isAssociationShop(organizationIdSet);
    }

    @Override
    public ShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                        String organizationId) {
        Wrapper<ShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopOrganizationRelationEntity>()
                .eq(ShopOrganizationRelationEntity::getShopId, shopId)
                .eq(ShopOrganizationRelationEntity::getOrganizationId, organizationId);
        return shopOrganizationRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public ShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                        OrganizationTypeEnum organizationType) {
        Wrapper<ShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopOrganizationRelationEntity>()
                .eq(ShopOrganizationRelationEntity::getShopId, shopId)
                .eq(ShopOrganizationRelationEntity::getOrganizationType, organizationType);
        return shopOrganizationRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public List<String> listShopIdByOrganizationIdSet(Set<String> organizationIdSet) {
        if (CollectionUtil.isEmpty(organizationIdSet)) {
            return List.of();
        }
        return shopOrganizationRelationMapper.listShopIdByOrganizationIdSet(organizationIdSet);
    }

    @Override
    public List<ShopOrganizationRelationEntity> listByShopId(String shopId) {
        Wrapper<ShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopOrganizationRelationEntity>()
                .eq(ShopOrganizationRelationEntity::getShopId, shopId);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdSet) {
        Wrapper<ShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopOrganizationRelationEntity>()
                .in(ShopOrganizationRelationEntity::getOrganizationId, organizationIdSet);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopOrganizationRelationEntity> listByShopIdSet(Set<String> shopIdSet) {
        Wrapper<ShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopOrganizationRelationEntity>()
                .in(ShopOrganizationRelationEntity::getShopId, shopIdSet);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopOrganizationRelationEntity> listByShopIdSet(OrganizationTypeEnum organizationType,
                                                                Set<String> shopIdSet) {
        Wrapper<ShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<ShopOrganizationRelationEntity>()
                .eq(ShopOrganizationRelationEntity::getOrganizationType, organizationType)
                .in(ShopOrganizationRelationEntity::getShopId, shopIdSet);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<ShopOrganizationRelationEntity> listByCondition(IamOrganizationShopRelationQueryListInputDto inputDto) {
        return shopOrganizationRelationMapper.listByCondition(inputDto);
    }
}
