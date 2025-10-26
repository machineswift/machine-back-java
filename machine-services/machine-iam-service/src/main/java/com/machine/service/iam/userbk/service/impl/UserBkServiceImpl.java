package com.machine.service.iam.userbk.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.employee.IDataCompanyEmployeeClient;
import com.machine.client.data.employee.IDataShopEmployeeClient;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeUpdateInputDto;
import com.machine.client.data.leaf.IDataLeaf4ScmCodeClient;
import com.machine.client.data.supplier.IDataSupplierUserClient;
import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.client.iam.userbk.dto.input.*;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.common.GenderEnum;
import com.machine.sdk.common.envm.data.DataShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.hrm.HrmEmployeeStatusEnum;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import com.machine.sdk.common.envm.iam.role.IamShopDefaultRoleEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.tool.StringUtil;
import com.machine.service.iam.user.dao.*;
import com.machine.service.iam.user.dao.mapper.entity.IamUserEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.service.iam.userbk.service.IUserBkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserBkServiceImpl implements IUserBkService {

    @Autowired
    private IIamUserDao userDao;

    @Autowired
    private IIamUserTypeDao userTypeDao;
    @Autowired
    private IIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IDataLeaf4ScmCodeClient leaf4CodeClient;

    @Autowired
    private IDataCompanyEmployeeClient companyEmployeeClient;

    @Autowired
    private IDataShopEmployeeClient shopEmployeeClient;

    @Autowired
    private IDataSupplierUserClient supplierClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createCompanyUser(IamCompanyUserCreateInputDto inputDto) {
        IamUserEntity entityByUserName = userDao.getByUsername(inputDto.getUsername());
        if (null != entityByUserName) {
            log.error("新增公司员工系统账号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("iam.user.createCompanyUser.usernameAlreadyExists", "系统账号已经存在");
        }

        IamUserEntity entityByCode = userDao.getByCode(inputDto.getCode());
        if (null != entityByCode) {
            log.error("新增公司员工编码已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("iam.user.createCompanyUser.codeAlreadyExists", "编码已经存在");
        }

        if (StrUtil.isNotBlank(inputDto.getPhone())) {
            IamUserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
            if (null != entityByPhone) {
                log.error("新增公司员工手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
                throw new IamBusinessException("iam.user.createCompanyUser.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //创建用户
        IamUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        insertEntity.setPassword(StringUtil.generatePassword());
        String userId = userDao.insert(insertEntity);

        //关联类型
        if (userTypeDao.notExists(userId, IamUserTypeEnum.COMPANY)) {
            userTypeDao.insertOrUpdate(userId, IamUserTypeEnum.COMPANY);
        }


        //创建公司员工
        DataCompanyEmployeeCreateInputDto createInputDto = new DataCompanyEmployeeCreateInputDto();
        createInputDto.setUserId(userId);
        createInputDto.setEmployeeStatus(inputDto.getEmployeeStatus());
        companyEmployeeClient.create(createInputDto);

        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createShopUser(IamShopUserCreateInputDto inputDto) {
        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> userRoleInputList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(userRoleInputList)) {
            throw new IamBusinessException("iam.user.createShopUser.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = userRoleInputList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != userRoleInputList.size()) {
            throw new IamBusinessException("iam.user.createShopUser.roleRepeat", "角色重复");
        }

        //验证手机号是否存在
        IamUserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity) {
            throw new IamBusinessException("iam.user.createShopUser.phoneAlreadyExists", "手机号已经存在");
        }

        //生成编码
        String code = null;
        //leaf4CodeClientIam.shopUserCode();

        //创建用户
        IamUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        insertEntity.setUsername(code);
        insertEntity.setStatus(StatusEnum.ENABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setCode(code);
        String userId = userDao.insert(insertEntity);

        //关联类型
        if (userTypeDao.notExists(userId, IamUserTypeEnum.SHOP)) {
            userTypeDao.insertOrUpdate(userId, IamUserTypeEnum.SHOP);
        }

        //关联角色 todo machine

        //创建门店员工
        DataShopEmployeeCreateInputDto createInputDto = new DataShopEmployeeCreateInputDto();
        createInputDto.setUserId(userId);
        createInputDto.setEmployeeStatus(DataShopEmployeeStatusEnum.FULL_TIME);
        createInputDto.setIdentityCard(inputDto.getIdentityCard());
        createInputDto.setHealthCertificate(inputDto.getHealthCertificate());
        shopEmployeeClient.create(createInputDto);
        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createSupplierUser(IamSupplierUserCreateInputDto inputDto) {
        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> userRoleInputList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(userRoleInputList)) {
            throw new IamBusinessException("iam.user.createSupplierUser.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = userRoleInputList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != userRoleInputList.size()) {
            throw new IamBusinessException("iam.user.createSupplierUser.roleRepeat", "角色重复");
        }

        //验证手机号是否存在
        IamUserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity) {
            throw new IamBusinessException("iam.user.createSupplierUser.phoneAlreadyExists", "手机号已经存在");
        }

        String code = leaf4CodeClient.supplierCode();

        //创建用户
        IamUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        insertEntity.setUsername(code);
        insertEntity.setStatus(StatusEnum.ENABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setCode(code);
        String userId = userDao.insert(insertEntity);

        //关联类型
        if (userTypeDao.notExists(userId, IamUserTypeEnum.SUPPLIER)) {
            userTypeDao.insertOrUpdate(userId, IamUserTypeEnum.SUPPLIER);
        }

        //关联角色 todo machine

        //创建供应商
        DataSupplierCreateInputDto createInputDto = new DataSupplierCreateInputDto();
        createInputDto.setUserId(userId);
        createInputDto.setCompanyIdSet(inputDto.getCompanyIdSet());
        supplierClient.create(createInputDto);

        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createFranchiseeUser(IamFranchiseeUserCreateInputDto inputDto) {
        IamUserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
        if (null != entityByPhone) {
            String userId = entityByPhone.getId();

            //添加身份
            if (userTypeDao.notExists(userId, IamUserTypeEnum.FRANCHISEE)) {
                userTypeDao.insertOrUpdate(userId, IamUserTypeEnum.FRANCHISEE);
            }

            //添加角色(加盟商)
            IamUserRoleRelationEntity iamUserRoleRelationEntity = userRoleRelationDao.getByUk(
                    userId, IamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            if (null == iamUserRoleRelationEntity) {
                userRoleRelationDao.insert(userId, IamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            }

            return userId;
        } else {
            //获取编码
            String code = leaf4CodeClient.franchiseeCode();

            //添加用户
            IamUserEntity insertEntity = new IamUserEntity();
            insertEntity.setUsername(code);
            insertEntity.setCode(code);
            insertEntity.setStatus(StatusEnum.ENABLE);
            insertEntity.setPassword(StringUtil.generatePassword());
            insertEntity.setPhone(inputDto.getPhone());
            insertEntity.setName(inputDto.getName());
            insertEntity.setGender(GenderEnum.UNDEFINED);
            String userId = userDao.insert(insertEntity);

            //添加身份
            userTypeDao.insertOrUpdate(userId, IamUserTypeEnum.FRANCHISEE);

            //添加角色(加盟商)
            userRoleRelationDao.insert(userId, IamShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            return userId;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCompanyUser4BeiSen(IamCompanyUserUpdate4BeiSenInputDto inputDto) {
        //修改公司员工状态
        DataCompanyEmployeeUpdateInputDto updateInputDto = new DataCompanyEmployeeUpdateInputDto();
        updateInputDto.setUserId(inputDto.getId());
        updateInputDto.setEmployeeStatus(inputDto.getEmployeeStatus());
        companyEmployeeClient.update(updateInputDto);


        IamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        IamUserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
        if (null != entityByPhone && !entityByPhone.getId().equals(inputDto.getId())) {
            log.error("修改公司员工手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("iam.user.updateCompanyUser.phoneAlreadyExists", "手机号已经存在");
        }

        //修改手机号
        if (!inputDto.getPhone().equals(dbEntity.getPhone())) {
            userDao.updatePhone(dbEntity.getId(), inputDto.getPhone());
        }

        //修改状态
        StatusEnum statusEnum;
        if (HrmEmployeeStatusEnum.FULL_TIME == inputDto.getEmployeeStatus()) {
            statusEnum = StatusEnum.ENABLE;
        } else {
            statusEnum = StatusEnum.DISABLE;
        }
        if (statusEnum != dbEntity.getStatus()) {
            userDao.updateStatus(dbEntity.getId(), statusEnum);
        }
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopUser(IamShopUserUpdateInputDto inputDto) {
        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> userRoleInputList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(userRoleInputList)) {
            throw new IamBusinessException("iam.user.updateShopUser.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = userRoleInputList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != userRoleInputList.size()) {
            throw new IamBusinessException("iam.user.updateShopUser.roleRepeat", "角色重复");
        }

        IamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(
                inputDto.getId(), IamUserTypeEnum.COMPANY))) {
            throw new IamBusinessException("iam.user.updateShopUser.typeIsCompany", "是公司用户不能修改");
        }

        if (!userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(
                inputDto.getId(), List.of(IamUserTypeEnum.SHOP, IamUserTypeEnum.FRANCHISEE)))) {
            throw new IamBusinessException("iam.user.updateShopUser.typeNotShop", "不是门店用户不能修改");
        }

        //验证用户名是否存在
        IamUserEntity usernameEntity = userDao.getByUsername(dbEntity.getUsername());
        if (null != usernameEntity && !usernameEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.updateShopUser.nameAlreadyExists", "用户名已经存在");
        }

        //验证手机号是否存在
        if (StrUtil.isNotEmpty(inputDto.getPhone())) {
            IamUserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
            if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
                throw new IamBusinessException("iam.user.updateShopUser.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //修改基础信息
        IamUserEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        updateEntity.setPhone(null);
        userDao.update(updateEntity);

        //修改手机号
        if (null != inputDto.getPhone() && !inputDto.getPhone().equals(dbEntity.getPhone())) {
            userDao.updatePhone(dbEntity.getId(), inputDto.getPhone());
        }

        //关联角色 todo machine

        //修改门店员工
        DataShopEmployeeUpdateInputDto updateInputDto = new DataShopEmployeeUpdateInputDto();
        updateInputDto.setUserId(inputDto.getId());
        updateInputDto.setIdentityCard(inputDto.getIdentityCard());
        updateInputDto.setHealthCertificate(inputDto.getHealthCertificate());
        return shopEmployeeClient.updateByUserId(updateInputDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSupplierUser(IamSupplierUserUpdateInputDto inputDto) {
        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> userRoleInputList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(userRoleInputList)) {
            throw new IamBusinessException("iam.user.updateSupplierUser.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = userRoleInputList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != userRoleInputList.size()) {
            throw new IamBusinessException("iam.user.updateSupplierUser.roleRepeat", "角色重复");
        }

        IamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(
                inputDto.getId(), IamUserTypeEnum.COMPANY))) {
            throw new IamBusinessException("iam.user.updateSupplierUser.typeIsCompany", "是公司用户不能修改");
        }

        if (userTypeDao.notExists(inputDto.getId(), IamUserTypeEnum.SUPPLIER)) {
            throw new IamBusinessException("iam.user.updateSupplierUser.typeNotShop", "不是供应商不能修改");
        }

        //验证用户名是否存在
        IamUserEntity usernameEntity = userDao.getByUsername(dbEntity.getUsername());
        if (null != usernameEntity && !usernameEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.updateSupplierUser.usernameAlreadyExists", "用户名已经存在");
        }

        //验证手机号是否存在
        if (StrUtil.isNotEmpty(inputDto.getPhone())) {
            IamUserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
            if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
                throw new IamBusinessException("iam.user.updateSupplierUser.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //修改基础信息
        IamUserEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        updateEntity.setPhone(null);
        userDao.update(updateEntity);

        //修改手机号
        if (null != inputDto.getPhone() && !inputDto.getPhone().equals(dbEntity.getPhone())) {
            userDao.updatePhone(dbEntity.getId(), inputDto.getPhone());
        }

        //关联角色 todo machine

        //修改供应商
        DataSupplierUpdateInputDto createInputDto = new DataSupplierUpdateInputDto();
        createInputDto.setUserId(inputDto.getId());
        createInputDto.setCompanyIdSet(inputDto.getCompanyIdSet());
        supplierClient.update(createInputDto);
        return 1;
    }


    @Override
    public Page<IamUserListOutputDto> pageCompany(IamCompanyUserQueryPageInputDto inputDto) {
        Page<IamUserEntity> page = userDao.pageCompany(inputDto);
        Page<IamUserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<IamUserListOutputDto> pageShop(IamShopUserQueryPageInputDto inputDto) {
        Page<IamUserEntity> page = userDao.pageShop(inputDto);
        Page<IamUserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<IamUserListOutputDto> pageSupplier(IamSupplierUserQueryPageInputDto inputDto) {
        Page<IamUserEntity> page = userDao.pageSupplier(inputDto);
        Page<IamUserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserListOutputDto.class));
        return pageResult;
    }

}
