package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.IDataAttachmentClient;
import com.machine.client.data.attachment.dto.input.DataAttachmentCreateTemporaryInputDto;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.client.iam.user.dto.input.IamUserExportInputDto;
import com.machine.client.iam.user.dto.output.IamUserAuthDetailOutputDto;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.iam.user.dto.IamUserDto;
import com.machine.client.iam.user.dto.input.*;
import com.machine.client.iam.user.dto.output.IamUserListOutputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import com.machine.sdk.common.envm.iam.role.IamUserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.auth.IamAuth2SourceEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.DateUtil;
import com.machine.sdk.common.tool.StringUtil;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.iam.permission.dao.IIamPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.IamPermissionEntity;
import com.machine.service.iam.role.dao.IIamRoleDao;
import com.machine.service.iam.role.dao.mapper.entity.IamRoleEntity;
import com.machine.service.iam.user.dao.*;
import com.machine.service.iam.user.dao.mapper.entity.IamUserEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserOrganizationRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.service.iam.user.service.IIamUserService;
import com.machine.service.iam.user.service.bo.IamShopUserExportBo;
import com.machine.starter.obs.function.ObsFunction;
import com.machine.starter.redis.cache.iam.RedisCacheIamPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonIamConstant.User.ROOT_USER_ID;

@Slf4j
@Service
public class IamUserServiceImpl implements IIamUserService {

    @Autowired
    private RedisCacheIamPermission permissionCache;

    @Autowired
    private ObsFunction obsFunction;

    @Autowired
    private IIamUserDao userDao;

    @Autowired
    private IIamRoleDao roleDao;

    @Autowired
    private IIamPermissionDao permissionDao;

    @Autowired
    private IIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IIamUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Autowired
    private IDataLeaf4IamCodeClient leaf4IamCodeClient;

    @Autowired
    private IDataAttachmentClient attachmentClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamUserCreateInputDto inputDto) {
        IamUserEntity entityByUserName = userDao.getByUsername(inputDto.getUsername());
        if (null != entityByUserName) {
            log.error("新增用户系统账号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new IamBusinessException("iam.user.service.create.usernameAlreadyExists", "系统账号已经存在");
        }

        if (StrUtil.isNotBlank(inputDto.getPhone())) {
            IamUserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
            if (null != entityByPhone) {
                log.error("新增用户手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
                throw new IamBusinessException("iam.user.service.create.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //生成编码
        String code = leaf4IamCodeClient.userCode();

        //创建用户
        IamUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        insertEntity.setStatus(StatusEnum.DISABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setCode(code);
        return userDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamUserUpdateInputDto inputDto) {
        IamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        //验证用户名是否存在
        IamUserEntity userNameEntity = userDao.getByUsername(dbEntity.getUsername());
        if (null != userNameEntity && !userNameEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.service.update.usernameAlreadyExists", "用户名已经存在");
        }

        //修改基础信息
        IamUserEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserEntity.class);
        return userDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(IamUserUpdateStatusInputDto inputDto) {
        return userDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhone(IamUserUpdatePhoneInputDto inputDto) {
        IamUserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
            throw new IamBusinessException("iam.user.service.updatePhone.phoneAlreadyExists", "手机号已经存在");
        }
        return userDao.updatePhone(inputDto.getId(), inputDto.getPhone());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePassword(IamUserUpdatePasswordInputDto dto) {
        IamUserEntity entity = userDao.getById(dto.getUserId());
        if (null == entity) {
            return 0;
        }
        return userDao.updatePassword(dto.getUserId(), dto.getPassword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(IamUserUpdatePermissionInputDto inputDto) {
        //验证角色是否重复
        List<IamUserRoleInfoUpdateInputDto> inputDtoUserRoleInfoList = inputDto.getUserRoleInfoList();
        if (CollectionUtil.isEmpty(inputDtoUserRoleInfoList)) {
            throw new IamBusinessException("iam.user.service.updateUserRole.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = inputDtoUserRoleInfoList.stream().map(IamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != inputDtoUserRoleInfoList.size()) {
            throw new IamBusinessException("iam.user.service.updateUserRole.roleRepeat", "角色重复");
        }

        String userId = inputDto.getId();

        IamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return;
        }

        {//用户组织关系
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(inputDto.getOrganizationIdMap())) {
                organizationIdMap = inputDto.getOrganizationIdMap();
            }
            Set<String> inputOrganizationIdSet = organizationIdMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

            List<IamUserOrganizationRelationEntity> dbEntityList = userOrganizationRelationDao.listByUserId(userId);
            Set<String> dbOrganizationIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(dbEntityList)) {
                dbOrganizationIdSet = dbEntityList.stream()
                        .map(IamUserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
            }

            Set<String> deleteOrganizationIdSet = new HashSet<>(dbOrganizationIdSet);
            Set<String> insertOrganizationIdSet = new HashSet<>(inputOrganizationIdSet);

            deleteOrganizationIdSet.removeAll(inputOrganizationIdSet);
            insertOrganizationIdSet.removeAll(dbOrganizationIdSet);

            userOrganizationRelationDao.deleteByUserId(userId, deleteOrganizationIdSet);
            userOrganizationRelationDao.insertByUserId(userId, insertOrganizationIdSet);
        }

        {//用户角色关系

            List<IamUserRoleRelationEntity> dbRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);
            if (CollectionUtil.isNotEmpty(dbRoleRelationEntityList)) {
                //删除角色
                userRoleRelationDao.deleteByUserId(userId);
                Set<String> userRoleRelationIdSet = dbRoleRelationEntityList
                        .stream().map(IamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                //删除角色关联的业务数据
                userRoleBusinessRelationDao.deleteByUserRoleRelationIdSet(userId, userRoleRelationIdSet);
            }

            //新增用户角色关系
            List<IamUserRoleRelationEntity> insertEntityList = new ArrayList<>();
            for (IamUserRoleInfoUpdateInputDto outputDto : inputDtoUserRoleInfoList) {
                IamUserRoleRelationEntity insertEntity = new IamUserRoleRelationEntity();
                insertEntity.setId(UUID.randomUUID().toString().replace("-", ""));
                insertEntity.setUserId(userId);
                insertEntity.setRoleId(outputDto.getRoleId());
                insertEntity.setSort(outputDto.getSort());
                insertEntityList.add(insertEntity);
            }
            userRoleRelationDao.batchInsert(userId, insertEntityList);

            {//用户角色关系业务数据
                Map<String, IamUserRoleRelationEntity> roleIdRelationInfoMap = insertEntityList.stream()
                        .collect(Collectors.toMap(IamUserRoleRelationEntity::getRoleId, Function.identity()));

                //新增用户角色关系业务数据
                List<IamUserRoleBusinessRelationEntity> insertBusinessRelationEntityList = new ArrayList<>();
                for (IamUserRoleInfoUpdateInputDto userRoleInfoUpdateInputDto : inputDtoUserRoleInfoList) {
                    String roleId = userRoleInfoUpdateInputDto.getRoleId();
                    Set<String> shopIdSet = userRoleInfoUpdateInputDto.getShopIdSet();
                    if (CollectionUtil.isNotEmpty(shopIdSet)) {
                        long sort = shopIdSet.size() + 800L;
                        for (String shopId : shopIdSet) {
                            IamUserRoleBusinessRelationEntity insertBusinessRelationEntity = new IamUserRoleBusinessRelationEntity();
                            insertBusinessRelationEntity.setUserRoleRelationId(roleIdRelationInfoMap.get(roleId).getId());
                            insertBusinessRelationEntity.setBusinessId(shopId);
                            insertBusinessRelationEntity.setBusinessType(IamUserRoleBusinessTypeEnum.SHOP);
                            insertBusinessRelationEntity.setSort(sort--);
                            insertBusinessRelationEntityList.add(insertBusinessRelationEntity);
                        }
                    }
                }
                userRoleBusinessRelationDao.batchInsert(userId, insertBusinessRelationEntityList);
            }

        }
    }

    @Override
    public int countNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto) {
        return userDao.countNotBindOrganization(inputDto);
    }

    @Override
    public IamUserDetailOutputDto detail(IdRequest request) {
        IamUserEntity entity = userDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamUserDetailOutputDto.class);
    }

    @Override
    public IamUserAuthDetailOutputDto detailAuth(IdRequest request) {
        //查询用户信息
        IamUserEntity iamUserEntity = userDao.getById(request.getId());
        if (null == iamUserEntity) {
            return null;
        }

        IamUserAuthDetailOutputDto detailDto = new IamUserAuthDetailOutputDto();
        detailDto.setUserId(iamUserEntity.getId());
        detailDto.setUsername(iamUserEntity.getUsername());
        detailDto.setPassword(iamUserEntity.getPassword());
        detailDto.setName(iamUserEntity.getName());
        detailDto.setPhone(iamUserEntity.getPhone());
        detailDto.setStatus(iamUserEntity.getStatus());

        if (ROOT_USER_ID.equals(iamUserEntity.getId())) {
            //root用户拥有所有的权限
            //角色编码
            List<String> roleCodeList = roleDao.listAllCode();

            //权限编码
            IamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
            List<IamPermissionTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(allTreeOutputDto);
            Set<String> permissionCodeSet = outputDtoList.stream().map(IamPermissionTreeOutputDto::getCode).collect(Collectors.toSet());

            detailDto.setRoleCodeList(roleCodeList);
            detailDto.setPermissionCodeList(new ArrayList<>(permissionCodeSet));
        } else {
            //查询用户角色信息
            List<IamRoleEntity> iamRoleEntityList = roleDao.selectByUserId(request.getId());
            List<String> permissionIdByRoleIds = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(iamRoleEntityList)) {
                permissionIdByRoleIds = permissionDao.selectIdByRoleIds(
                        iamRoleEntityList.stream().map(IamRoleEntity::getId).collect(Collectors.toList()));
            }
            if (CollectionUtil.isNotEmpty(iamRoleEntityList)) {
                detailDto.setRoleCodeList(iamRoleEntityList.stream().map(IamRoleEntity::getCode).collect(Collectors.toList()));
            }

            //查询角色权限信息
            List<IamPermissionEntity> iamPermissionEntityList = permissionDao.selectByUserId(request.getId());
            Set<String> permissionIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(iamPermissionEntityList)) {
                permissionIdSet.addAll(iamPermissionEntityList.stream().map(IamPermissionEntity::getId).toList());
            }
            if (CollectionUtil.isNotEmpty(permissionIdByRoleIds)) {
                permissionIdSet.addAll(permissionIdByRoleIds);
            }

            IamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
            Set<IamPermissionTreeOutputDto> permissionWithParentNodeSet = TreeUtil.getAllParentNodes(allTreeOutputDto, permissionIdSet);

            detailDto.setPermissionCodeList(permissionWithParentNodeSet.stream()
                    .map(IamPermissionTreeOutputDto::getCode).collect(Collectors.toList()));
        }
        return detailDto;
    }

    @Override
    public IamUserDto getByUserId(String userId) {
        IamUserEntity entity = userDao.getById(userId);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public IamUserDto getByUsername(String username) {
        IamUserEntity entity = userDao.getByUsername(username);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public IamUserDto getByThirdPartyUuid(IamAuth2SourceEnum source,
                                          String thirdPartyUuid) {
        IamUserEntity entity = userDao.getByThirdPartyUuid(source, thirdPartyUuid);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public IamUserDto getByPhone(String phone) {
        IamUserEntity entity = userDao.getByPhone(phone);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public Set<String> getIdByRoleIdSet(IdSetRequest request) {
        Set<String> roleIdSet = request.getIdSet();
        if (CollectionUtil.isEmpty(roleIdSet)) {
            return Set.of();
        }

        return new HashSet<>(userRoleRelationDao.listUserIdByRoleIdSet(roleIdSet));
    }

    @Override
    public Set<String> getIdByShopIdSet(IdSetRequest request) {
        Set<String> shopIdSet = request.getIdSet();
        if (CollectionUtil.isEmpty(shopIdSet)) {
            return Set.of();
        }

        return new HashSet<>(userDao.listIdByShopIdSet(shopIdSet));
    }

    @Override
    public Set<String> getIdByOrganizationIdSet(IdSetRequest request) {
        Set<String> organizationIdSet = request.getIdSet();
        if (CollectionUtil.isEmpty(organizationIdSet)) {
            return Set.of();
        }

        return new HashSet<>(userOrganizationRelationDao.listUserIdByOrganizationIdSet(organizationIdSet));
    }

    @Override
    public List<String> listNotBindOrganization(IamDataUserNotBindOrganizationInputDto inputDto) {
        return userDao.listNotBindOrganization(inputDto);
    }

    @Override
    public Map<String, IamUserDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<IamUserEntity> iamUserEntityList = userDao.selectByIdSet(request.getIdSet());
        return iamUserEntityList.stream()
                .collect(Collectors.toMap(IamUserEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), IamUserDetailOutputDto.class)));
    }

    @Override
    public List<IamUserListOutputDto> listByOffset(IamUserQueryListOffsetInputDto inputDto) {
        List<IamUserEntity> entityList = userDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamUserListOutputDto.class);
    }

    @Override
    public Page<IamUserListOutputDto> selectPage(IamUserQueryPageInputDto inputDto) {
        Page<IamUserEntity> page = userDao.selectPage(inputDto);
        Page<IamUserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamUserListOutputDto.class));
        return pageResult;
    }

    @Override
    public String exportUser(IamUserExportInputDto inputDto) {
        List<IamUserEntity> entityList = userDao.listShopUser4Export(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return null;
        }

        List<IamShopUserExportBo> exportBoList = new ArrayList<>();
        for (IamUserEntity entity : entityList) {
            IamShopUserExportBo exportBo = new IamShopUserExportBo();
            exportBo.setId(entity.getId());
            exportBo.setUsername(entity.getUsername());
            exportBo.setStatus(entity.getStatus().getMessage());
            exportBo.setCode(entity.getCode());
            exportBo.setName(entity.getName());
            exportBo.setPhone(entity.getPhone());
            exportBo.setGender(entity.getGender().getMessage());
            exportBoList.add(exportBo);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FastExcel.write(outputStream, IamShopUserExportBo.class).sheet("用户").doWrite(exportBoList);
        byte[] bytes = outputStream.toByteArray();
        String originalFilename = UUID.randomUUID().toString().replaceAll("-", "")
                + "-" + DateUtil.getCurrentDate() + "-用户.xlsx";

        DataAttachmentCreateTemporaryInputDto createInputDto = new DataAttachmentCreateTemporaryInputDto();
        createInputDto.setType(DataAttachmentTypeEnum.FILE);
        createInputDto.setSize((long) bytes.length);
        createInputDto.setName(originalFilename);
        createInputDto.setFileInfo(JSONUtil.toJsonStr(obsFunction.upload(bytes, originalFilename)));

        return attachmentClient.createTemporary(createInputDto);
    }

    private IamUserDto getUserDto(IamUserEntity entity) {
        IamUserDto dto = new IamUserDto();
        dto.setUserId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        if (StatusEnum.ENABLE == entity.getStatus()) {
            dto.setEnabled(true);
        }
        return dto;
    }
}
