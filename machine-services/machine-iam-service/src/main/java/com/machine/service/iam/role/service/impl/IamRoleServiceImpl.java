package com.machine.service.iam.role.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.client.iam.role.dto.input.*;
import com.machine.client.iam.role.dto.output.IamRoleDetailOutputDto;
import com.machine.client.iam.role.dto.output.IamRoleListOutputDto;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.iam.IamDataPermissionScopeTypeEnum;
import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
import com.machine.sdk.common.envm.iam.IamPermissionTypeEnum;
import com.machine.sdk.common.envm.iam.role.IamCompanyDefaultRoleEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.role.dao.IIamRoleDao;
import com.machine.service.iam.role.dao.IIamRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.IamRoleEntity;
import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;
import com.machine.service.iam.role.service.IIamRoleService;
import com.machine.service.iam.user.dao.IIamUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.starter.redis.cache.RedisCacheIamPermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.sdk.common.constant.CommonIamConstant.DATA_PERMISSION_DEFAULT_FUNCTION_CODE;

@Slf4j
@Service
public class IamRoleServiceImpl implements IIamRoleService {

    @Autowired
    private IDataLeaf4IamCodeClient leafClient;

    @Autowired
    private RedisCacheIamPermission permissionCache;

    @Autowired
    private IIamRoleDao roleDao;

    @Autowired
    private IIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IIamRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamRoleCreateInputDto inputDto) {
        //验证名称是否存在
        IamRoleEntity entityByName = roleDao.getByName(inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("iam.role.service.create.nameAlreadyExists", "角色名称已经存在");
        }

        //验证数据权限
        DataPermissionRuleDto dataPermissionRule = inputDto.getDataPermissionRule();
        String functionCode = dataPermissionRule.getFunctionCode();
        if (!DATA_PERMISSION_DEFAULT_FUNCTION_CODE.equals(functionCode)) {
            throw new IamBusinessException("iam.role.service.create.functionCodeWrong", "数据权限编码错误");
        }

        boolean contains = false;
        String scopeCode = dataPermissionRule.getScopeCode();
        for (IamDataPermissionScopeTypeEnum e : IamDataPermissionScopeTypeEnum.values()) {
            if (e.getName().equals(scopeCode)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            throw new IamBusinessException("iam.role.service.create.functionScopeCodeWrong", "数据权限范围编码错误");
        }

        if (IamDataPermissionScopeTypeEnum.CUSTOM.getName().equals(scopeCode)) {
            if (CollectionUtil.isEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new IamBusinessException("iam.role.service.create.dataPermissionEmpty", "自定义数据权限范围为空");
            }
        } else {
            if (CollectionUtil.isNotEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new IamBusinessException("iam.role.service.create.dataPermissionNotEmpty", "数据权限范围不为空");
            }
        }

        IamRoleEntity insertEntity = new IamRoleEntity();
        insertEntity.setParentId(inputDto.getType().name().toLowerCase());
        insertEntity.setStatus(StatusEnum.ENABLE);

        //角色编码
        insertEntity.setCode(leafClient.roleCode());
        insertEntity.setType(inputDto.getType());
        insertEntity.setName(inputDto.getName());
        insertEntity.setDescription(inputDto.getDescription());
        insertEntity.setDataPermissionRule(JSONUtil.toJsonStr(dataPermissionRule));
        insertEntity.setSort(System.currentTimeMillis());
        return roleDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        IamRoleEntity entity = roleDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (isDefaultRole(entity.getCode())) {
            throw new IamBusinessException("iam.role.service.delete.defaultRole", "默认角色，不能删除");
        }

        //子角色信息
        List<IamRoleEntity> subList = roleDao.listSub(new IamRoleListSubInputDto(entity.getId()));
        if (!CollectionUtil.isEmpty(subList)) {
            throw new IamBusinessException("iam.role.service.delete.hasSubRole", "下面有子数据，不能删除");
        }

        //是否关联用户
        List<IamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao
                .selectByRoleId(request.getId());
        if (!CollectionUtil.isEmpty(iamUserRoleRelationEntityList)) {
            throw new IamBusinessException("iam.role.service.delete.associationUser", "角色关联用户，不能删除");
        }

        //删除角色和权限关系
        rolePermissionRelationDao.deleteByRoleId(request.getId());
        return roleDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(IamRoleUpdateInputDto inputDto) {
        IamRoleEntity entity = roleDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证数据权限
        DataPermissionRuleDto dataPermissionRule = inputDto.getDataPermissionRule();
        String functionCode = dataPermissionRule.getFunctionCode();
        if (!DATA_PERMISSION_DEFAULT_FUNCTION_CODE.equals(functionCode)) {
            throw new IamBusinessException("iam.role.service.update.functionCodeWrong", "数据权限编码错误");
        }

        boolean contains = false;
        String scopeCode = dataPermissionRule.getScopeCode();
        for (IamDataPermissionScopeTypeEnum e : IamDataPermissionScopeTypeEnum.values()) {
            if (e.getName().equals(scopeCode)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            throw new IamBusinessException("iam.role.service.update.functionScopeCodeWrong", "数据权限范围编码错误");
        }

        if (IamDataPermissionScopeTypeEnum.CUSTOM.getName().equals(scopeCode)) {
            if (CollectionUtil.isEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new IamBusinessException("iam.role.service.update.dataPermissionEmpty", "自定义数据权限范围为空");
            }
        } else {
            if (CollectionUtil.isNotEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new IamBusinessException("iam.role.service.update.dataPermissionNotEmpty", "数据权限范围不为空");
            }
        }

        //验证名称在同一层级是否存在
        IamRoleEntity entityByName = roleDao.getByName(inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new IamBusinessException("iam.role.service.update.nameAlreadyExists", "角色名称已经存在");
        }

        //修改角色信息
        IamRoleEntity updateEntity = new IamRoleEntity();
        updateEntity.setId(inputDto.getId());
        if (!isDefaultRole(entity.getCode())) {
            //不是默认角色可以修改名称
            updateEntity.setName(inputDto.getName());
        }
        updateEntity.setDescription(inputDto.getDescription());
        updateEntity.setDataPermissionRule(JSONUtil.toJsonStr(dataPermissionRule));
        return roleDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(IamRoleUpdateStatusInputDto inputDto) {
        IamRoleEntity entity = roleDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        if (isDefaultRole(entity.getCode())) {
            throw new IamBusinessException("iam.role.service.updateStatus.defaultRole", "默认角色，不能修改状态");
        }

        return roleDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(IamRoleUpdatePermissionInputDto inputDto) {
        //处理角色关联的权限信息
        List<IamRolePermissionRelationEntity> insertEntityList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inputDto.getPermissionIdSet())) {
            List<IamPermissionTreeOutputDto> outputDtoList = permissionCache.listByIdSet(inputDto.getPermissionIdSet());
            if (CollectionUtil.isNotEmpty(outputDtoList)) {
                //新增角色关联的权限信息
                Map<String, List<DataPermissionRuleDto>> dataPermissionRuleMap = inputDto.getDataPermissionRuleMap();
                long sort = System.currentTimeMillis();
                for (IamPermissionTreeOutputDto outputDto : outputDtoList) {
                    IamRolePermissionRelationEntity e = new IamRolePermissionRelationEntity();
                    e.setRoleId(inputDto.getId());
                    e.setPermissionId(outputDto.getId());
                    e.setType(IamPermissionTypeEnum.READ);
                    if (IamPermissionResourceTypeEnum.MENU == outputDto.getResourceType()) {
                        //保存数据权限信息
                        if (null != dataPermissionRuleMap) {
                            List<DataPermissionRuleDto> dataPermissionRuleList = dataPermissionRuleMap.get(outputDto.getId());
                            if (CollectionUtil.isNotEmpty(dataPermissionRuleList)) {
                                e.setDataPermissionRules(JSONUtil.toJsonStr(dataPermissionRuleList));
                            } else {
                                e.setDataPermissionRules(EMPTY_LIST_STR);
                            }
                        }
                    }
                    e.setSort(--sort);
                    insertEntityList.add(e);
                }
            }
        }

        //删除角色关联的权限信息
        rolePermissionRelationDao.deleteByRoleId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(insertEntityList)) {
            rolePermissionRelationDao.insert(insertEntityList);
        }
    }

    @Override
    public IamRoleDetailOutputDto detail(IdRequest request) {
        IamRoleEntity entity = roleDao.getById(request.getId());
        if (null == entity) {
            return null;
        }

        IamRoleDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamRoleDetailOutputDto.class,true);
        String dataPermissionRule = entity.getDataPermissionRule();
        if (StrUtil.isNotBlank(dataPermissionRule)) {
            outputDto.setDataPermissionRule(JSONUtil.toBean(JSONUtil.toJsonStr(dataPermissionRule), DataPermissionRuleDto.class));
        }
        return outputDto;
    }

    @Override
    public List<String> listSubId(IamRoleListSubInputDto inputDto) {
        return roleDao.listSubId(inputDto);
    }

    @Override
    public List<String> listParentByTarget(IdRequest request) {
        return roleDao.listParentByTarget(request.getId());
    }

    @Override
    public List<IamRoleListOutputDto> listSub(IamRoleListSubInputDto inputDto) {
        List<IamRoleEntity> entityList = roleDao.listSub(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamRoleListOutputDto.class);
    }

    @Override
    public Page<IamRoleListOutputDto> selectPage(IamRoleQueryPageInputDto inputDto) {
        Page<IamRoleEntity> page = roleDao.selectPage(inputDto);
        Page<IamRoleListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), IamRoleListOutputDto.class));
        return pageResult;
    }

    @Override
    public Map<String, IamRoleDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<IamRoleEntity> userEntityList = roleDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(IamRoleEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), IamRoleDetailOutputDto.class)));
    }

    private boolean isDefaultRole(String code) {
        try {
            IamCompanyDefaultRoleEnum roleEnum = BaseEnum.getInstance(IamCompanyDefaultRoleEnum.class, code);
            if (null != roleEnum) {
                return true;
            }
            roleEnum = BaseEnum.getInstance(IamCompanyDefaultRoleEnum.class, code);
            if (null != roleEnum) {
                return true;
            }

            roleEnum = BaseEnum.getInstance(IamCompanyDefaultRoleEnum.class, code);
            if (null != roleEnum) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }
}
