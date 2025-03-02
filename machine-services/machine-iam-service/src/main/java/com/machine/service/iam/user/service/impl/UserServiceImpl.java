package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.employee.ICompanyEmployeeClient;
import com.machine.client.data.employee.IShopEmployeeClient;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataCompanyEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeCreateInputDto;
import com.machine.client.data.employee.dto.input.DataShopEmployeeUpdateInputDto;
import com.machine.client.data.employee.dto.output.ShopEmployeeDetailOutputDto;
import com.machine.client.data.leaf.IDataLeaf4CodeClient;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.input.DataShopCodeInoutDto;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.client.data.supplier.ISupplierCompanyClient;
import com.machine.client.data.supplier.ISupplierUserClient;
import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
import com.machine.client.data.supplier.dto.output.DataSupplierCompanySimpleListOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.client.iam.user.dto.output.UserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.UserDetailOutputDto;
import com.machine.client.iam.user.dto.UserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.UserListOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import com.machine.sdk.common.envm.data.MaterIalTypeEnum;
import com.machine.sdk.common.envm.data.ShopEmployeeStatusEnum;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.StringUtil;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.sdk.huawei.client.file.HuaWeiObsHttpClient;
import com.machine.sdk.huawei.domain.UploadParamDto;
import com.machine.sdk.huawei.util.UploadParamUtil;
import com.machine.service.iam.organization.dao.IOrganizationDao;
import com.machine.service.iam.organization.dao.mapper.entity.OrganizationEntity;
import com.machine.service.iam.permission.dao.IPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;
import com.machine.service.iam.role.dao.IRoleDao;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;
import com.machine.service.iam.user.dao.IUserDao;
import com.machine.service.iam.user.dao.IUserRoleTargetRelationDao;
import com.machine.service.iam.user.dao.IUserTypeDao;
import com.machine.service.iam.user.dao.mapper.entity.UserEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import com.machine.service.iam.user.service.IUserService;
import com.machine.service.iam.user.service.bo.ShopUserExportBo;
import com.machine.starter.redis.cache.RedisCacheIamPermission;
import com.obs.services.model.PutObjectResult;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.DOWNLOAD_FILE_EXPIRATION_DAYS;
import static com.machine.sdk.common.constant.CommonConstant.Iam.ROOT_USER_ID;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource(name = "huaWeiObsHttpClient")
    private HuaWeiObsHttpClient huaWeiObsHttpClient;

    @Autowired
    private RedisCacheIamPermission permissionCache;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserTypeDao userTypeDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IDataShopClient shopClient;

    @Autowired
    private IOrganizationDao organizationDao;

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IUserRoleTargetRelationDao userRoleTargetRelationDao;

    @Autowired
    private IDataLeaf4CodeClient leaf4CodeClient;

    @Autowired
    private ICompanyEmployeeClient companyEmployeeClient;

    @Autowired
    private IShopEmployeeClient shopEmployeeClient;

    @Autowired
    private ISupplierUserClient supplierClient;

    @Autowired
    private ISupplierCompanyClient supplierCompanyClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createCompanyUser(IamCompanyUserCreateInputDto inputDto) {
        UserEntity entityByUserName = userDao.getByUserName(inputDto.getUserName());
        if (null != entityByUserName) {
            log.error("新增公司员工系统账号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("iam.user.createCompanyUser.userNameAlreadyExists", "系统账号已经存在");
        }

        UserEntity entityByCode = userDao.getByCode(inputDto.getCode());
        if (null != entityByCode) {
            log.error("新增公司员工编码已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("iam.user.createCompanyUser.codeAlreadyExists", "编码已经存在");
        }

        if (StrUtil.isNotBlank(inputDto.getPhone())) {
            UserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
            if (null != entityByPhone) {
                log.error("新增公司员工手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
                throw new IamBusinessException("iam.user.createCompanyUser.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //创建用户
        UserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserEntity.class);
        insertEntity.setPassword(StringUtil.generatePassword());
        String userId = userDao.insert(insertEntity);

        //关联类型
        if (!userTypeDao.exists(userId, UserTypeEnum.COMPANY)) {
            userTypeDao.insertOrUpdate(userId, UserTypeEnum.COMPANY);
        }

        //关联角色
        insertUserRoleInfo4Company(userId, inputDto.getRoleIdSet());

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
        UserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity) {
            throw new IamBusinessException("iam.user.createShopUser.phoneAlreadyExists", "手机号已经存在");
        }

        //生成编码
        String code = leaf4CodeClient.iamUserMdyg();

        //创建用户
        UserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserEntity.class);
        insertEntity.setUserName(code);
        insertEntity.setStatus(StatusEnum.ENABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setCode(code);
        String userId = userDao.insert(insertEntity);

        //关联类型
        if (!userTypeDao.exists(userId, UserTypeEnum.SHOP)) {
            userTypeDao.insertOrUpdate(userId, UserTypeEnum.SHOP);
        }

        //关联角色
        insertUserRoleInfo(userId, inputDto.getUserRoleList());

        //创建门店员工
        DataShopEmployeeCreateInputDto createInputDto = new DataShopEmployeeCreateInputDto();
        createInputDto.setUserId(userId);
        createInputDto.setEmployeeStatus(ShopEmployeeStatusEnum.FULL_TIME);
        createInputDto.setIdentityCard(inputDto.getIdentityCard());
        createInputDto.setHealthCertificate(inputDto.getHealthCertificate());
        shopEmployeeClient.create(createInputDto);
        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createShopUser4Openapi(IamShopUserCreate4OpenapiInputDto inputDto) {
        //验证角色是否正确
        String roleCode = inputDto.getRoleCode();
        if (!ShopDefaultRoleEnum.STORE_MANAGER.getName().equals(roleCode) &&
                !ShopDefaultRoleEnum.SALES_CLERK.getName().equals(roleCode)) {
            throw new IamBusinessException("iam.user.createShopUser4Openapi.roleCodeWrong", "角色编码错误");
        }

        //验证门店编码是否存在
        DataShopDetailOutputDto dataShopDetailOutputDto = shopClient.getByCode(new DataShopCodeInoutDto(inputDto.getShopCode()));
        if (null == dataShopDetailOutputDto) {
            throw new IamBusinessException("iam.user.createShopUser4Openapi.shopCodeNotExists", "门店编码不存在");
        }

        String userId;
        UserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null == phoneEntity) {
            //生成门店员工编码
            String code = leaf4CodeClient.iamUserMdyg();

            //创建用户
            UserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserEntity.class);
            insertEntity.setUserName(code);
            insertEntity.setStatus(StatusEnum.ENABLE);
            insertEntity.setPassword(StringUtil.generatePassword());
            insertEntity.setCode(code);
            userId = userDao.insert(insertEntity);
        } else {
            userId = phoneEntity.getId();
        }

        //创建门店员工
        String employeeId;
        ShopEmployeeDetailOutputDto outputDto = shopEmployeeClient.getByUserId(new IdRequest(userId));
        if (null == outputDto) {
            //增加身份
            userTypeDao.insertOrUpdate(userId, UserTypeEnum.SHOP);

            //创建门店员工
            DataShopEmployeeCreateInputDto createInputDto = new DataShopEmployeeCreateInputDto();
            createInputDto.setUserId(userId);
            createInputDto.setEmployeeStatus(ShopEmployeeStatusEnum.FULL_TIME);
            createInputDto.setIdentityCard(inputDto.getIdentityCard());
            createInputDto.setHealthCertificate(inputDto.getHealthCertificate());
            employeeId = shopEmployeeClient.create(createInputDto);
        } else {
            employeeId = outputDto.getId();
        }

        //关联角色
        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao.
                listByUserAndRoleIdId(userId, roleCode.toLowerCase());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            UserRoleTargetRelationEntity insertRelationEntity = new UserRoleTargetRelationEntity();
            insertRelationEntity.setUserId(userId);
            insertRelationEntity.setRoleId(roleCode.toLowerCase());
            insertRelationEntity.setTargetId(dataShopDetailOutputDto.getId());
            insertRelationEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
            userRoleTargetRelationDao.insert(userId, insertRelationEntity);
        } else {
            UserRoleTargetRelationEntity dBRelationEntity = null;
            for (UserRoleTargetRelationEntity relationEntity : relationEntityList) {
                if (roleCode.toLowerCase().equals(relationEntity.getRoleId())) {
                    if (null == relationEntity.getTargetType()) {
                        //为空
                        dBRelationEntity = relationEntity;
                        break;
                    } else {
                        if (relationEntity.getTargetId().equals(dataShopDetailOutputDto.getId())) {
                            dBRelationEntity = relationEntity;
                            break;
                        }
                    }
                }
            }

            if (null == dBRelationEntity) {
                UserRoleTargetRelationEntity insertRelationEntity = new UserRoleTargetRelationEntity();
                insertRelationEntity.setUserId(userId);
                insertRelationEntity.setRoleId(roleCode.toLowerCase());
                insertRelationEntity.setTargetId(dataShopDetailOutputDto.getId());
                insertRelationEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
                userRoleTargetRelationDao.insert(userId, insertRelationEntity);
            } else {
                if (null == dBRelationEntity.getTargetType()) {
                    dBRelationEntity.setTargetType(UserRoleTargetTypeEnum.SHOP);
                    dBRelationEntity.setTargetId(dataShopDetailOutputDto.getId());
                    userRoleTargetRelationDao.update(dBRelationEntity);
                } else {
                    throw new IamBusinessException("iam.user.createShopUser4Openapi.shopEmployeeAlreadyExists", "门店员工已存在");
                }
            }
        }
        return employeeId;
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
        UserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity) {
            throw new IamBusinessException("iam.user.createSupplierUser.phoneAlreadyExists", "手机号已经存在");
        }

        String code = leaf4CodeClient.iamUserGysyg();

        //创建用户
        UserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserEntity.class);
        insertEntity.setUserName(code);
        insertEntity.setStatus(StatusEnum.ENABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setCode(code);
        String userId = userDao.insert(insertEntity);

        //关联类型
        if (!userTypeDao.exists(userId, UserTypeEnum.SUPPLIER)) {
            userTypeDao.insertOrUpdate(userId, UserTypeEnum.SUPPLIER);
        }

        //关联角色
        insertUserRoleInfo(userId, inputDto.getUserRoleList());

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
        UserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
        if (null != entityByPhone) {
            String userId = entityByPhone.getId();

            //添加身份
            if (!userTypeDao.exists(userId, UserTypeEnum.FRANCHISEE)) {
                userTypeDao.insertOrUpdate(userId, UserTypeEnum.FRANCHISEE);
            }

            //添加角色(加盟商)
            List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao.listByUserAndRoleIdId(
                    userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            if (CollectionUtil.isEmpty(relationEntityList)) {
                UserRoleTargetRelationEntity relationInsertEntity = new UserRoleTargetRelationEntity();
                relationInsertEntity.setUserId(userId);
                relationInsertEntity.setRoleId(ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
                userRoleTargetRelationDao.insert(userId, relationInsertEntity);
            }

            return userId;
        }

        //获取编码
        String code = leaf4CodeClient.iamUserJmsyg();

        //添加用户
        UserEntity insertEntity = new UserEntity();
        insertEntity.setUserName(code);
        insertEntity.setCode(code);
        insertEntity.setStatus(StatusEnum.ENABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setPhone(inputDto.getPhone());
        insertEntity.setName(inputDto.getName());
        insertEntity.setGender(GenderEnum.UNDEFINED);
        String userId = userDao.insert(insertEntity);

        //添加身份
        if (!userTypeDao.exists(userId, UserTypeEnum.FRANCHISEE)) {
            userTypeDao.insertOrUpdate(userId, UserTypeEnum.FRANCHISEE);
        }

        //添加角色(加盟商)
        UserRoleTargetRelationEntity relationInsertEntity = new UserRoleTargetRelationEntity();
        relationInsertEntity.setUserId(userId);
        relationInsertEntity.setRoleId(ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
        userRoleTargetRelationDao.insert(userId, relationInsertEntity);

        return userId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(IamUserUpdateStatusInputDto inputDto) {
        return userDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhone(IamUserUpdatePhoneInputDto inputDto) {
        UserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.updatePhone.phoneAlreadyExists", "手机号已经存在");
        }
        return userDao.updatePhone(inputDto.getId(), inputDto.getPhone());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePassword(IamUserUpdatePasswordInputDto dto) {
        UserEntity entity = userDao.getById(dto.getUserId());
        if (null == entity) {
            return 0;
        }
        return userDao.updatePassword(dto.getUserId(), dto.getPassword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCompanyUser(IamCompanyUserUpdateInputDto inputDto) {
        UserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (!userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(inputDto.getId(), UserTypeEnum.COMPANY))) {
            throw new IamBusinessException("iam.user.updateShopUser.typeNotCompany", "不是公司用户不能修改");
        }

        //关联角色
        userRoleTargetRelationDao.deleteByUserId(inputDto.getId());
        insertUserRoleInfo(inputDto.getId(), inputDto.getUserRoleList());
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCompanyUser4BeiSen(IamCompanyUserUpdate4BeiSenInputDto inputDto) {
        //修改公司员工状态
        DataCompanyEmployeeUpdateInputDto updateInputDto = new DataCompanyEmployeeUpdateInputDto();
        updateInputDto.setUserId(inputDto.getId());
        updateInputDto.setEmployeeStatus(inputDto.getEmployeeStatus());
        companyEmployeeClient.update(updateInputDto);


        UserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        UserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
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
        if (CompanyEmployeeStatusEnum.FULL_TIME == inputDto.getEmployeeStatus()) {
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

        UserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(
                inputDto.getId(), UserTypeEnum.COMPANY))) {
            throw new IamBusinessException("iam.user.updateShopUser.typeIsCompany", "是公司用户不能修改");
        }

        if (!userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(
                inputDto.getId(), List.of(UserTypeEnum.SHOP, UserTypeEnum.FRANCHISEE)))) {
            throw new IamBusinessException("iam.user.updateShopUser.typeNotShop", "不是门店用户不能修改");
        }

        //验证用户名是否存在
        UserEntity userNameEntity = userDao.getByUserName(dbEntity.getUserName());
        if (null != userNameEntity && !userNameEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.updateShopUser.nameAlreadyExists", "用户名已经存在");
        }

        //验证手机号是否存在
        if (StrUtil.isNotEmpty(inputDto.getPhone())) {
            UserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
            if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
                throw new IamBusinessException("iam.user.updateShopUser.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //修改基础信息
        UserEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserEntity.class);
        updateEntity.setPhone(null);
        userDao.update(updateEntity);

        //修改手机号
        if (null != inputDto.getPhone() && !inputDto.getPhone().equals(dbEntity.getPhone())) {
            userDao.updatePhone(dbEntity.getId(), inputDto.getPhone());
        }

        //关联角色
        userRoleTargetRelationDao.deleteByUserId(inputDto.getId());
        insertUserRoleInfo(inputDto.getId(), inputDto.getUserRoleList());

        //修改门店员工
        DataShopEmployeeUpdateInputDto updateInputDto = new DataShopEmployeeUpdateInputDto();
        updateInputDto.setUserId(inputDto.getId());
        updateInputDto.setIdentityCard(inputDto.getIdentityCard());
        updateInputDto.setHealthCertificate(inputDto.getHealthCertificate());
        return shopEmployeeClient.updateByUserId(updateInputDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateShopUserRole(IamShopUserUpdateRoleInputDto inputDto) {
        String userId = inputDto.getId();
        //验证角色是否重复
        List<IamShopUserUpdateRoleInputDto.UserRoleUpdateDto> userRoleInputList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(userRoleInputList)) {
            throw new IamBusinessException("iam.user.updateShopUserRole.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = userRoleInputList.stream().map(IamShopUserUpdateRoleInputDto.UserRoleUpdateDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != userRoleInputList.size()) {
            throw new IamBusinessException("iam.user.updateShopUserRole.roleRepeat", "角色重复");
        }

        UserEntity dbEntity = userDao.getById(userId);
        if (null == dbEntity) {
            throw new IamBusinessException("iam.user.updateShopUserRole.userNotExists", "用户不存在");
        }

        if (!userTypeDao.exists(userId, UserTypeEnum.SHOP)) {
            throw new IamBusinessException("iam.user.updateShopUserRole.typeNotShop", "不是门店员工不能修改");
        }

        //查询角色
        List<RoleEntity> dbRoleEntityList = roleDao.selectByIdSet(inputRoleIdSet);
        if (CollectionUtil.isEmpty(dbRoleEntityList) || dbRoleEntityList.size() != inputRoleIdSet.size()) {
            for (RoleEntity roleEntity : dbRoleEntityList) {
                inputRoleIdSet.remove(roleEntity.getId());
            }
            throw new IamBusinessException("iam.user.updateShopUserRole.roleNotExists", "角色不存在，roleId:" + JSONUtil.toJsonStr(inputRoleIdSet));
        }

        for (RoleEntity entity : dbRoleEntityList) {
            if (ShopDefaultRoleEnum.FRANCHISEE.getName().equals(entity.getCode())) {
                throw new IamBusinessException("iam.user.updateShopUserRole.franchiseeRole", "不支持修改加盟商角色");
            }
            if (RoleTypeEnum.SHOP != entity.getType()) {
                throw new IamBusinessException("iam.user.updateShopUserRole.notShopRole", "不支持修改非门店类型角色");
            }
        }

        //删除角色
        List<UserRoleTargetRelationEntity> dbTTargetRelationEntityList = userRoleTargetRelationDao.listByUserId(userId);
        Set<String> deleteIdSet = new HashSet<>();
        for (UserRoleTargetRelationEntity dbTargetRelationEntity : dbTTargetRelationEntityList) {
            RoleEntity roleEntity = roleDao.getById(dbTargetRelationEntity.getRoleId());
            if (null == roleEntity) {
                deleteIdSet.add(dbTargetRelationEntity.getId());
            } else if (RoleTypeEnum.SHOP == roleEntity.getType() &&
                    !ShopDefaultRoleEnum.FRANCHISEE.getName().equals(roleEntity.getCode())) {
                deleteIdSet.add(dbTargetRelationEntity.getId());
            }
        }
        if (CollectionUtil.isNotEmpty(deleteIdSet)) {
            userRoleTargetRelationDao.deleteByIdSet(userId, deleteIdSet);
        }

        //新增角色
        List<UserRoleTargetRelationEntity> insertEntityList = new ArrayList<>();
        for (IamShopUserUpdateRoleInputDto.UserRoleUpdateDto dto : userRoleInputList) {
            Set<String> shopIdSet = dto.getShopIdSet();
            if (CollectionUtil.isEmpty(shopIdSet)) {
                throw new IamBusinessException("iam.user.updateShopUserRole.shopIdEmpty", "门店信息为空，roleId:" + dto.getRoleId());
            }

            List<DataShopDetailOutputDto> shopDetailOutputDtoList = shopClient.listByIdSet(new IdSetRequest(shopIdSet));
            if (CollectionUtil.isEmpty(shopDetailOutputDtoList) || shopIdSet.size() != shopDetailOutputDtoList.size()) {
                for (DataShopDetailOutputDto outputDto : shopDetailOutputDtoList) {
                    shopIdSet.remove(outputDto.getId());
                }
                throw new IamBusinessException("iam.user.updateShopUserRole.shopNotExists",
                        "门店不存在，shopId:" + JSONUtil.toJsonStr(shopIdSet));
            }

            for (String shopId : shopIdSet) {
                UserRoleTargetRelationEntity entity = new UserRoleTargetRelationEntity();
                entity.setUserId(userId);
                entity.setRoleId(dto.getRoleId());


                entity.setTargetId(shopId);
                entity.setTargetType(UserRoleTargetTypeEnum.SHOP);
                entity.setSort(0L);
                insertEntityList.add(entity);
            }
        }
        userRoleTargetRelationDao.batchInsert(userId, insertEntityList);
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

        UserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (userTypeDao.existsType(new IamUserTypeExistsTypeInputDto(
                inputDto.getId(), UserTypeEnum.COMPANY))) {
            throw new IamBusinessException("iam.user.updateSupplierUser.typeIsCompany", "是公司用户不能修改");
        }

        if (!userTypeDao.exists(inputDto.getId(), UserTypeEnum.SUPPLIER)) {
            throw new IamBusinessException("iam.user.updateSupplierUser.typeNotShop", "不是供应商不能修改");
        }

        //验证用户名是否存在
        UserEntity userNameEntity = userDao.getByUserName(dbEntity.getUserName());
        if (null != userNameEntity && !userNameEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.updateSupplierUser.nameAlreadyExists", "用户名已经存在");
        }

        //验证手机号是否存在
        if (StrUtil.isNotEmpty(inputDto.getPhone())) {
            UserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
            if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
                throw new IamBusinessException("iam.user.updateSupplierUser.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //修改基础信息
        UserEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), UserEntity.class);
        updateEntity.setPhone(null);
        userDao.update(updateEntity);

        //修改手机号
        if (null != inputDto.getPhone() && !inputDto.getPhone().equals(dbEntity.getPhone())) {
            userDao.updatePhone(dbEntity.getId(), inputDto.getPhone());
        }

        //关联角色
        userRoleTargetRelationDao.deleteByUserId(inputDto.getId());
        insertUserRoleInfo(inputDto.getId(), inputDto.getUserRoleList());

        //修改供应商
        DataSupplierUpdateInputDto createInputDto = new DataSupplierUpdateInputDto();
        createInputDto.setUserId(inputDto.getId());
        createInputDto.setCompanyIdSet(inputDto.getCompanyIdSet());
        supplierClient.update(createInputDto);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserRole(IamCompanyUserUpdateInputDto inputDto) {
        String userId = inputDto.getId();

        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> userRoleInputList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(userRoleInputList)) {
            throw new IamBusinessException("iam.user.updateSupplierUser.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = userRoleInputList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != userRoleInputList.size()) {
            throw new IamBusinessException("iam.user.updateSupplierUser.roleRepeat", "角色重复");
        }

        UserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        //删除角色
        userRoleTargetRelationDao.deleteByRoleIdSet(userId, userRoleInputList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet()));

        //新增角色
        List<UserRoleTargetRelationEntity> insertEntityList = new ArrayList<>();
        for (IamUserRoleInfoUpdateInputDto dto : userRoleInputList) {
            Set<String> shopIdSet = dto.getShopIdSet();
            Set<String> organizationIdSet = dto.getOrganizationIdSet();

            if (CollectionUtil.isNotEmpty(shopIdSet)) {
                for (String shopId : shopIdSet) {
                    UserRoleTargetRelationEntity entity = new UserRoleTargetRelationEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(dto.getRoleId());


                    entity.setTargetId(shopId);
                    entity.setTargetType(UserRoleTargetTypeEnum.SHOP);
                    entity.setSort(0L);
                    insertEntityList.add(entity);
                }
            }

            if (CollectionUtil.isNotEmpty(organizationIdSet)) {
                for (String organizationId : organizationIdSet) {
                    UserRoleTargetRelationEntity entity = new UserRoleTargetRelationEntity();
                    entity.setUserId(userId);
                    entity.setRoleId(dto.getRoleId());


                    entity.setTargetId(organizationId);
                    entity.setTargetType(UserRoleTargetTypeEnum.ORGANIZATION);
                    entity.setSort(0L);
                    insertEntityList.add(entity);
                }
            }
        }
        userRoleTargetRelationDao.batchInsert(userId, insertEntityList);
        return 1;
    }

    @Override
    public int countNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto) {
        return userDao.countNotBindOrganization(inputDto);
    }

    @Override
    public UserDetailOutputDto detail(IdRequest request) {
        UserEntity entity = userDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), UserDetailOutputDto.class);
    }

    @Override
    public UserAuthDetailOutputDto detailAuth(IdRequest request) {
        //查询用户信息
        UserEntity userEntity = userDao.getById(request.getId());
        if (null == userEntity) {
            return null;
        }


        UserAuthDetailOutputDto detailDto = new UserAuthDetailOutputDto();
        detailDto.setUserId(userEntity.getId());
        detailDto.setUserName(userEntity.getUserName());
        detailDto.setPassword(userEntity.getPassword());
        detailDto.setName(userEntity.getName());
        detailDto.setPhone(userEntity.getPhone());
        detailDto.setStatus(userEntity.getStatus());

        if (ROOT_USER_ID.equals(userEntity.getId())) {
            //root用户拥有所有的权限
            //角色编码
            List<String> roleCodeList = roleDao.listAllCode();

            //权限编码
            PermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
            List<PermissionTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(allTreeOutputDto);
            Set<String> permissionCodeSet = outputDtoList.stream().map(PermissionTreeOutputDto::getCode).collect(Collectors.toSet());

            detailDto.setRoleCodeList(roleCodeList);
            detailDto.setPermissionCodeList(new ArrayList<>(permissionCodeSet));
        } else {
            //查询用户角色信息
            List<RoleEntity> roleEntityList = roleDao.selectByUserId(request.getId());
            List<PermissionEntity> permissionsByRoleIds = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(roleEntityList)) {
                permissionsByRoleIds = permissionDao.selectByRoleIds(
                        roleEntityList.stream().map(RoleEntity::getId).collect(Collectors.toList()));
            }
            if (CollectionUtil.isNotEmpty(roleEntityList)) {
                detailDto.setRoleCodeList(roleEntityList.stream().map(RoleEntity::getCode).collect(Collectors.toList()));
            }

            //查询角色权限信息
            List<PermissionEntity> permissionEntityList = permissionDao.selectByUserId(request.getId());
            Set<String> permissionIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(permissionEntityList)) {
                permissionIdSet.addAll(permissionEntityList.stream().map(PermissionEntity::getId).toList());
            }
            if (CollectionUtil.isNotEmpty(permissionsByRoleIds)) {
                permissionIdSet.addAll(permissionsByRoleIds.stream().map(PermissionEntity::getId).toList());
            }

            PermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
            Set<PermissionTreeOutputDto> permissionWithParentNodeSet = TreeUtil.getAllParentNodes(allTreeOutputDto, permissionIdSet);

            detailDto.setPermissionCodeList(permissionWithParentNodeSet.stream()
                    .map(PermissionTreeOutputDto::getCode).collect(Collectors.toList()));
        }
        return detailDto;
    }

    @Override
    public UserDto getByUserId(String userId) {
        UserEntity entity = userDao.getById(userId);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public UserDto getByUserName(String userName) {
        UserEntity entity = userDao.getByUserName(userName);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public UserDto getByPhone(String phone) {
        UserEntity entity = userDao.getByPhone(phone);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public List<String> listNotBindOrganization(DataUserNotBindOrganizationInputDto inputDto) {
        return userDao.listNotBindOrganization(inputDto);
    }

    @Override
    public Map<String, UserDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<UserEntity> userEntityList = userDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(UserEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), UserDetailOutputDto.class)));
    }

    @Override
    public List<UserListOutputDto> listByOffset(IamUserQueryListOffsetInputDto inputDto) {
        List<UserEntity> entityList = userDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), UserListOutputDto.class);
    }

    @Override
    public Page<UserListOutputDto> page(IamUserQueryPageInputDto inputDto) {
        Page<UserEntity> page = userDao.page(inputDto);
        Page<UserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), UserListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<UserListOutputDto> pageCompany(IamCompanyUserQueryPageInputDto inputDto) {
        Page<UserEntity> page = userDao.pageCompany(inputDto);
        Page<UserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), UserListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<UserListOutputDto> pageShop(IamShopUserQueryPageInputDto inputDto) {
        Page<UserEntity> page = userDao.pageShop(inputDto);
        Page<UserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), UserListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<UserListOutputDto> pageSupplier(IamSupplierUserQueryPageInputDto inputDto) {
        Page<UserEntity> page = userDao.pageSupplier(inputDto);
        Page<UserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), UserListOutputDto.class));
        return pageResult;
    }

    @Override
    public MaterialDto exportShopUser(IamShopUserExportInputDto inputDto) {
        List<UserEntity> entityList = userDao.listShopUser4Export(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return null;
        }

        List<ShopUserExportBo> exportBoList = new ArrayList<>();
        for (UserEntity entity : entityList) {
            ShopUserExportBo exportBo = new ShopUserExportBo();
            exportBo.setId(entity.getId());
            exportBo.setUserName(entity.getUserName());
            exportBo.setStatus(entity.getStatus().getMsg());
            exportBo.setCode(entity.getCode());
            exportBo.setName(entity.getName());
            exportBo.setPhone(entity.getPhone());
            exportBo.setGender(entity.getGender().getMsg());
            exportBoList.add(exportBo);
        }

        String fileName = "门店用户.xlsx";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FastExcel.write(outputStream, ShopUserExportBo.class).sheet("门店用户").doWrite(exportBoList);
        byte[] bytes = outputStream.toByteArray();
        UploadParamDto uploadParamDto = UploadParamUtil.get4Download(new ByteArrayInputStream(bytes),
                fileName, DOWNLOAD_FILE_EXPIRATION_DAYS + 30);

        PutObjectResult putObjectResult;
        try {
            putObjectResult = huaWeiObsHttpClient.uploadFileByExpires(uploadParamDto);
        } catch (Exception e) {
            log.error("门店用户导出异常", e);
            throw new IamBusinessException("iam.user.exportShop.uploadException", "上传文件失败", e);
        }

        MaterialDto materialDto = new MaterialDto();
        materialDto.setType(MaterIalTypeEnum.FILE);
        materialDto.setSize((long) bytes.length);
        materialDto.setName(fileName);
        materialDto.setUrl(putObjectResult.getObjectUrl());
        return materialDto;
    }

    /**
     * 关联角色（公司用户）
     */
    private void insertUserRoleInfo4Company(String userId,
                                            Set<String> roleIdSet) {
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return;
        }
        List<RoleEntity> roleEntityList = roleDao.selectByIdSet(roleIdSet);
        if (CollectionUtil.isEmpty(roleEntityList)) {
            return;
        }

        List<UserRoleTargetRelationEntity> insertEntityList = new ArrayList<>(roleEntityList.size());
        for (RoleEntity roleEntity : roleEntityList) {
            UserRoleTargetRelationEntity e = new UserRoleTargetRelationEntity();
            e.setUserId(userId);
            e.setRoleId(roleEntity.getId());
            insertEntityList.add(e);
        }
        userRoleTargetRelationDao.batchInsert(userId, insertEntityList);
    }

    /**
     * 关联角色（门店员工/供应商员工）
     */
    private void insertUserRoleInfo(String userId,
                                    List<IamUserRoleInfoUpdateInputDto> userRoleList) {
        if (CollectionUtil.isEmpty(userRoleList)) {
            return;
        }

        Set<String> inputRoleIdset = new HashSet<>();
        Set<String> inputShopIdSet = new HashSet<>();
        Set<String> inputOrganizationIdSet = new HashSet<>();
        Set<String> inputCompanyIdSet = new HashSet<>();

        for (IamUserRoleInfoUpdateInputDto inputDto : userRoleList) {
            inputRoleIdset.add(inputDto.getRoleId());

            if (CollectionUtil.isNotEmpty(inputDto.getShopIdSet())) {
                inputShopIdSet.addAll(inputDto.getShopIdSet());
            }

            if (CollectionUtil.isNotEmpty(inputDto.getOrganizationIdSet())) {
                inputOrganizationIdSet.addAll(inputDto.getOrganizationIdSet());
            }

            if (CollectionUtil.isNotEmpty(inputDto.getCompanyIdSet())) {
                inputCompanyIdSet.addAll(inputDto.getCompanyIdSet());
            }
        }

        List<RoleEntity> roleEntityList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inputRoleIdset)) {
            roleEntityList = roleDao.selectByIdSet(inputRoleIdset);
        }
        List<DataShopDetailOutputDto> dataShopDetailOutputDtoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inputShopIdSet)) {
            dataShopDetailOutputDtoList = shopClient.listByIdSet(new IdSetRequest(inputShopIdSet));
        }
        List<OrganizationEntity> organizationEntityList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inputOrganizationIdSet)) {
            organizationEntityList = organizationDao.selectByIdSet(inputOrganizationIdSet);
        }

        List<DataSupplierCompanySimpleListOutputDto> companyOutputList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inputCompanyIdSet)) {
            companyOutputList = supplierCompanyClient.listByIdSet(new IdSetRequest(inputCompanyIdSet));
        }

        Set<String> dbRoleIdset = roleEntityList.stream().map(RoleEntity::getId).collect(Collectors.toSet());
        Set<String> dbShopIdSet = dataShopDetailOutputDtoList.stream().map(DataShopDetailOutputDto::getId).collect(Collectors.toSet());
        Set<String> dbOrganizationIdSet = organizationEntityList.stream().map(OrganizationEntity::getId).collect(Collectors.toSet());
        Set<String> dbCompanyIdSet = companyOutputList.stream().map(DataSupplierCompanySimpleListOutputDto::getId).collect(Collectors.toSet());


        List<UserRoleTargetRelationEntity> insertEntityList = new ArrayList<>();
        for (IamUserRoleInfoUpdateInputDto inputDto : userRoleList) {
            String roleId = inputDto.getRoleId();
            Set<String> shopIdSet = inputDto.getShopIdSet();
            Set<String> organizationIdSet = inputDto.getOrganizationIdSet();
            Set<String> companyIdSet = inputDto.getCompanyIdSet();

            if (!dbRoleIdset.contains(roleId)) {
                continue;
            }

            List<UserRoleTargetRelationEntity> singleRoleList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(shopIdSet)) {
                for (String shopId : shopIdSet) {
                    if (dbShopIdSet.contains(shopId)) {
                        UserRoleTargetRelationEntity e = new UserRoleTargetRelationEntity();
                        e.setUserId(userId);
                        e.setRoleId(roleId);
                        e.setTargetType(UserRoleTargetTypeEnum.SHOP);
                        e.setTargetId(shopId);
                        singleRoleList.add(e);
                    }
                }
            }

            if (CollectionUtil.isNotEmpty(organizationIdSet)) {
                for (String organizationId : organizationIdSet) {
                    if (dbOrganizationIdSet.contains(organizationId)) {
                        UserRoleTargetRelationEntity e = new UserRoleTargetRelationEntity();
                        e.setUserId(userId);
                        e.setRoleId(roleId);
                        e.setTargetType(UserRoleTargetTypeEnum.ORGANIZATION);
                        e.setTargetId(organizationId);
                        singleRoleList.add(e);
                    }
                }
            }

            if (CollectionUtil.isNotEmpty(companyIdSet)) {
                for (String companyId : companyIdSet) {
                    if (dbCompanyIdSet.contains(companyId)) {
                        UserRoleTargetRelationEntity e = new UserRoleTargetRelationEntity();
                        e.setUserId(userId);
                        e.setRoleId(roleId);
                        e.setTargetType(UserRoleTargetTypeEnum.COMPANY);
                        e.setTargetId(companyId);
                        singleRoleList.add(e);
                    }
                }
            }

            //如果为空，增加一条没有target的数据
            if (CollectionUtil.isEmpty(singleRoleList)) {
                UserRoleTargetRelationEntity e = new UserRoleTargetRelationEntity();
                e.setUserId(userId);
                e.setRoleId(roleId);
                singleRoleList.add(e);
            }

            insertEntityList.addAll(singleRoleList);
        }
        userRoleTargetRelationDao.batchInsert(userId, insertEntityList);
    }

    private UserDto getUserDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setUserId(entity.getId());
        dto.setUserName(entity.getUserName());
        dto.setPassword(entity.getPassword());
        if (StatusEnum.ENABLE == entity.getStatus()) {
            dto.setEnabled(true);
        }
        return dto;
    }
}
