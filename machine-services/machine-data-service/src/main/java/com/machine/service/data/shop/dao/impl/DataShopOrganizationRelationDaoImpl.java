package com.machine.service.data.shop.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.client.iam.organization.dto.input.IamOrganizationShopRelationQueryListInputDto;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.self.domain.data.shop.DataShopUnBindOrganizationDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.shop.dao.IDataShopOrganizationRelationDao;
import com.machine.service.data.shop.dao.mapper.DataShopOrganizationRelationMapper;
import com.machine.service.data.shop.dao.mapper.entity.DataShopOrganizationRelationEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import com.machine.starter.redis.function.CustomerRedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.IAM_USER_SUPER_APP_DATA_PERMISSION_KEY;

@Repository
public class DataShopOrganizationRelationDaoImpl implements IDataShopOrganizationRelationDao {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataShopOrganizationRelationMapper shopOrganizationRelationMapper;

    @Override
    public int insert(DataShopOrganizationRelationEntity entity) {
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
                             IamOrganizationTypeEnum organizationType) {
        DataShopOrganizationRelationEntity entity = selectOneByUk(shopId, organizationType);
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
    public int update(DataShopOrganizationRelationEntity entity) {
        //缓存
        customerRedisCommands.del(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        customerRedisCommands.del(IAM_USER_MANAGE_DATA_PERMISSION_KEY);

        //事件
        customerStreamBridge.sendWebHookEvent(
                EventTypeEnum.DATA_SHOP_UPDATE_UPDATE_SHOP_ORGANIZATION, new IdDto(entity.getShopId()));

        return shopOrganizationRelationMapper.updateById(entity);
    }

    @Override
    public Boolean isAssociationShopByOrganizationId(String organizationId) {
        return shopOrganizationRelationMapper.isAssociationShop(organizationId);
    }

    @Override
    public DataShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                            String organizationId) {
        Wrapper<DataShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<DataShopOrganizationRelationEntity>()
                .eq(DataShopOrganizationRelationEntity::getShopId, shopId)
                .eq(DataShopOrganizationRelationEntity::getOrganizationId, organizationId);
        return shopOrganizationRelationMapper.selectOne(queryWrapper);
    }

    @Override
    public DataShopOrganizationRelationEntity selectOneByUk(String shopId,
                                                            IamOrganizationTypeEnum organizationType) {
        Wrapper<DataShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<DataShopOrganizationRelationEntity>()
                .eq(DataShopOrganizationRelationEntity::getShopId, shopId)
                .eq(DataShopOrganizationRelationEntity::getOrganizationType, organizationType);
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
    public List<DataShopOrganizationRelationEntity> listByShopId(String shopId) {
        Wrapper<DataShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<DataShopOrganizationRelationEntity>()
                .eq(DataShopOrganizationRelationEntity::getShopId, shopId);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataShopOrganizationRelationEntity> listByOrganizationIdSet(Set<String> organizationIdSet) {
        Wrapper<DataShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<DataShopOrganizationRelationEntity>()
                .in(DataShopOrganizationRelationEntity::getOrganizationId, organizationIdSet);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataShopOrganizationRelationEntity> listByShopIdSet(Set<String> shopIdSet) {
        Wrapper<DataShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<DataShopOrganizationRelationEntity>()
                .in(DataShopOrganizationRelationEntity::getShopId, shopIdSet);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataShopOrganizationRelationEntity> listByShopIdSet(IamOrganizationTypeEnum organizationType,
                                                                    Set<String> shopIdSet) {
        Wrapper<DataShopOrganizationRelationEntity> queryWrapper = new LambdaQueryWrapper<DataShopOrganizationRelationEntity>()
                .eq(DataShopOrganizationRelationEntity::getOrganizationType, organizationType)
                .in(DataShopOrganizationRelationEntity::getShopId, shopIdSet);
        return shopOrganizationRelationMapper.selectList(queryWrapper);
    }

    @Override
    public List<DataShopOrganizationRelationEntity> listByCondition(IamOrganizationShopRelationQueryListInputDto inputDto) {
        return shopOrganizationRelationMapper.listByCondition(inputDto);
    }
}
