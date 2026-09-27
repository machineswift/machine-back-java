package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataFileTempCreateInputDto;
import com.machine.client.data.filecenter.download.IDataDownloadClient;
import com.machine.client.data.filecenter.download.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.client.iam.biam.user.dto.input.*;
import com.machine.client.iam.biam.user.dto.output.BIamUserAuthDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.BIamUserDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserListOutputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.envm.biam.auth.BIamAuth2SourceEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.DateUtil;
import com.machine.sdk.base.tool.StringUtil;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.UUIDv7;
import com.machine.service.iam.biam.permission.dao.IBIamPermissionDao;
import com.machine.service.iam.biam.permission.dao.mapper.entity.BIamPermissionEntity;
import com.machine.service.iam.biam.role.dao.IBIamRoleDao;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import com.machine.service.iam.biam.user.dao.IBIamUserDao;
import com.machine.service.iam.biam.user.dao.IBIamUserOrganizationRelationDao;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleBusinessRelationDao;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserOrganizationRelationEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleBusinessRelationEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.service.iam.biam.user.service.IBIamUserService;
import com.machine.service.iam.biam.user.service.bo.BIamShopUserExportBo;
import com.machine.starter.obs.operateLog.AttachmentOperationLogPublisher;
import com.machine.starter.obs.service.ObsFileService;
import com.machine.starter.obs.tool.AttachmentExpireTimeUtil;
import com.machine.starter.redis.cache.biam.RedisBIamPermissionCache;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonBIamConstant.User.ROOT_USER_ID;
import static com.machine.starter.obs.constant.ObsFileConstant.ATTACHMENT_DEFAULT_GROUP;

@Slf4j
@Service
public class BIamUserServiceImpl implements IBIamUserService {

    @Autowired
    private AttachmentOperationLogPublisher attachmentOperationLogPublisher;

    @Autowired
    private RedisBIamPermissionCache permissionCache;

    @Autowired
    private ObsFileService obsFileService;

    @Autowired
    private IBIamUserDao userDao;

    @Autowired
    private IBIamRoleDao roleDao;

    @Autowired
    private IBIamPermissionDao permissionDao;

    @Autowired
    private IBIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IBIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IBIamUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Autowired
    private IDataFileTempClient dataFileTempClient;

    @Autowired
    private IDataAttachmentClient dataAttachmentClient;

    @Autowired
    private IDataDownloadClient dataDownloadClient;

    @Autowired
    private IDataLeaf4IamCodeClient leaf4IamCodeClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamUserCreateInputDto inputDto) {
        BIamUserEntity entityByUserName = userDao.getByUsername(inputDto.getUsername());
        if (null != entityByUserName) {
            log.error("新增用户系统账号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
            throw new BIamBusinessException("biam.user.service.create.usernameAlreadyExists", "系统账号已经存在");
        }

        if (StrUtil.isNotBlank(inputDto.getPhone())) {
            BIamUserEntity entityByPhone = userDao.getByPhone(inputDto.getPhone());
            if (null != entityByPhone) {
                log.error("新增用户手机号已经存在，inputDto={}", JSONUtil.toJsonStr(inputDto));
                throw new BIamBusinessException("biam.user.service.create.phoneAlreadyExists", "手机号已经存在");
            }
        }

        //生成编码
        String code = leaf4IamCodeClient.userCode();

        //创建用户
        BIamUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamUserEntity.class);
        insertEntity.setStatus(StatusEnum.DISABLE);
        insertEntity.setPassword(StringUtil.generatePassword());
        insertEntity.setCode(code);
        return userDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BIamUserUpdateInputDto inputDto) {
        BIamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        //验证用户名是否存在
        BIamUserEntity userNameEntity = userDao.getByUsername(dbEntity.getUsername());
        if (null != userNameEntity && !userNameEntity.getId().equals(inputDto.getId())) {
            throw new BIamBusinessException("biam.user.service.update.usernameAlreadyExists", "用户名已经存在");
        }

        //修改基础信息
        BIamUserEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamUserEntity.class);
        return userDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(BIamUserUpdateStatusInputDto inputDto) {
        BIamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity || inputDto.getStatus()==dbEntity.getStatus()) {
            return 0;
        }
        return userDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhone(BIamUserUpdatePhoneInputDto inputDto) {
        BIamUserEntity phoneEntity = userDao.getByPhone(inputDto.getPhone());
        if (null != phoneEntity && !phoneEntity.getId().equals(inputDto.getId())) {
            throw new BIamBusinessException("biam.user.service.updatePhone.phoneAlreadyExists", "手机号已经存在");
        }
        return userDao.updatePhone(inputDto.getId(), inputDto.getPhone());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePassword(BIamUserUpdatePasswordInputDto dto) {
        BIamUserEntity entity = userDao.getById(dto.getUserId());
        if (null == entity) {
            return 0;
        }
        return userDao.updatePassword(dto.getUserId(), dto.getPassword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(BIamUserUpdatePermissionInputDto inputDto) {
        //验证角色是否重复
        List<BIamUserRoleInfoUpdateInputDto> inputDtoUserRoleInfoList = inputDto.getUserRoleInfoList();
        if (CollectionUtil.isEmpty(inputDtoUserRoleInfoList)) {
            throw new BIamBusinessException("biam.user.service.updateUserRole.roleIsEmpty", "角色为空");
        }
        Set<String> inputRoleIdSet = inputDtoUserRoleInfoList.stream().map(BIamUserRoleInfoUpdateInputDto::getRoleId).collect(Collectors.toSet());
        if (inputRoleIdSet.size() != inputDtoUserRoleInfoList.size()) {
            throw new BIamBusinessException("biam.user.service.updateUserRole.roleRepeat", "角色重复");
        }

        String userId = inputDto.getId();

        BIamUserEntity dbEntity = userDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return;
        }

        {//用户组织关系
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap = new HashMap<>();
            if (CollectionUtil.isNotEmpty(inputDto.getOrganizationIdMap())) {
                organizationIdMap = inputDto.getOrganizationIdMap();
            }
            Set<String> inputOrganizationIdSet = organizationIdMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

            List<BIamUserOrganizationRelationEntity> dbEntityList = userOrganizationRelationDao.listByUserId(userId);
            Set<String> dbOrganizationIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(dbEntityList)) {
                dbOrganizationIdSet = dbEntityList.stream()
                        .map(BIamUserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
            }

            Set<String> deleteOrganizationIdSet = new HashSet<>(dbOrganizationIdSet);
            Set<String> insertOrganizationIdSet = new HashSet<>(inputOrganizationIdSet);

            deleteOrganizationIdSet.removeAll(inputOrganizationIdSet);
            insertOrganizationIdSet.removeAll(dbOrganizationIdSet);

            userOrganizationRelationDao.deleteByUserId(userId, deleteOrganizationIdSet);
            userOrganizationRelationDao.insertByUserId(userId, insertOrganizationIdSet);
        }

        {//用户角色关系

            List<BIamUserRoleRelationEntity> dbRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);
            if (CollectionUtil.isNotEmpty(dbRoleRelationEntityList)) {
                //删除角色
                userRoleRelationDao.deleteByUserId(userId);
                Set<String> userRoleRelationIdSet = dbRoleRelationEntityList
                        .stream().map(BIamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                //删除角色关联的业务数据
                userRoleBusinessRelationDao.deleteByUserRoleRelationIdSet(userId, userRoleRelationIdSet);
            }

            //新增用户角色关系
            List<BIamUserRoleRelationEntity> insertEntityList = new ArrayList<>();
            for (BIamUserRoleInfoUpdateInputDto outputDto : inputDtoUserRoleInfoList) {
                BIamUserRoleRelationEntity insertEntity = new BIamUserRoleRelationEntity();
                insertEntity.setId(UUIDv7.generateWithoutDashes());
                insertEntity.setUserId(userId);
                insertEntity.setRoleId(outputDto.getRoleId());
                insertEntity.setSort(outputDto.getSort());
                insertEntityList.add(insertEntity);
            }
            userRoleRelationDao.batchInsert(userId, insertEntityList);

            {//用户角色关系业务数据
                Map<String, BIamUserRoleRelationEntity> roleIdRelationInfoMap = insertEntityList.stream()
                        .collect(Collectors.toMap(BIamUserRoleRelationEntity::getRoleId, Function.identity()));

                //新增用户角色关系业务数据
                List<BIamUserRoleBusinessRelationEntity> insertBusinessRelationEntityList = new ArrayList<>();
                for (BIamUserRoleInfoUpdateInputDto userRoleInfoUpdateInputDto : inputDtoUserRoleInfoList) {
                    String roleId = userRoleInfoUpdateInputDto.getRoleId();
                    Set<String> shopIdSet = userRoleInfoUpdateInputDto.getShopIdSet();
                    if (CollectionUtil.isNotEmpty(shopIdSet)) {
                        long sort = shopIdSet.size() + 800L;
                        for (String shopId : shopIdSet) {
                            BIamUserRoleBusinessRelationEntity insertBusinessRelationEntity = new BIamUserRoleBusinessRelationEntity();
                            insertBusinessRelationEntity.setUserRoleRelationId(roleIdRelationInfoMap.get(roleId).getId());
                            insertBusinessRelationEntity.setBusinessId(shopId);
                            insertBusinessRelationEntity.setBusinessType(BIamUserRoleBusinessTypeEnum.SHOP);
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
    public int countNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto) {
        return userDao.countNotBindOrganization(inputDto);
    }

    @Override
    public BIamUserDetailOutputDto detail(IdRequest request) {
        BIamUserEntity entity = userDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamUserDetailOutputDto.class);
    }

    @Override
    public BIamUserAuthDetailOutputDto detailAuth(IdRequest request) {
        //查询用户信息
        BIamUserEntity iamUserEntity = userDao.getById(request.getId());
        if (null == iamUserEntity) {
            return null;
        }

        BIamUserAuthDetailOutputDto detailDto = new BIamUserAuthDetailOutputDto();
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
            BIamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
            List<BIamPermissionTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(allTreeOutputDto);
            Set<String> permissionCodeSet = outputDtoList.stream().map(BIamPermissionTreeOutputDto::getCode).collect(Collectors.toSet());

            detailDto.setRoleCodeList(roleCodeList);
            detailDto.setPermissionCodeList(new ArrayList<>(permissionCodeSet));
        } else {
            //查询用户角色信息
            List<BIamRoleEntity> roleEntityList = roleDao.selectByUserId(request.getId());
            List<String> permissionIdByRoleIds = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(roleEntityList)) {
                permissionIdByRoleIds = permissionDao.selectIdByRoleIds(
                        roleEntityList.stream().map(BIamRoleEntity::getId).collect(Collectors.toList()));
            }
            if (CollectionUtil.isNotEmpty(roleEntityList)) {
                detailDto.setRoleCodeList(roleEntityList.stream().map(BIamRoleEntity::getCode).collect(Collectors.toList()));
            }

            //查询角色权限信息
            List<BIamPermissionEntity> permissionEntityList = permissionDao.selectByUserId(request.getId());
            Set<String> permissionIdSet = new HashSet<>();
            if (CollectionUtil.isNotEmpty(permissionEntityList)) {
                permissionIdSet.addAll(permissionEntityList.stream().map(BIamPermissionEntity::getId).toList());
            }
            if (CollectionUtil.isNotEmpty(permissionIdByRoleIds)) {
                permissionIdSet.addAll(permissionIdByRoleIds);
            }

            BIamPermissionTreeOutputDto allTreeOutputDto = permissionCache.treeAll();
            Set<BIamPermissionTreeOutputDto> permissionWithParentNodeSet = TreeUtil.getAllParentNodes(allTreeOutputDto, permissionIdSet);

            detailDto.setPermissionCodeList(permissionWithParentNodeSet.stream()
                    .map(BIamPermissionTreeOutputDto::getCode).collect(Collectors.toList()));
        }
        return detailDto;
    }

    @Override
    public BIamUserDto getByUserId(String userId) {
        BIamUserEntity entity = userDao.getById(userId);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public BIamUserDto getByUsername(String username) {
        BIamUserEntity entity = userDao.getByUsername(username);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public BIamUserDto getByThirdPartyUuid(BIamAuth2SourceEnum source,
                                           String thirdPartyUuid) {
        BIamUserEntity entity = userDao.getByThirdPartyUuid(source, thirdPartyUuid);
        if (entity == null) {
            return null;
        }
        return getUserDto(entity);
    }

    @Override
    public BIamUserDto getByPhone(String phone) {
        BIamUserEntity entity = userDao.getByPhone(phone);
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
    public List<String> listNotBindOrganization(BIamDataUserNotBindOrganizationInputDto inputDto) {
        return userDao.listNotBindOrganization(inputDto);
    }

    @Override
    public Map<String, BIamUserDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<BIamUserEntity> iamUserEntityList = userDao.selectByIdSet(request.getIdSet());
        return iamUserEntityList.stream()
                .collect(Collectors.toMap(BIamUserEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), BIamUserDetailOutputDto.class)));
    }

    @Override
    public List<BIamUserListOutputDto> listByOffset(BIamUserQueryListOffsetInputDto inputDto) {
        List<BIamUserEntity> entityList = userDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamUserListOutputDto.class);
    }

    @Override
    public Page<BIamUserListOutputDto> selectPage(BIamUserQueryPageInputDto inputDto) {
        Page<BIamUserEntity> page = userDao.selectPage(inputDto);
        Page<BIamUserListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamUserListOutputDto.class));
        return pageResult;
    }

    @Override
    public String exportUser(BIamUserExportInputDto inputDto) {
        DataDownloadDetailOutputDto downloadDetail = dataDownloadClient.getById(new IdRequest(inputDto.getDownloadId()));
        ClientEnvironmentInfo environmentInfo = JSONUtil.toBean(downloadDetail.getFeatures(), ClientEnvironmentInfo.class);
        AppContextHolder.getContext().setUserId(downloadDetail.getCreateBy());

        List<BIamUserEntity> entityList = userDao.listShopUser4Export(inputDto);

        List<BIamShopUserExportBo> exportBoList = new ArrayList<>();
        for (BIamUserEntity entity : entityList) {
            BIamShopUserExportBo exportBo = new BIamShopUserExportBo();
            exportBo.setId(entity.getId());
            exportBo.setUsername(entity.getUsername());
            exportBo.setStatus(entity.getStatus().getMessage());
            exportBo.setCode(entity.getCode());
            exportBo.setName(entity.getName());
            exportBo.setPhone(entity.getPhone());
            exportBo.setGender(entity.getGender().getMessage());
            exportBoList.add(exportBo);
        }

        // 文件信息
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FastExcel.write(outputStream, BIamShopUserExportBo.class).sheet("用户").doWrite(exportBoList);
        String uuid = UUIDv7.generateWithoutDashes();
        String originalFilename = "用户-" + DateUtil.getCurrentDate() + "-" + uuid + ".xlsx";


        // 上传到对象存储（临时路径）
        String obsPath = "/temp/" + java.time.LocalDate.now() + "/" + UUIDv7.generateWithoutDashes();
        FileInfo fileInfo = obsFileService.upload(new ByteArrayInputStream(outputStream.toByteArray()), originalFilename, obsPath);

        // 记录临时文件
        DataFileTempCreateInputDto fileTempCreateInputDto = new DataFileTempCreateInputDto();
        fileTempCreateInputDto.setFileType(DataFileTypeEnum.SPREADSHEET);
        fileTempCreateInputDto.setOriginalName(originalFilename);
        fileTempCreateInputDto.setStorageName(fileInfo.getFilename());
        fileTempCreateInputDto.setStoragePath(fileInfo.getPath());
        fileTempCreateInputDto.setFileInfo(JSONUtil.toJsonStr(fileInfo));
        fileTempCreateInputDto.setSize(fileInfo.getSize());
        fileTempCreateInputDto.setExpireTime(System.currentTimeMillis() + 24 * 60 * 60 * 1000L);
        String tempFileId = dataFileTempClient.create(fileTempCreateInputDto);

        // 创建附件
        DataAttachmentCreateInputDto attachmentCreateInputDto = new DataAttachmentCreateInputDto();
        attachmentCreateInputDto.setEntity(ModuleEntityEnum.DATA_DOWNLOAD);
        attachmentCreateInputDto.setEntityId(downloadDetail.getId());
        attachmentCreateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
        attachmentCreateInputDto.setExpireTime(AttachmentExpireTimeUtil.dataDownload());
        attachmentCreateInputDto.setChangeDesc("用户下载");

        DataFileTempCreateDto tempFileItem = new DataFileTempCreateDto();
        tempFileItem.setFileId(tempFileId);
        tempFileItem.setSort(1L);
        attachmentCreateInputDto.setFileTempList(List.of(tempFileItem));
        String attachmentId = dataAttachmentClient.create(attachmentCreateInputDto);

        // 记录日志
        attachmentOperationLogPublisher.publish(attachmentId,DataAttachmentOperationTypeEnum.UPLOAD,
                OperateSourceEnum.IAM_APP, ModuleEnum.BIAM);

        return attachmentId;
    }


    private BIamUserDto getUserDto(BIamUserEntity entity) {
        BIamUserDto dto = new BIamUserDto();
        dto.setUserId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        if (StatusEnum.ENABLE == entity.getStatus()) {
            dto.setEnabled(true);
        }
        return dto;
    }
}
