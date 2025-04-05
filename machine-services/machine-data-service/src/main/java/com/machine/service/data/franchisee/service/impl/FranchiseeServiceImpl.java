package com.machine.service.data.franchisee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.franchisee.dto.input.*;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeListOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserRoleBusinessRelationClient;
import com.machine.client.iam.user.dto.input.IamFranchiseeUserCreateInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserUpdatePhoneInputDto;
import com.machine.sdk.common.envm.system.DataPersistenceStatusEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.franchisee.dao.IFranchiseeDao;
import com.machine.service.data.franchisee.dao.IFranchiseeShopRelationDao;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeEntity;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeShopRelationEntity;
import com.machine.service.data.franchisee.service.IFranchiseeService;
import com.machine.service.data.shop.dao.IShopDao;
import com.machine.service.data.shop.dao.mapper.entity.ShopEntity;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class FranchiseeServiceImpl implements IFranchiseeService {

    @Autowired
    private IShopDao shopDao;

    @Autowired
    private IFranchiseeDao franchiseeDao;

    @Autowired
    private IFranchiseeShopRelationDao franchiseeShopRelationDao;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserRoleBusinessRelationClient userRoleBusinessRelationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataFranchiseeCreateInputDto inputDto) {
        //验证编码是否存在
        FranchiseeEntity codeEntity = franchiseeDao.getByCode(inputDto.getCode());
        if (null != codeEntity) {
            throw new IamBusinessException("data.franchisee.create.codeAlreadyExists", "编码已经存在");
        }

        //验证证件号是否存在
        FranchiseeEntity certificateEntity = franchiseeDao.getByCertificate(inputDto.getCertificateType(), inputDto.getCertificateNumber());
        if (null != certificateEntity) {
            throw new IamBusinessException("data.franchisee.create.certificateAlreadyExists", "证件号已经存在");
        }

        //根据手机号创建加盟商用户
        String userId = userClient.createFranchiseeUser(new IamFranchiseeUserCreateInputDto(inputDto.getPhone(), inputDto.getName()));

        //验证是否存在加盟商
        FranchiseeEntity userIdEntity = franchiseeDao.getByUserId(userId);
        if (null != userIdEntity) {
            throw new IamBusinessException("data.franchisee.create.franchiseeAlreadyExists", "加盟商已经存在");
        }

        //创建加盟商
        FranchiseeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), FranchiseeEntity.class);
        insertEntity.setUserId(userId);
        String franchiseeId = franchiseeDao.insert(insertEntity);

        //身份证信息
        franchiseeDao.updateIdentityCard(franchiseeId, DataPersistenceStatusEnum.TEMPORARY, inputDto.getIdentityCard());
        //健康证信息
        franchiseeDao.updateHealthCertificate(franchiseeId, DataPersistenceStatusEnum.TEMPORARY, inputDto.getHealthCertificate());
        return franchiseeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindShop(DataFranchiseeBindShopInputDto inputDto) {
        FranchiseeEntity entity = franchiseeDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("data.franchisee.bindShop.franchiseeNotExists", "加盟商不存在");
        }

        ShopEntity dbShopEntity = shopDao.getByCode(inputDto.getShopCode());
        if (null == dbShopEntity) {
            throw new IamBusinessException("data.franchisee.bindShop.shopCodeNotExists", "门店编码不存在");
        }

        //加盟商角色绑定的门店
        userRoleBusinessRelationClient.bindFranchiseeShop(
                new IamUserRoleInfoFranchiseeBindShopInputDto(entity.getUserId(), dbShopEntity.getId()));

        //添加加盟商和门店的关系
        FranchiseeShopRelationEntity dbRelationEntity = franchiseeShopRelationDao.getByShopId(dbShopEntity.getId());
        if (null != dbRelationEntity) {
            if (dbRelationEntity.getFranchiseeId().equals(inputDto.getId())) {
                return Boolean.TRUE;
            } else {
                throw new IamBusinessException("data.franchisee.bindShop.bindOtherFranchisee", "门店绑定其他加盟商");
            }
        }

        FranchiseeShopRelationEntity insertRelation = new FranchiseeShopRelationEntity();
        insertRelation.setFranchiseeId(inputDto.getId());
        insertRelation.setShopId(dbShopEntity.getId());
        franchiseeShopRelationDao.insert(dbShopEntity.getCode(), insertRelation);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindShop(DataFranchiseeUnbindShopInputDto inputDto) {
        FranchiseeEntity entity = franchiseeDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("data.franchisee.unbindShop.franchiseeNotExists", "加盟商不存在");
        }

        ShopEntity dbShopEntity = shopDao.getByCode(inputDto.getShopCode());
        if (null == dbShopEntity) {
            throw new IamBusinessException("data.franchisee.unbindShop.shopCodeNotExists", "门店编码不存在");
        }

        //加盟商角色解绑的门店
        userRoleBusinessRelationClient.unbindFranchiseeShop(
                new IamUserRoleInfoFranchiseeUnBindShopInputDto(entity.getUserId(), dbShopEntity.getId()));

        //删除加盟商和门店的关系
        franchiseeShopRelationDao.deleteByUk(inputDto.getId(), dbShopEntity.getId(), dbShopEntity.getCode());
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhone(OpenApiFranchiseeUpdatePhoneInputDto inputDto) {
        FranchiseeEntity entity = franchiseeDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("data.franchisee.unbindShop.franchiseeNotExists", "加盟商不存在");
        }
        return userClient.updatePhone(new IamUserUpdatePhoneInputDto(entity.getUserId(), inputDto.getPhone()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataFranchiseeUpdateInputDto inputDto) {
        FranchiseeEntity dbEntity = franchiseeDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }
        FranchiseeEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), FranchiseeEntity.class);

        //身份证信息
        franchiseeDao.updateIdentityCard(updateEntity.getId(), DataPersistenceStatusEnum.PERMANENT, inputDto.getIdentityCard());
        //健康证信息
        franchiseeDao.updateHealthCertificate(updateEntity.getId(), DataPersistenceStatusEnum.TEMPORARY, inputDto.getHealthCertificate());
        return franchiseeDao.update(updateEntity);
    }

    @Override
    public int updateIdentityCard(OpenApiFranchiseeUpdateIdentityCardInputDto inputDto) {
        FranchiseeEntity dbEntity = franchiseeDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }
        //身份证
        return franchiseeDao.updateIdentityCard(dbEntity.getId(), inputDto.getPersistenceStatus(), inputDto.getIdentityCard());
    }

    @Override
    public int updateHealthCertificate(OpenApiFranchiseeUpdateHealthCertificateInputDto inputDto) {
        FranchiseeEntity dbEntity = franchiseeDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }
        //健康证
        return franchiseeDao.updateHealthCertificate(dbEntity.getId(), inputDto.getPersistenceStatus(), inputDto.getHealthCertificate());
    }

    @Override
    public DataFranchiseeDetailOutputDto detail(IdRequest request) {
        FranchiseeEntity entity = franchiseeDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return getDataFranchiseeDetailOutputDto(entity);
    }

    @Override
    public DataFranchiseeDetailOutputDto getByUserId(IdRequest request) {
        FranchiseeEntity entity = franchiseeDao.getByUserId(request.getId());
        if (null == entity) {
            return null;
        }
        return getDataFranchiseeDetailOutputDto(entity);
    }

    @Override
    public DataFranchiseeDetailOutputDto detailByCode(IdRequest request) {
        FranchiseeEntity entity = franchiseeDao.getByCode(request.getId());
        if (null == entity) {
            return null;
        }
        return getDataFranchiseeDetailOutputDto(entity);
    }


    @Override
    public OpenapiFranchiseeIdentityCardOutputDto identityCard(IdRequest request) {
        OpenapiFranchiseeIdentityCardOutputDto outputDto = new OpenapiFranchiseeIdentityCardOutputDto();
        outputDto.setTemporaryIdentityCard(franchiseeDao.getIdentityCard(request.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentIdentityCard(franchiseeDao.getIdentityCard(request.getId(), DataPersistenceStatusEnum.PERMANENT));
        return outputDto;
    }

    @Override
    public OpenapiFranchiseeHealthCertificateOutputDto healthCertificate(IdRequest request) {
        OpenapiFranchiseeHealthCertificateOutputDto outputDto = new OpenapiFranchiseeHealthCertificateOutputDto();
        outputDto.setTemporaryHealthCertificate(franchiseeDao.getHealthCertificated(request.getId(), DataPersistenceStatusEnum.TEMPORARY));
        outputDto.setPermanentHealthCertificate(franchiseeDao.getHealthCertificated(request.getId(), DataPersistenceStatusEnum.PERMANENT));
        return outputDto;
    }

    @Override
    public List<DataFranchiseeListOutputDto> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto) {
        List<FranchiseeEntity> entityList = franchiseeDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataFranchiseeListOutputDto.class);
    }

    private @NotNull DataFranchiseeDetailOutputDto getDataFranchiseeDetailOutputDto(FranchiseeEntity entity) {
        DataFranchiseeDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFranchiseeDetailOutputDto.class);

        //获取门店id
        List<FranchiseeShopRelationEntity> relationEntityList = franchiseeShopRelationDao.getByFranchiseeId(entity.getId());
        if (CollectionUtil.isNotEmpty(relationEntityList)) {
            outputDto.setBindShopIdList(relationEntityList.stream().map(FranchiseeShopRelationEntity::getShopId).toList());
        }

        //身份证信息
        outputDto.setIdentityCard(franchiseeDao.getIdentityCard(entity.getId(), DataPersistenceStatusEnum.PERMANENT));
        outputDto.setHealthCertificate(franchiseeDao.getHealthCertificated(entity.getId(), DataPersistenceStatusEnum.PERMANENT));
        return outputDto;
    }
}
