package com.machine.service.iam.role.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4CodeClient;
import com.machine.client.hrm.jobpost.IHrmJobPostRoleRelationClient;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPosRoleRelationBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostRoleRelationCreateInputDto;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.client.iam.role.dto.input.*;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.PermissionTypeEnum;
import com.machine.sdk.common.envm.iam.role.CompanyDefaultRoleEnum;
import com.machine.sdk.common.envm.iam.role.RoleTypeEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.role.dao.IRoleDao;
import com.machine.service.iam.role.dao.IRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.RoleEntity;
import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;
import com.machine.service.iam.role.service.IRoleService;
import com.machine.service.iam.user.dao.IUserRoleTargetRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import com.machine.starter.redis.cache.RedisCacheIamPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RedisCacheIamPermission permissionCache;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IUserRoleTargetRelationDao userRoleTargetRelationDao;

    @Autowired
    private IDataLeaf4CodeClient leafClient;

    @Autowired
    private IHrmJobPostRoleRelationClient jobPostRoleRelationClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamRoleCreateInputDto inputDto) {
        //验证名称是否存在
        RoleEntity entityByName = roleDao.getByName(inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.role.create.nameAlreadyExists", "角色名称已经存在");
        }

        RoleEntity insertEntity = new RoleEntity();
        insertEntity.setParentId(inputDto.getType().name().toLowerCase());
        insertEntity.setStatus(StatusEnum.ENABLE);

        //角色编码
        if (RoleTypeEnum.COMPANY == inputDto.getType()) {
            insertEntity.setCode(leafClient.iamRoleGs());
        } else if (RoleTypeEnum.SHOP == inputDto.getType()) {
            insertEntity.setCode(leafClient.iamRoleMd());
        } else if (RoleTypeEnum.SUPPLIER == inputDto.getType()) {
            insertEntity.setCode(leafClient.iamRoleGys());
        }
        insertEntity.setType(inputDto.getType());
        insertEntity.setName(inputDto.getName());
        insertEntity.setDescription(inputDto.getDescription());
        insertEntity.setSort(System.currentTimeMillis());
        String roleId = roleDao.insert(insertEntity);

        //处理角色关联的权限信息
        if (CollectionUtil.isNotEmpty(inputDto.getPermissionIdSet())) {
            List<PermissionTreeOutputDto> entityList = permissionCache.listByIdSet(inputDto.getPermissionIdSet());
            if (CollectionUtil.isEmpty(entityList)) {
                return roleId;
            }

            Map<String, String> dataPermissionMap = inputDto.getDataPermissionMap();
            List<RolePermissionRelationEntity> insertEntityList = new ArrayList<>();
            Set<String> dbIdList = entityList.stream().map(PermissionTreeOutputDto::getId).collect(Collectors.toSet());
            long sort = System.currentTimeMillis();
            for (String permissionId : dbIdList) {
                RolePermissionRelationEntity e = new RolePermissionRelationEntity();
                e.setRoleId(roleId);
                e.setPermissionId(permissionId);
                e.setType(PermissionTypeEnum.READ);
                if (null != dataPermissionMap) {
                    e.setDataInto(dataPermissionMap.get(permissionId));
                }
                e.setSort(--sort);
                insertEntityList.add(e);
            }
            rolePermissionRelationDao.insert(insertEntityList);
        }

        //保存职务与角色的关系
        if (CollectionUtil.isNotEmpty(inputDto.getJobPostIdSet())) {
            List<HrmJobPostRoleRelationCreateInputDto> inputDtoList = new ArrayList<>();
            for (String jobPostId : inputDto.getJobPostIdSet()) {
                inputDtoList.add(new HrmJobPostRoleRelationCreateInputDto(jobPostId, roleId));
            }
            jobPostRoleRelationClient.batchCreate(new HrmJobPosRoleRelationBatchInsertInputDto(inputDtoList));
        }

        return roleId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        RoleEntity entity = roleDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (isDefaultRole(entity.getCode())) {
            throw new IamBusinessException("iam.role.delete.defaultRole", "默认角色，不能删除");
        }

        //子角色信息
        List<RoleEntity> subList = roleDao.listSub(new IamRoleListSubInputDto(entity.getId()));
        if (!CollectionUtil.isEmpty(subList)) {
            throw new IamBusinessException("iam.role.delete.hasSubRole", "下面有子数据，不能删除");
        }

        //是否关联用户
        List<UserRoleTargetRelationEntity> userRoleTargetRelationEntityList = userRoleTargetRelationDao
                .selectByRoleId(request.getId());
        if (!CollectionUtil.isEmpty(userRoleTargetRelationEntityList)) {
            throw new IamBusinessException("iam.role.delete.associationUser", "角色关联用户，不能删除");
        }

        //删除角色和权限关系
        rolePermissionRelationDao.deleteByRoleId(request.getId());

        //删除角色和职务关系
        jobPostRoleRelationClient.deleteByRoleId(new IdRequest(request.getId()));

        return roleDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamRoleUpdateInputDto inputDto) {
        RoleEntity entity = roleDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证名称在同一层级是否存在
        RoleEntity entityByName = roleDao.getByName(inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.role.update.nameAlreadyExists", "角色名称已经存在");
        }

        //修改角色信息
        RoleEntity updateEntity = new RoleEntity();
        updateEntity.setId(inputDto.getId());
        if (!isDefaultRole(entity.getCode())) {
            //不是默认角色可以修改名称
            updateEntity.setName(inputDto.getName());
        }
        updateEntity.setDescription(inputDto.getDescription());
        roleDao.update(updateEntity);

        //删除角色关联的权限信息
        rolePermissionRelationDao.deleteByRoleId(inputDto.getId());

        //处理角色关联的权限信息
        if (CollectionUtil.isNotEmpty(inputDto.getPermissionIdSet())) {
            List<PermissionTreeOutputDto> entityList = permissionCache.listByIdSet(inputDto.getPermissionIdSet());
            if (CollectionUtil.isNotEmpty(entityList)) {
                //新增角色关联的权限信息
                Map<String, String> dataPermissionMap = inputDto.getDataPermissionMap();
                List<RolePermissionRelationEntity> insertEntityList = new ArrayList<>();
                Set<String> dbIdList = entityList.stream().map(PermissionTreeOutputDto::getId).collect(Collectors.toSet());
                long sort = System.currentTimeMillis();
                for (String permissionId : dbIdList) {
                    RolePermissionRelationEntity e = new RolePermissionRelationEntity();
                    e.setRoleId(inputDto.getId());
                    e.setPermissionId(permissionId);
                    e.setType(PermissionTypeEnum.READ);
                    if (null != dataPermissionMap) {
                        e.setDataInto(dataPermissionMap.get(permissionId));
                    }
                    e.setSort(--sort);
                    insertEntityList.add(e);
                }
                rolePermissionRelationDao.insert(insertEntityList);
            }
        }

        //处理职务与角色的关系
        jobPostRoleRelationClient.deleteByRoleId(new IdRequest(inputDto.getId()));
        Set<String> jobPostIdSet = inputDto.getJobPostIdSet();
        if (CollectionUtil.isNotEmpty(jobPostIdSet)) {
            List<HrmJobPostRoleRelationCreateInputDto> inputDtoList = new ArrayList<>();
            for (String jobPostId : jobPostIdSet) {
                inputDtoList.add(new HrmJobPostRoleRelationCreateInputDto(jobPostId, inputDto.getId()));
            }
            jobPostRoleRelationClient.batchCreate(new HrmJobPosRoleRelationBatchInsertInputDto(inputDtoList));
        }

        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(IamRoleUpdateStatusInputDto inputDto) {
        RoleEntity entity = roleDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        if (isDefaultRole(entity.getCode())) {
            throw new IamBusinessException("iam.role.updateStatus.defaultRole", "默认角色，不能修改状态");
        }

        return roleDao.updateStatus(inputDto.getId(),inputDto.getStatus());
    }

    @Override
    public IamRoleDetailOutputDto detail(IdRequest request) {
        RoleEntity entity = roleDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamRoleDetailOutputDto.class);
    }

    @Override
    public IamRoleDetailOutputDto detailByCode(IamRoleCodeInputDto request) {
        RoleEntity entity = roleDao.getByCode(request.getCode());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamRoleDetailOutputDto.class);
    }

    @Override
    public IamRoleDetailOutputDto detailByName(IamRoleCodeInputDto request) {
        RoleEntity entity = roleDao.getByName(request.getCode());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamRoleDetailOutputDto.class);
    }

    @Override
    public List<String> listSubId(IamRoleListSubInputDto inputDto) {
        return roleDao.listSubId(inputDto);
    }

    @Override
    public List<String> listIdByType(RoleTypeEnum type) {
        return roleDao.listIdByType(type);
    }

    @Override
    public List<String> listParentByTarget(IdRequest request) {
        return roleDao.listParentByTarget(request.getId());
    }

    @Override
    public List<IamRoleListOutputDto> listSub(IamRoleListSubInputDto inputDto) {
        List<RoleEntity> entityList = roleDao.listSub(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamRoleListOutputDto.class);
    }

    @Override
    public Page<IamRoleListOutputDto> page(IamRoleQueryPageInputDto inputDto) {
        Page<RoleEntity> page = roleDao.selectPage(inputDto);
        Page<IamRoleListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamRoleListOutputDto.class));
        return pageResult;
    }

    @Override
    public Map<String, IamRoleDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<RoleEntity> userEntityList = roleDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(RoleEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), IamRoleDetailOutputDto.class)));
    }

    private boolean isDefaultRole(String code) {
        try {
            CompanyDefaultRoleEnum roleEnum = BaseEnum.getInstance(CompanyDefaultRoleEnum.class, code);
            if (null != roleEnum) {
                return true;
            }
            roleEnum = BaseEnum.getInstance(CompanyDefaultRoleEnum.class, code);
            if (null != roleEnum) {
                return true;
            }

            roleEnum = BaseEnum.getInstance(CompanyDefaultRoleEnum.class, code);
            if (null != roleEnum) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
}
