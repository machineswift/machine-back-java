package com.machine.service.data.franchisee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.franchisee.dto.input.*;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeListOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.IIamUserRoleBusinessRelationClient;
import com.machine.client.iam.userbk.IIamUserBkClient;
import com.machine.client.iam.userbk.dto.input.IamFranchiseeUserCreateInputDto;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeBindShopInputDto;
import com.machine.client.iam.userbk.dto.input.IamUserRoleInfoFranchiseeUnBindShopInputDto;
import com.machine.client.iam.user.dto.input.IamUserUpdatePhoneInputDto;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.franchisee.dao.IDataFranchiseeDao;
import com.machine.service.data.franchisee.dao.IDataFranchiseeShopRelationDao;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeEntity;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeShopRelationEntity;
import com.machine.service.data.franchisee.service.IDataFranchiseeService;
import com.machine.service.data.shop.dao.IDataShopDao;
import com.machine.service.data.shop.dao.mapper.entity.DataShopEntity;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class DataFranchiseeServiceImpl implements IDataFranchiseeService {

    @Autowired
    private IDataShopDao shopDao;

    @Autowired
    private IDataFranchiseeDao franchiseeDao;

    @Autowired
    private IDataFranchiseeShopRelationDao franchiseeShopRelationDao;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IIamUserBkClient userBkClient;

    @Autowired
    private IIamUserRoleBusinessRelationClient userRoleBusinessRelationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataFranchiseeCreateInputDto inputDto) {
        //验证编码是否存在
        DataFranchiseeEntity codeEntity = franchiseeDao.getByCode(inputDto.getCode());
        if (null != codeEntity) {
            throw new IamBusinessException("data.franchisee.service.create.codeAlreadyExists", "编码已经存在");
        }

        //验证证件号是否存在
        DataFranchiseeEntity certificateEntity = franchiseeDao.getByCertificate(inputDto.getCertificateType(), inputDto.getCertificateNumber());
        if (null != certificateEntity) {
            throw new IamBusinessException("data.franchisee.service.create.certificateAlreadyExists", "证件号已经存在");
        }

        //根据手机号创建加盟商用户
        String userId = userBkClient.createFranchiseeUser(new IamFranchiseeUserCreateInputDto(inputDto.getPhone(), inputDto.getName()));

        //验证是否存在加盟商
        DataFranchiseeEntity userIdEntity = franchiseeDao.getByUserId(userId);
        if (null != userIdEntity) {
            throw new IamBusinessException("data.franchisee.service.create.franchiseeAlreadyExists", "加盟商已经存在");
        }

        //创建加盟商
        DataFranchiseeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataFranchiseeEntity.class);
        insertEntity.setUserId(userId);
        String franchiseeId = franchiseeDao.insert(insertEntity);

        //身份证信息
        franchiseeDao.updateIdentityCard(franchiseeId, SystemStorageTypeEnum.TEMPORARY, inputDto.getIdentityCard());
        //健康证信息
        franchiseeDao.updateHealthCertificate(franchiseeId, SystemStorageTypeEnum.TEMPORARY, inputDto.getHealthCertificate());
        return franchiseeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindShop(DataFranchiseeBindShopInputDto inputDto) {
        DataFranchiseeEntity entity = franchiseeDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("data.franchisee.service.bindShop.franchiseeNotExists", "加盟商不存在");
        }

        DataShopEntity dbDataShopEntity = shopDao.getByCode(inputDto.getShopCode());
        if (null == dbDataShopEntity) {
            throw new IamBusinessException("data.franchisee.service.bindShop.shopCodeNotExists", "门店编码不存在");
        }

        //加盟商角色绑定的门店
        userRoleBusinessRelationClient.bindFranchiseeShop(
                new IamUserRoleInfoFranchiseeBindShopInputDto(entity.getUserId(), dbDataShopEntity.getId()));

        //添加加盟商和门店的关系
        DataFranchiseeShopRelationEntity dbRelationEntity = franchiseeShopRelationDao.getByShopId(dbDataShopEntity.getId());
        if (null != dbRelationEntity) {
            if (dbRelationEntity.getFranchiseeId().equals(inputDto.getId())) {
                return Boolean.TRUE;
            } else {
                throw new IamBusinessException("data.franchisee.service.bindShop.bindOtherFranchisee", "门店绑定其他加盟商");
            }
        }

        DataFranchiseeShopRelationEntity insertRelation = new DataFranchiseeShopRelationEntity();
        insertRelation.setFranchiseeId(inputDto.getId());
        insertRelation.setShopId(dbDataShopEntity.getId());
        franchiseeShopRelationDao.insert(dbDataShopEntity.getCode(), insertRelation);

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindShop(DataFranchiseeUnbindShopInputDto inputDto) {
        DataFranchiseeEntity entity = franchiseeDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("data.franchisee.service.unbindShop.franchiseeNotExists", "加盟商不存在");
        }

        DataShopEntity dbDataShopEntity = shopDao.getByCode(inputDto.getShopCode());
        if (null == dbDataShopEntity) {
            throw new IamBusinessException("data.franchisee.service.unbindShop.shopCodeNotExists", "门店编码不存在");
        }

        //加盟商角色解绑的门店
        userRoleBusinessRelationClient.unbindFranchiseeShop(
                new IamUserRoleInfoFranchiseeUnBindShopInputDto(entity.getUserId(), dbDataShopEntity.getId()));

        //删除加盟商和门店的关系
        franchiseeShopRelationDao.deleteByUk(inputDto.getId(), dbDataShopEntity.getId(), dbDataShopEntity.getCode());
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhone(OpenApiFranchiseeUpdatePhoneInputDto inputDto) {
        DataFranchiseeEntity entity = franchiseeDao.getById(inputDto.getId());
        if (null == entity) {
            throw new IamBusinessException("data.franchisee.service.unbindShop.franchiseeNotExists", "加盟商不存在");
        }
        return userClient.updatePhone(new IamUserUpdatePhoneInputDto(entity.getUserId(), inputDto.getPhone()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataFranchiseeUpdateInputDto inputDto) {
        DataFranchiseeEntity dbEntity = franchiseeDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }
        DataFranchiseeEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataFranchiseeEntity.class);

        //身份证信息
        franchiseeDao.updateIdentityCard(updateEntity.getId(), SystemStorageTypeEnum.PERMANENT, inputDto.getIdentityCard());
        //健康证信息
        franchiseeDao.updateHealthCertificate(updateEntity.getId(), SystemStorageTypeEnum.TEMPORARY, inputDto.getHealthCertificate());
        return franchiseeDao.update(updateEntity);
    }

    @Override
    public DataFranchiseeDetailOutputDto detail(IdRequest request) {
        DataFranchiseeEntity entity = franchiseeDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return getDataFranchiseeDetailOutputDto(entity);
    }

    @Override
    public DataFranchiseeDetailOutputDto getByUserId(IdRequest request) {
        DataFranchiseeEntity entity = franchiseeDao.getByUserId(request.getId());
        if (null == entity) {
            return null;
        }
        return getDataFranchiseeDetailOutputDto(entity);
    }

    @Override
    public DataFranchiseeDetailOutputDto detailByCode(IdRequest request) {
        DataFranchiseeEntity entity = franchiseeDao.getByCode(request.getId());
        if (null == entity) {
            return null;
        }
        return getDataFranchiseeDetailOutputDto(entity);
    }


    @Override
    public OpenapiFranchiseeIdentityCardOutputDto identityCard(IdRequest request) {
        OpenapiFranchiseeIdentityCardOutputDto outputDto = new OpenapiFranchiseeIdentityCardOutputDto();
        outputDto.setTemporaryIdentityCard(franchiseeDao.getIdentityCard(request.getId(), SystemStorageTypeEnum.TEMPORARY));
        outputDto.setPermanentIdentityCard(franchiseeDao.getIdentityCard(request.getId(), SystemStorageTypeEnum.PERMANENT));
        return outputDto;
    }

    @Override
    public OpenapiFranchiseeHealthCertificateOutputDto healthCertificate(IdRequest request) {
        OpenapiFranchiseeHealthCertificateOutputDto outputDto = new OpenapiFranchiseeHealthCertificateOutputDto();
        outputDto.setTemporaryHealthCertificate(franchiseeDao.getHealthCertificated(request.getId(), SystemStorageTypeEnum.TEMPORARY));
        outputDto.setPermanentHealthCertificate(franchiseeDao.getHealthCertificated(request.getId(), SystemStorageTypeEnum.PERMANENT));
        return outputDto;
    }

    @Override
    public Set<String> listUserIdByCondition(DataFranchiseeListUserIdInputDto inputDto) {
        return franchiseeDao.listUserIdByCondition(inputDto);
    }

    @Override
    public List<DataFranchiseeListOutputDto> listByOffset(DataFranchiseeQueryListOffsetInputDto inputDto) {
        List<DataFranchiseeEntity> entityList = franchiseeDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataFranchiseeListOutputDto.class);
    }

    private @NotNull DataFranchiseeDetailOutputDto getDataFranchiseeDetailOutputDto(DataFranchiseeEntity entity) {
        DataFranchiseeDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFranchiseeDetailOutputDto.class);

        //获取门店id
        List<DataFranchiseeShopRelationEntity> relationEntityList = franchiseeShopRelationDao.getByFranchiseeId(entity.getId());
        if (CollectionUtil.isNotEmpty(relationEntityList)) {
            outputDto.setBindShopIdList(relationEntityList.stream().map(DataFranchiseeShopRelationEntity::getShopId).toList());
        }

        //身份证信息
        outputDto.setIdentityCard(franchiseeDao.getIdentityCard(entity.getId(), SystemStorageTypeEnum.PERMANENT));
        outputDto.setHealthCertificate(franchiseeDao.getHealthCertificated(entity.getId(), SystemStorageTypeEnum.PERMANENT));
        return outputDto;
    }
}
