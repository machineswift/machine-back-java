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
import com.machine.client.data.leaf.IDataLeaf4CodeClient;
import com.machine.client.data.supplier.ISupplierUserClient;
import com.machine.client.data.supplier.dto.input.DataSupplierCreateInputDto;
import com.machine.client.data.supplier.dto.input.DataSupplierUpdateInputDto;
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
import com.machine.sdk.common.envm.iam.UserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.UserTypeEnum;
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
import com.machine.service.iam.permission.dao.IPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;
import com.machine.service.iam.role.dao.IRoleDao;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;
import com.machine.service.iam.user.dao.*;
import com.machine.service.iam.user.dao.mapper.entity.UserEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
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
import java.util.function.Function;
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
    private IPermissionDao permissionDao;

    @Autowired
    private IUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Autowired
    private IDataLeaf4CodeClient leaf4CodeClient;

    @Autowired
    private ICompanyEmployeeClient companyEmployeeClient;

    @Autowired
    private IShopEmployeeClient shopEmployeeClient;

    @Autowired
    private ISupplierUserClient supplierClient;

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
        updateUserRoleInfo(userId, inputDto.getUserRoleList());

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
        updateUserRoleInfo(userId, inputDto.getUserRoleList());

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
            UserRoleRelationEntity userRoleRelationEntity = userRoleRelationDao.getByUk(
                    userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            if (null == userRoleRelationEntity) {
                userRoleRelationDao.insert(userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            }

            return userId;
        } else {
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
            userTypeDao.insertOrUpdate(userId, UserTypeEnum.FRANCHISEE);

            //添加角色(加盟商)
            userRoleRelationDao.insert(userId, ShopDefaultRoleEnum.FRANCHISEE.getName().toLowerCase());
            return userId;
        }
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

        updateUserRoleInfo(inputDto.getId(), inputDto.getUserRoleList());
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
        updateUserRoleInfo(inputDto.getId(), inputDto.getUserRoleList());

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
        updateUserRoleInfo(inputDto.getId(), inputDto.getUserRoleList());

        //修改供应商
        DataSupplierUpdateInputDto createInputDto = new DataSupplierUpdateInputDto();
        createInputDto.setUserId(inputDto.getId());
        createInputDto.setCompanyIdSet(inputDto.getCompanyIdSet());
        supplierClient.update(createInputDto);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserRole(IamCompanyUserUpdateInputDto inputDto) {
        String userId = inputDto.getId();

        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> inputUserRoleList = inputDto.getUserRoleList();
        if (CollectionUtil.isEmpty(inputUserRoleList)) {
            throw new IamBusinessException("iam.user.updateSupplierUser.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = inputUserRoleList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != inputUserRoleList.size()) {
            throw new IamBusinessException("iam.user.updateSupplierUser.roleRepeat", "角色重复");
        }

        UserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return;
        }

        {//用户组织关系
            Set<String> inputOrganizationIdSet = inputDto.getOrganizationIdSet();

            List<UserOrganizationRelationEntity> dbEntityList = userOrganizationRelationDao.listByUserId(userId);
            Set<String> dbOrganizationIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(dbEntityList)) {
                dbOrganizationIdSet = dbEntityList.stream()
                        .map(UserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
            }

            Set<String> deleteOrganizationIdSet = new HashSet<>(dbOrganizationIdSet);
            Set<String> insertOrganizationIdSet = new HashSet<>(inputOrganizationIdSet);

            deleteOrganizationIdSet.removeAll(inputOrganizationIdSet);
            insertOrganizationIdSet.removeAll(dbOrganizationIdSet);

            userOrganizationRelationDao.deleteByUserId(userId, deleteOrganizationIdSet);
            userOrganizationRelationDao.insertByUserId(userId, insertOrganizationIdSet);
        }


        {//用户角色关系
            List<UserRoleRelationEntity> dbRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);
            Set<String> dbRoleIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(dbRoleRelationEntityList)) {
                dbRoleIdSet = dbRoleRelationEntityList.stream()
                        .map(UserRoleRelationEntity::getRoleId).collect(Collectors.toSet());
            }

            Set<String> deleteRoleIdSet = new HashSet<>(dbRoleIdSet);
            Set<String> insertRoleIdSet = new HashSet<>(inputRoleIdSet);

            deleteRoleIdSet.removeAll(inputRoleIdSet);
            insertRoleIdSet.removeAll(dbRoleIdSet);

            userRoleRelationDao.deleteByUserId(userId, deleteRoleIdSet);
            userRoleRelationDao.insertByUserId(userId, insertRoleIdSet);

            dbRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

            {//用户角色关系业务数据
                Set<String> userRoleRelationIdSet = dbRoleRelationEntityList
                        .stream().map(UserRoleRelationEntity::getId).collect(Collectors.toSet());
                Map<String, UserRoleRelationEntity> roleIdRelationInfoMap = dbRoleRelationEntityList.stream()
                        .collect(Collectors.toMap(UserRoleRelationEntity::getRoleId, Function.identity()));

                //删除用户角色关系业务数据
                userRoleBusinessRelationDao.deleteByUserRoleRelationIdSet(userId, userRoleRelationIdSet);

                //新增用户角色关系业务数据
                List<UserRoleBusinessRelationEntity> insertBusinessRelationEntityList = new ArrayList<>();
                for (IamUserRoleInfoUpdateInputDto userRoleInfoUpdateInputDto : inputUserRoleList) {
                    String roleId = userRoleInfoUpdateInputDto.getRoleId();
                    Set<String> shopIdSet = userRoleInfoUpdateInputDto.getShopIdSet();
                    if (CollectionUtil.isEmpty(shopIdSet)) {
                        continue;
                    }

                    for (String shopId : shopIdSet) {
                        UserRoleBusinessRelationEntity insertBusinessRelationEntity = new UserRoleBusinessRelationEntity();
                        insertBusinessRelationEntity.setUserRoleRelationId(roleIdRelationInfoMap.get(roleId).getId());
                        insertBusinessRelationEntity.setBusinessId(shopId);
                        insertBusinessRelationEntity.setBusinessType(UserRoleBusinessTypeEnum.SHOP);
                        insertBusinessRelationEntityList.add(insertBusinessRelationEntity);
                    }
                }
                userRoleBusinessRelationDao.batchInsert(userId, insertBusinessRelationEntityList);
            }

        }
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
